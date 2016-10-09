package sensation.snapread.view.browser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import sensation.snapread.R;

public class BrowserActivity extends AppCompatActivity {
    private static final String ARG_URL = "url";
    String url;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.webview)
    WebView webView;

    public static void activityStart(Activity activity, String url) {
        Intent intent = new Intent(activity, BrowserActivity.class);
        intent.putExtra(ARG_URL, url);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        url = getIntent().getStringExtra(ARG_URL);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        initToolBar();
        initWebView();
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initWebView() {
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
    }
}
