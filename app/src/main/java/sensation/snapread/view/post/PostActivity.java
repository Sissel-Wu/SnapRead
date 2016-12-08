package sensation.snapread.view.post;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import sensation.snapread.R;
import sensation.snapread.contract.PostContract;
import sensation.snapread.model.vopo.PostVO;
import sensation.snapread.presenter.PostPresenter;
import sensation.snapread.view.browser.BrowserActivity;
import sensation.snapread.view.widget.SwipeBackActivity;

public class PostActivity extends SwipeBackActivity implements PostContract.View {

    private static final String ARG_ID = "id";
    private static final String ARG_TITLE = "title";
    private static final String ARG_IMG = "image";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView mToolBarTitle;

    @BindView(R.id.title)
    TextView mTitleView;

    @BindView(R.id.image)
    ImageView mPostImageView;

    @BindView(R.id.image_background)
    ImageView mPostImageBackgroundView;

    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    @BindView(R.id.content)
    TextView mContentView;

    @BindView(R.id.url)
    TextView mUrlView;

    @BindView(R.id.type)
    TextView mTypeView;

    @BindView(R.id.web_content)
    WebView webView;

    PostVO postVO;
    String postID, title, imgUrl;
    PostContract.Presenter presenter;

    public static void startActivity(Activity activity, String postID, String title, String imgUrl) {
        Intent intent = new Intent(activity, PostActivity.class);
        intent.putExtra(ARG_ID, postID);
        intent.putExtra(ARG_TITLE, title);
        intent.putExtra(ARG_IMG, imgUrl);

        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in, R.anim.half_silde_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        presenter = new PostPresenter(this);
        ButterKnife.bind(this);

        preLoad();
        initViews();
    }

    private void initViews() {
        initToolbar();
        initScrollView();
        initContent();
    }

    private void initScrollView() {
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p);
        int screenWidth = p.x;
        int screenHeight = p.y;
        final Rect rect = new Rect(0, 0, screenWidth, screenHeight);
        int[] location = new int[2];
        mTitleView.getLocationInWindow(location);

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mTitleView.getLocalVisibleRect(rect)) {
                    toolbar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                } else {
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                return false;
            }
        });
    }

    private void preLoad() {
        Intent intent = getIntent();
        if (intent != null) {
            postID = intent.getStringExtra(ARG_ID);
            title = intent.getStringExtra(ARG_TITLE);
            imgUrl = intent.getStringExtra(ARG_IMG);
            if (!imgUrl.equals("")) {
                Glide.with(this).load(imgUrl).into(mPostImageView);
            } else {
                mPostImageView.setVisibility(View.GONE);
                mPostImageBackgroundView.setVisibility(View.VISIBLE);
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
            mToolBarTitle.setText(title);
            toolbar.setTitle(title);
            presenter.getPost(postID);
        }
    }

    private void initContent() {
        mTitleView.setText(title);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("UTF -8");
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                if (postVO != null) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, postVO.getTitle());
                    intent.putExtra(Intent.EXTRA_TEXT, postVO.getTitle() + "\n" + postVO.getUrl() + "\n\n--来自SnapRead");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(Intent.createChooser(intent, "分享内容到"));
                } else {
                    Toast.makeText(PostActivity.this, "网络错误，请重试", Toast.LENGTH_SHORT).show();
                }
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_broswer:
                if (postVO != null) {
                    BrowserActivity.activityStart(this, postVO.getUrl());
                } else {
                    Toast.makeText(PostActivity.this, "网络错误，请重试", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showPost(PostVO postVO) {
        this.postVO = postVO;
        mTypeView.setText(postVO.getType());
        String url = postVO.getUrl().split(".com/")[0] + ".com";
        mUrlView.setText(url);
        webView.loadUrl(postVO.getContent());
//        webView.loadData(postVO.getContent(), "text/html; charset=UTF-8", null);
    }

    @Override
    public void setPresenter(PostContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.half_slide_in, R.anim.slide_out);
    }
}
