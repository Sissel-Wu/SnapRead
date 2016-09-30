package sensation.snapread.view.main.collection;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.IndeterminateProgressDrawable;
import sensation.snapread.R;
import sensation.snapread.contract.CollectionContract;
import sensation.snapread.model.vopo.CollectionListItemVO;
import sensation.snapread.view.NavigationInterface;
import sensation.snapread.view.main.collection.adapter.CollectionAdapter;
import sensation.snapread.view.widget.ScrollChildSwipeRefreshLayout;

/**
 * 收藏碎片
 */
public class CollectionFragment extends Fragment implements CollectionContract.View {
    private static final String TAG = "SnapRead";

    @BindView(R.id.collection_list)
    ListView mCollectionListView;

    @BindView(R.id.fab)
    com.github.clans.fab.FloatingActionButton fab;

    @BindView(R.id.refresh_layout)
    ScrollChildSwipeRefreshLayout refreshLayout;

    NavigationInterface navigationInterface;
    CollectionContract.Presenter presenter;
    CollectionAdapter collectionAdapter;
    List<String> deleteList;
    boolean isLoaded = false, isLoading = false;
    View footerView;

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

    private void initViews() {
        initListView();
        initRefreshLayout();
    }

    private void initListView() {
        //滚动事件
        mCollectionListView.setOnTouchListener(new View.OnTouchListener() {
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

        mCollectionListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (view.getLastVisiblePosition() == totalItemCount - 1) {
                    if (!isLoading) {
                        isLoading = true;
                        if (footerView == null) {
                            footerView = LayoutInflater.from(getContext()).inflate(R.layout.collection_footer, null);
                            ProgressBar progressBar = (ProgressBar) footerView.findViewById(R.id.progress_bar);
                            progressBar.setIndeterminateDrawable(new IndeterminateProgressDrawable(getContext()));
                        }
                        mCollectionListView.addFooterView(footerView);
                    }
                }
            }
        });

        //点击事件
        mCollectionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CollectionListItemVO item = (CollectionListItemVO) parent.getItemAtPosition(position);
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
                        //TODO 进入文章
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
        collectionAdapter = new CollectionAdapter(getContext(), R.layout.collection_item, collectionList, mCollectionListView);
        mCollectionListView.setAdapter(collectionAdapter);
    }

    @Override
    public void addCollections(List<CollectionListItemVO> collectionList) {
        collectionAdapter.addAll(collectionList);
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
    public void deleteCollection(List<CollectionListItemVO> deleteList) {
        int count = collectionAdapter.getCount();
        for (int i = 0; i < count; i++) {
            CollectionListItemVO item = collectionAdapter.getItem(i);
            for (int j = 0; j < deleteList.size(); j++) {
                CollectionListItemVO deleteItem = deleteList.get(j);
                if (deleteItem.equals(item)) {
                    collectionAdapter.remove(item);
                }
            }
        }
        collectionAdapter.notifyDataSetChanged();
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
                        presenter.start();
                    }
                }, 100);
            }
        }
    }

    /**
     * 隐藏显示动画
     *
     * @param view
     * @param inOrOut
     * @param offset
     * @return
     */
    private Animation getAnim(View view, int inOrOut, int offset) {
        float from, to;
        if (inOrOut == 1) {
            from = 0;
            if (view != null) {
                to = -(view.getHeight() + offset);
            } else {
                to = -offset;
            }
        } else {
            if (view != null) {
                from = -(view.getHeight() + offset);
            } else {
                from = -offset;
            }
            to = 0;
        }
        TranslateAnimation anim = new TranslateAnimation(0, 0, from, to);
        anim.setDuration(300);
        anim.setInterpolator(new DecelerateInterpolator());
        return anim;
    }
}
