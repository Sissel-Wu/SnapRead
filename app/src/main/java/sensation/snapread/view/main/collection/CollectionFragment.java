package sensation.snapread.view.main.collection;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sensation.snapread.R;
import sensation.snapread.contract.CollectionContract;
import sensation.snapread.model.vopo.CollectionListItemVO;
import sensation.snapread.view.NavigationInterface;
import sensation.snapread.view.main.collection.adapter.CollectionAdapter;
import sensation.snapread.view.post.PostActivity;
import sensation.snapread.view.widget.ScrollChildSwipeRefreshLayout;
import sensation.snapread.view.widget.ViewTool;

/**
 * 收藏碎片
 */
public class CollectionFragment extends Fragment implements CollectionContract.View {
    private static final String TAG = "SnapRead";

    @BindView(R.id.collection_list)
    ListView mCollectionListView;

    @BindView(R.id.refresh_layout)
    ScrollChildSwipeRefreshLayout refreshLayout;

    @BindView(R.id.delete_btn)
    TextView deleteView;

    @BindView(R.id.no_data)
    View noDataView;

    NavigationInterface navigationInterface;
    CollectionContract.Presenter presenter;
    CollectionAdapter collectionAdapter;
    List<String> deleteList;
    boolean isLoaded = false;

    public static CollectionFragment newInstance() {
        return new CollectionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        ButterKnife.bind(this, view);
        initViews();
        startPresenter();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.start();
        }
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
            }
        });
    }

    @OnClick(R.id.delete_btn)
    void deletePost() {
        presenter.deleteCollection(deleteList);
    }

    private void initListView() {
        //滚动事件
        mCollectionListView.setOnTouchListener(new View.OnTouchListener() {
            float startY = 0, endY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (deleteView.getVisibility() == View.GONE) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            startY = event.getY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            endY = event.getY();
                            if (endY > startY + 5) {
                                navigationInterface.showNavigation();
                            } else if (endY < startY) {
                                navigationInterface.hideNavigation();
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            startY = 0;
                            endY = 0;
                            break;
                    }
                }
                return false;
            }
        });

        //点击事件
        mCollectionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View sharedView = view.findViewById(R.id.type_circle);
                CollectionListItemVO item = (CollectionListItemVO) parent.getItemAtPosition(position);
                Gson gson = new Gson();
                Log.d(TAG, "onItemClick: " + gson.toJson(item));
                if (item != null) {
                    if (mCollectionListView.getChoiceMode() == AbsListView.CHOICE_MODE_MULTIPLE) {
                        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
                        if (checkBox.isChecked()) {
                            mCollectionListView.setItemChecked(position, false);
                            deleteList.remove(item.getCollectionID());
                        } else {
                            mCollectionListView.setItemChecked(position, true);
                            deleteList.add(item.getCollectionID());
                        }
                        collectionAdapter.notifyDataSetChanged();
                        mCollectionListView.invalidate();

                    } else {
                        //进入文章
                        if (!item.getImgUrl().equals("")) {
                            PostActivity.
                                    startActivity(getActivity(),
                                            item.getCollectionID(),
                                            item.getTitle(),
                                            item.getImgUrl(),
                                            sharedView);
                        } else {
                            PostActivity.
                                    startActivity(getActivity(),
                                            item.getCollectionID(),
                                            item.getTitle(),
                                            "",
                                            sharedView);
                        }
                    }
                }
            }
        });

        //长按事件
        mCollectionListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteList = new ArrayList<>();
                CollectionListItemVO item = (CollectionListItemVO) parent.getItemAtPosition(position);
                if (item != null) {
                    mCollectionListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
                    mCollectionListView.setItemChecked(position, true);
                    deleteList.add(item.getCollectionID());

                    collectionAdapter.notifyDataSetChanged();
                    mCollectionListView.invalidate();

                    navigationInterface.hideNavigation();
                    deleteView.setVisibility(View.VISIBLE);
                    deleteView.startAnimation(ViewTool.getAnim(deleteView, 2, ViewTool.dip2px(getContext(), 20)));
                }
                return true;
            }
        });

        mCollectionListView.setFocusable(true);
        mCollectionListView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (mCollectionListView.getChoiceMode() == AbsListView.CHOICE_MODE_MULTIPLE) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        navigationInterface.showNavigation();
                        deleteView.startAnimation(ViewTool.getAnim(deleteView, 1, ViewTool.dip2px(getContext(), 20)));
                        deleteView.setVisibility(View.GONE);

                        mCollectionListView.clearChoices();
                        mCollectionListView.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
                        collectionAdapter.notifyDataSetChanged();
                        mCollectionListView.invalidate();
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

    private void initRefreshLayout() {
        refreshLayout.setScrollUpChild(mCollectionListView);
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

    @Override
    public void showCollections(List<CollectionListItemVO> collectionList) {
        List<CollectionListItemVO> invertedCollectionList = new ArrayList<>();
        for (int i = collectionList.size() - 1; i >= 0; i--) {
            invertedCollectionList.add(collectionList.get(i));
        }
        if (collectionList.size() != 0) {
            noDataView.setVisibility(View.GONE);
            collectionAdapter = new CollectionAdapter(getContext(), R.layout.collection_item, invertedCollectionList, mCollectionListView);
            mCollectionListView.setAdapter(collectionAdapter);
        }
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
    public void deleteSuccess() {
        Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
        presenter.start();
    }

    @Override
    public void deleteFail() {
        Toast.makeText(getContext(), "删除失败，请检查网络~", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInternetError() {
        Toast.makeText(getContext(), "网络错误，请检查设置~", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(CollectionContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void setNavigationInterface(NavigationInterface navigationInterface) {
        this.navigationInterface = navigationInterface;
    }

    public void startPresenter() {
        if (!isLoaded) {
            isLoaded = true;
            if (presenter != null) {
                presenter.start();
            } else {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (presenter != null) {
                            presenter.start();
                        }
                    }
                }, 200);
            }
        }
    }
}
