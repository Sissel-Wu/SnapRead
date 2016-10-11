package sensation.snapread.view.main.typecollection;

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
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

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

    @BindView(R.id.no_data)
    View noDataView;


    NavigationInterface navigationInterface;
    TypeContract.Presenter presenter;
    TypeAdapter typeAdapter;
    List<String> deleteList;
    boolean isLoaded = false;
    MaterialDialog dialog;
    ImageView imageView1, imageView2, imageView3;
    View card1, card2, card3;
    String imgPath = "https://camo.githubusercontent.com/5f260ff56ba9dd4accf22a9572a9874556704bf9/687474703a2f2f75706c6f61642d696d616765732e6a69616e7368752e696f2f75706c6f61645f696d616765732f333037323536362d386564663232356566306266646263332e706e673f696d6167654d6f6772322f6175746f2d6f7269656e742f7374726970253743696d61676556696577322f322f772f31323430";

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
        initNoDataView();
    }

    private void initNoDataView() {
        noDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationInterface.showNavigation();
                fab.show(true);
            }
        });
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
                                presenter.addType(tagName, des, imgPath);
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
                if (imageView1 == null) {
                    card1 = view.findViewById(R.id.card1);
                    imageView1 = (ImageView) view.findViewById(R.id.image1);
                }
                if (imageView2 == null) {
                    card2 = view.findViewById(R.id.card2);
                    imageView2 = (ImageView) view.findViewById(R.id.image2);
                }
                if (imageView3 == null) {
                    card3 = view.findViewById(R.id.card3);
                    imageView3 = (ImageView) view.findViewById(R.id.image3);
                }
                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgPath = "https://camo.githubusercontent.com/5f260ff56ba9dd4accf22a9572a9874556704bf9/687474703a2f2f75706c6f61642d696d616765732e6a69616e7368752e696f2f75706c6f61645f696d616765732f333037323536362d386564663232356566306266646263332e706e673f696d6167654d6f6772322f6175746f2d6f7269656e742f7374726970253743696d61676556696577322f322f772f31323430";
                        clearElevation();
                        card1.setElevation(20);
                    }
                });
                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgPath = "http://img.taopic.com/uploads/allimg/140226/234991-14022609204234.jpg";
                        clearElevation();
                        card2.setElevation(20);
                    }
                });
                imageView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgPath = "http://img2.3lian.com/2014/f7/11/d/97.jpg";
                        clearElevation();
                        card3.setElevation(20);
                    }
                });
            }
        } else {
            dialog.show();
        }
    }

    private void clearElevation() {
        card1.setElevation(3);
        card2.setElevation(3);
        card3.setElevation(3);
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
                        SearchActivity.startActivity(getActivity(), item.getTitle(), ViewTool.TYPE_TAG, item.getTypeID());
                    }
                }
            }
        });

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
        if (typeList.size() != 0) {
            noDataView.setVisibility(View.GONE);
            typeAdapter = new TypeAdapter(getContext(), R.layout.type_item, typeList, mTypeListView);
            mTypeListView.setAdapter(typeAdapter);
        }
    }

    @Override
    public void addSuccess() {
        Toast.makeText(getContext(), "添加成功！", Toast.LENGTH_SHORT).show();
        presenter.start();
    }

    @Override
    public void addFail() {
        Toast.makeText(getContext(), "添加失败，请检查网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInternetError() {
        Toast.makeText(getContext(), "网络错误，请检查设置~", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showImageError() {
        Toast.makeText(getContext(), "图片错误，请重试", Toast.LENGTH_SHORT).show();
    }
}
