package sensation.snapread.view.main.recommend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import sensation.snapread.R;
import sensation.snapread.contract.RecommendContract;
import sensation.snapread.model.vopo.CollectionListItemVO;
import sensation.snapread.view.NavigationInterface;
import sensation.snapread.view.main.collection.adapter.CollectionAdapter;
import sensation.snapread.view.post.PostActivity;

public class RecommendFragment extends Fragment implements RecommendContract.View {

    @BindView(R.id.recommend_list)
    ListView mRecommendListView;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.no_data)
    View noDataView;

    NavigationInterface navigationInterface;
    RecommendContract.Presenter presenter;
    boolean isLoaded = false;

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
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

    private void initNoDataView() {
        noDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationInterface.showNavigation();
            }
        });
    }

    private void initListView() {
        //滚动事件
        mRecommendListView.setOnTouchListener(new View.OnTouchListener() {
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
                        } else if (endY < startY) {
                            navigationInterface.hideNavigation();
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

        //点击事件
        mRecommendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CollectionListItemVO item = (CollectionListItemVO) parent.getItemAtPosition(position);
                if (item != null) {
                    //进入文章
                    if (!item.getImgUrl().equals("")) {
                        PostActivity.
                                startActivity(getActivity(),
                                        item.getCollectionID(),
                                        item.getTitle(),
                                        item.getImgUrl());
                    } else {
                        PostActivity.
                                startActivity(getActivity(),
                                        item.getCollectionID(),
                                        item.getTitle(),
                                        "");
                    }
                }
            }
        });
    }

    public void setNavigationInterface(NavigationInterface navigationInterface) {
        this.navigationInterface = navigationInterface;
    }

    @Override
    public void setPresenter(RecommendContract.Presenter presenter) {
        this.presenter = presenter;
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
    public void showInternetError() {
        Toast.makeText(getContext(), "网络错误，请检查设置~", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showList(List<CollectionListItemVO> recommendList) {
        Log.d("SnapRead", "showList: " + recommendList.size());
        if (recommendList.size() != 0) {
            noDataView.setVisibility(View.GONE);
            CollectionAdapter adapter = new CollectionAdapter(getContext(), R.layout.collection_item, recommendList, mRecommendListView);
            mRecommendListView.setAdapter(adapter);
        }
    }
}
