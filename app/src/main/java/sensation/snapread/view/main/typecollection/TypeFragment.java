package sensation.snapread.view.main.typecollection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sensation.snapread.R;
import sensation.snapread.contract.TypeContract;
import sensation.snapread.model.vopo.TypeItemVO;
import sensation.snapread.view.NavigationInterface;
import sensation.snapread.view.main.typecollection.adapter.TypeAdapter;
import sensation.snapread.view.search.SearchActivity;
import sensation.snapread.view.widget.ViewTool;

/**
 * 类别碎片
 */
public class TypeFragment extends Fragment implements TypeContract.View {

    private final static int SELECT_PHOTO = 2;

    @BindView(R.id.type_list)
    ListView mTypeListView;

    @BindView(R.id.fab)
    com.github.clans.fab.FloatingActionButton fab;

    @BindView(R.id.delete_btn)
    TextView deleteView;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    NavigationInterface navigationInterface;
    TypeContract.Presenter presenter;
    TypeAdapter typeAdapter;
    List<String> deleteList;
    boolean isLoaded = false;
    MaterialDialog dialog;
    ImageView imageView;
    String path;

    public static TypeFragment newInstance() {
        return new TypeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type, container, false);
        ButterKnife.bind(this, view);
        initViews();
        startPresenter();
        return view;
    }

    private void initViews() {
        initListView();
        initRefreshLayout();
    }

    @OnClick(R.id.fab)
    void newType() {
        if (dialog == null) {
            dialog = new MaterialDialog.Builder(getContext())
                    .title("新建标签")
                    .customView(R.layout.type_builder, true)
                    .positiveText("确认")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            View view = dialog.getCustomView();

                            if (view != null) {
                                TextView tagNameView = (TextView) view.findViewById(R.id.tag);
                                String tagName = tagNameView.getText().toString();
                                TextView tagDesView = (TextView) view.findViewById(R.id.des);
                                String des = tagDesView.getText().toString();
                                presenter.addType(tagName, des, path);
                            }
                        }
                    })
                    .negativeText("取消")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .cancelable(true)
                    .canceledOnTouchOutside(false)
                    .show();

            View view = dialog.getCustomView();
            if (view != null) {
                if (imageView == null) {
                    imageView = (ImageView) view.findViewById(R.id.image);
                }
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "选择图片"), SELECT_PHOTO);
                    }
                });
            }
        } else {
            dialog.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Uri uri = data.getData();
                    path = ViewTool.uri2path(getContext(), uri);

                    imageView.setImageBitmap(BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(data.getData())));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initRefreshLayout() {
        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.start();
            }
        });
    }

    private void initListView() {
        //滚动事件
        mTypeListView.setOnTouchListener(new View.OnTouchListener() {
            float startY = 0, endY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        endY = event.getY();
                        if (endY > startY + 5) {
                            navigationInterface.showNavigation();
                            fab.show(true);

                        } else if (endY < startY) {
                            navigationInterface.hideNavigation();
                            fab.hide(true);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        startY = 0;
                        endY = 0;
                        break;
                }
                return false;
            }
        });

        mTypeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TypeItemVO item = (TypeItemVO) parent.getItemAtPosition(position);
                if (item != null) {
                    if (mTypeListView.getChoiceMode() == AbsListView.CHOICE_MODE_MULTIPLE) {
                        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
                        if (checkBox.isChecked()) {
                            mTypeListView.setItemChecked(position, false);
                            deleteList.remove(item.getTypeID());
                        } else {
                            mTypeListView.setItemChecked(position, true);
                            deleteList.add(item.getTypeID());
                        }
                        typeAdapter.notifyDataSetChanged();
                        mTypeListView.invalidate();

                    } else {
                        SearchActivity.startActivity(getActivity(), item.getTitle(), ViewTool.TYPE_TAG);
                    }
                }
            }
        });

        //长按事件
//        mTypeListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                deleteList = new ArrayList<>();
//                TypeItemVO item = (TypeItemVO) parent.getItemAtPosition(position);
//                if (item != null) {
//                    mTypeListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
//                    mTypeListView.setItemChecked(position, true);
//                    deleteList.add(item.getTypeID());
//
//                    typeAdapter.notifyDataSetChanged();
//                    mTypeListView.invalidate();
//                    mTypeListView.setFocusable(true);
//
//                    navigationInterface.hideNavigation();
//                    fab.hide(true);
//                    deleteView.setVisibility(View.VISIBLE);
//                    deleteView.startAnimation(ViewTool.getAnim(deleteView, 2, 10));
//                }
//                return true;
//            }
//        });

        mTypeListView.setFocusable(true);
        mTypeListView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (mTypeListView.getChoiceMode() == AbsListView.CHOICE_MODE_MULTIPLE) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        mTypeListView.clearChoices();
                        mTypeListView.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
                        typeAdapter.notifyDataSetChanged();
                        mTypeListView.invalidate();


                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        });
    }

    private void startPresenter() {
        if (!isLoaded) {
            isLoaded = true;
            if (presenter != null) {
                presenter.start();
            } else {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        presenter.start();
                    }
                }, 100);
            }
        }
    }

    public void setNavigationInterface(NavigationInterface navigationInterface) {
        this.navigationInterface = navigationInterface;
    }

    @Override
    public void setPresenter(TypeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideLoading() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void setTypesList(List<TypeItemVO> typeList) {
        typeAdapter = new TypeAdapter(getContext(), R.layout.type_item, typeList, mTypeListView);
        mTypeListView.setAdapter(typeAdapter);
    }
}
