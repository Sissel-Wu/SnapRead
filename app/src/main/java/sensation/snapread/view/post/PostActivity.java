package sensation.snapread.view.post;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import butterknife.BindView;
import sensation.snapread.R;
import sensation.snapread.model.vopo.PostVO;

public class PostActivity extends AppCompatActivity {

    private static final String ARG_ID = "id";
    private static final String ARG_TITLE = "title";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    PostVO postVO;
    String postID,title;

    public static void startActivity(Activity activity, String postID, String title) {
        Intent intent = new Intent(activity,PostActivity.class);
        intent.putExtra(ARG_ID,postID);
        intent.putExtra(ARG_TITLE,title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        initViews();
    }

    private void initViews() {
        initToolbar();
        initContent();
    }

    private void initContent() {

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_post, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, postVO.getTitle());
            intent.putExtra(Intent.EXTRA_STREAM, postVO.getImgUrl());
            intent.putExtra(Intent.EXTRA_TEXT, postVO.getTitle() + "\n" + postVO.getUrl() + "\n\n--来自SnapRead");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Intent.createChooser(intent, "分享内容到"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
