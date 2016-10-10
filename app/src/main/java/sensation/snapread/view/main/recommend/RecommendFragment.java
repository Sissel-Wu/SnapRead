package sensation.snapread.view.main.recommend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sensation.snapread.R;
import sensation.snapread.contract.RecommendContract;
import sensation.snapread.view.NavigationInterface;

public class RecommendFragment extends Fragment implements RecommendContract.View {

    @BindView(R.id.recommend_list)
    ListView mRecommendListView;

    NavigationInterface navigationInterface;

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
    }

    private void initListView() {
        //TODO 设置列表监听
    }

    private void startPresenter() {
        //TODO recommend presenter
    }

    public void setNavigationInterface(NavigationInterface navigationInterface) {
        this.navigationInterface = navigationInterface;
    }

    @Override
    public void setPresenter(RecommendContract.Presenter presenter) {

    }
}
