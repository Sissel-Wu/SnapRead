package sensation.snapread.view.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sensation.snapread.R;
import sensation.snapread.contract.SearchContract;

public class SearchActivity extends AppCompatActivity implements SearchContract.View {

    private static final String ARG_KEY = "key";

    @BindView(R.id.toolbar_title)
    TextView mToolBarTitle;

    @BindView(R.id.search_list)
    ListView mSearchListView;

    String type, key;

    public static void newInstance(Activity activity, String keyword) {
        Intent intent = new Intent(activity, SearchActivity.class);
        intent.putExtra(ARG_KEY, keyword);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        key = getIntent().getStringExtra(ARG_KEY);
        initViews();
    }

    private void initViews() {
        initToolBar();
        initListView();
    }

    private void initToolBar() {
        mToolBarTitle.setText(key);
    }

    private void initListView() {
        //TODO listview监听
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {

    }
}
