package sensation.snapread.view.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sensation.snapread.R;
import sensation.snapread.contract.SearchContract;
import sensation.snapread.model.vopo.CollectionListItemVO;
import sensation.snapread.presenter.SearchPresenter;
import sensation.snapread.view.main.collection.adapter.CollectionAdapter;
import sensation.snapread.view.post.PostActivity;
import sensation.snapread.view.widget.SwipeBackActivity;
import sensation.snapread.view.widget.ViewTool;

public class SearchActivity extends SwipeBackActivity implements SearchContract.View {

    private static final String ARG_KEY = "keyword", ARG_TYPE = "type", ARG_ID = "id";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_list)
    ListView mSearchListView;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    String keyword, type, id;
    SearchContract.Presenter presenter;
    CollectionAdapter collectionAdapter;
    MaterialDialog deleteDialog;

    public static void startActivity(Activity activity, String keyword, String type, String id) {
        Intent intent = new Intent(activity, SearchActivity.class);
        intent.putExtra(ARG_KEY, keyword);
        intent.putExtra(ARG_TYPE, type);
        intent.putExtra(ARG_ID, id);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in, R.anim.half_silde_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        presenter = new SearchPresenter(this);

        keyword = getIntent().getStringExtra(ARG_KEY);
        type = getIntent().getStringExtra(ARG_TYPE);
        id = getIntent().getStringExtra(ARG_ID);
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
                View sharedView = view.findViewById(R.id.type_circle);
                CollectionListItemVO item = (CollectionListItemVO) parent.getItemAtPosition(position);

                //进入文章
                if (!item.getImgUrl().equals("")) {
                    PostActivity.
                            startActivity(SearchActivity.this,
                                    item.getCollectionID(),
                                    item.getTitle(),
                                    item.getImgUrl(),
                                    sharedView);
                } else {
                    PostActivity.
                            startActivity(SearchActivity.this,
                                    item.getCollectionID(),
                                    item.getTitle(),
                                    "",
                                    sharedView);
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
    public void deleteSuccess() {
        Toast.makeText(SearchActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void deleteFail() {
        Toast.makeText(SearchActivity.this, "删除失败，请检查网络~", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void noData() {
        Toast.makeText(SearchActivity.this, "没有搜索到相关内容~", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInternetError() {
        Toast.makeText(SearchActivity.this, "网络错误，请检查设置~", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if (type.equals(ViewTool.TYPE_TAG)) {
            menuInflater.inflate(R.menu.menu_type, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_delete:
                if (deleteDialog == null) {
                    deleteDialog = new MaterialDialog.Builder(this)
                            .title("确认删除 " + keyword + " 标签吗?")
                            .content("同时也会删除该标签下的文章")
                            .positiveText("确认")
                            .negativeText("取消")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    presenter.deleteTag(id);
                                    dialog.dismiss();
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    deleteDialog.show();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.half_slide_in, R.anim.slide_out);
    }
}
