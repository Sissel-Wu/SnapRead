package sensation.snapread.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.litepal.tablemanager.Connector;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import sensation.snapread.presenter.BasePresenter;
import sensation.snapread.R;
import sensation.snapread.presenter.CollectionPresenter;
import sensation.snapread.presenter.PresenterCache;
import sensation.snapread.presenter.RecommendPresenter;
import sensation.snapread.presenter.TypePresenter;
import sensation.snapread.service.SnapService;
import sensation.snapread.view.NavigationInterface;
import sensation.snapread.view.main.about.AboutFragment;
import sensation.snapread.view.main.collection.CollectionFragment;
import sensation.snapread.view.main.recommend.RecommendFragment;
import sensation.snapread.view.main.typecollection.TypeFragment;
import sensation.snapread.view.search.SearchActivity;
import sensation.snapread.view.widget.ViewTool;

public class MainActivity extends AppCompatActivity implements NavigationInterface {
    /**
     * 是否准备退出标志位
     */
    boolean isExiting = false;

    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    @BindView(R.id.app_bar)
    View appBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitleView;

    @BindView(R.id.toolbar_image)
    ImageView toolbarImageView;

    @BindView(R.id.search_view)
    MaterialSearchView searchView;

    FragmentManager fragmentManager;
    CollectionFragment collectionFragment;
    TypeFragment typeFragment;
    RecommendFragment recommendFragment;
    AboutFragment aboutFragment;

    BasePresenter collectionPresenter, typePresenter, recommendPresenter;

    PresenterCache cache;
    String[] historyArray;
    List<String> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent snapIntent = new Intent(this, SnapService.class);
        startService(snapIntent);
        //这里是为了litepal新建数据库
        Connector.getDatabase();

        fragmentManager = getSupportFragmentManager();
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        initToolbar();
        initSearchView();
        initBottomNavigation();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initSearchView() {
        if (cache == null) {
            cache = PresenterCache.getInstance();
        }
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                historyArray = cache.getSearchHistory();
                if (historyArray != null) {
                    historyList = Arrays.asList(historyArray);
                    searchView.setSuggestions(historyArray);
                    searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = (String) parent.getItemAtPosition(position);
                            SearchActivity.startActivity(MainActivity.this, item, ViewTool.TYPE_SEARCH, "");
                        }
                    });
                }
            }

            @Override
            public void onSearchViewClosed() {

            }
        });
        searchView.setVoiceSearch(false);
//        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                cache.addSearchHistory(query);
                SearchActivity.startActivity(MainActivity.this, query, ViewTool.TYPE_SEARCH, "");
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initBottomNavigation() {
        //TODO 图标制作
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.collections, R.drawable.book, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tag, R.drawable.tag, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.recommendation, R.drawable.heart, R.color.colorPrimary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.about, R.drawable.share, R.color.colorPrimary);

        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);

        bottomNavigation.setForceTitlesDisplay(true);
        bottomNavigation.setAccentColor(getResources().getColor(R.color.colorAccent));

        // Set current item programmatically
        bottomNavigation.setCurrentItem(0);
        switchTab(0);

        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switchTab(position);
                return true;
            }
        });
    }

    private void switchTab(int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ViewTool.hideFragments(transaction, collectionFragment, typeFragment, recommendFragment, aboutFragment);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) findViewById(R.id.fragment_content).getLayoutParams();
        switch (position) {
            case 0:
                toolbarTitleView.setText(getString(R.string.collections));
                toolbarImageView.setImageDrawable(getDrawable(R.drawable.book));
                if (collectionFragment == null) {
                    layoutParams.setMargins(0, 0, 0, 0);
                    collectionFragment = CollectionFragment.newInstance();
                    collectionFragment.setNavigationInterface(this);
                    collectionPresenter = new CollectionPresenter(collectionFragment);
                    transaction.add(R.id.fragment_content, collectionFragment);
                } else {
                    layoutParams.setMargins(0, 0, 0, 0);
                    transaction.show(collectionFragment);
                }
                break;
            case 1:
                toolbarTitleView.setText(getResources().getString(R.string.tag));
                toolbarImageView.setImageDrawable(getDrawable(R.drawable.tag));
                if (typeFragment == null) {
                    layoutParams.setMargins(0, 0, 0, 0);
                    typeFragment = TypeFragment.newInstance();
                    typeFragment.setNavigationInterface(this);
                    typePresenter = new TypePresenter(typeFragment);
                    transaction.add(R.id.fragment_content, typeFragment);
                } else {
                    layoutParams.setMargins(0, 0, 0, 0);
                    transaction.show(typeFragment);
                }
                break;
            case 2:
                toolbarTitleView.setText(getString(R.string.recommendation));
                toolbarImageView.setImageDrawable(getDrawable(R.drawable.heart));
                if (recommendFragment == null) {
                    layoutParams.setMargins(0, 0, 0, 0);
                    recommendFragment = RecommendFragment.newInstance();
                    recommendFragment.setNavigationInterface(this);
                    recommendPresenter = new RecommendPresenter(recommendFragment);
                    transaction.add(R.id.fragment_content, recommendFragment);
                } else {
                    layoutParams.setMargins(0, 0, 0, 0);
                    transaction.show(recommendFragment);
                }
                break;
            case 3:
                toolbarTitleView.setText(getString(R.string.about));
                toolbarImageView.setImageDrawable(getDrawable(R.drawable.share));
                if (aboutFragment == null) {
                    layoutParams.setMargins(0, 0, 0, ViewTool.dip2px(this, Float.valueOf("56.0")));
                    aboutFragment = AboutFragment.newInstance();
                    transaction.add(R.id.fragment_content, aboutFragment);
                } else {
                    layoutParams.setMargins(0, 0, 0, ViewTool.dip2px(this, Float.valueOf("56.0")));
                    transaction.show(aboutFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            exitBy2Click();
        }
    }

    /**
     * TimerTask倒计时实现exit by 2 clicks
     */
    private void exitBy2Click() {
        Timer tExit = null;
        Toast exitToast = Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT);
        if (!isExiting) {
            isExiting = true; // 准备退出
            exitToast.show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExiting = false; // 取消退出
                }
            }, 1500); // 如果1.5秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            exitToast.cancel();
            super.onBackPressed();
        }
    }

    @Override
    public View getAppBar() {
        return appBar;
    }

    @Override
    public void hideNavigation() {
        bottomNavigation.hideBottomNavigation();

    }

    @Override
    public void showNavigation() {
        bottomNavigation.restoreBottomNavigation();
    }


    @Override
    public void setNotification(String title, int position) {
        bottomNavigation.setNotification(title, position);
    }

    @Override
    public void setNotification(String title) {
        setNotification(title, 2);
    }
}
