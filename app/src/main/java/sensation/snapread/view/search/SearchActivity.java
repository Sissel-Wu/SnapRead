package sensation.snapread.view.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sensation.snapread.R;
import sensation.snapread.contract.SearchContract;
import sensation.snapread.model.RepositoryFactory;
import sensation.snapread.model.vopo.CollectionListItemVO;
import sensation.snapread.presenter.SearchPresenter;
import sensation.snapread.view.main.collection.adapter.CollectionAdapter;
import sensation.snapread.view.post.PostActivity;

public class SearchActivity extends AppCompatActivity implements SearchContract.View {

    private static final String ARG_KEY = "keyword", ARG_TYPE = "type";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_list)
    ListView mSearchListView;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    String keyword, type;
    SearchContract.Presenter presenter;
    CollectionAdapter collectionAdapter;

    public static void startActivity(Activity activity, String keyword, String type) {
        Intent intent = new Intent(activity, SearchActivity.class);
        intent.putExtra(ARG_KEY, keyword);
        intent.putExtra(ARG_TYPE, type);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        presenter = new SearchPresenter(RepositoryFactory.getInternetRepository(), this);

        keyword = getIntent().getStringExtra(ARG_KEY);
        type = getIntent().getStringExtra(ARG_TYPE);
        initViews();
        initSearch();
    }

    private void initSearch() {
        presenter.getSearchResult(keyword, type);
    }

    private void initViews() {
        initToolBar();
        initListView();
        initRefreshLayout();
    }

    private void initRefreshLayout() {
        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getSearchResult(keyword, type);
            }
        });
    }

    private void initToolBar() {
        toolbar.setTitle(keyword);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initListView() {
        mSearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CollectionListItemVO item = (CollectionListItemVO) parent.getItemAtPosition(position);

                //进入文章
                if (!item.getImgUrl().equals("")) {
                    PostActivity.
                            startActivity(SearchActivity.this,
                                    item.getCollectionID(),
                                    item.getTitle(),
                                    item.getImgUrl());
                } else {
                    PostActivity.
                            startActivity(SearchActivity.this,
                                    item.getCollectionID(),
                                    item.getTitle(),
                                    "");
                }
            }
        });
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showSearchResult(List<CollectionListItemVO> searchList) {
        collectionAdapter = new CollectionAdapter(this, R.layout.collection_item, searchList, mSearchListView);
        mSearchListView.setAdapter(collectionAdapter);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
