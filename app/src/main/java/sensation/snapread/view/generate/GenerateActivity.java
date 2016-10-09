package sensation.snapread.view.generate;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sensation.snapread.R;
import sensation.snapread.contract.GenerateContract;
import sensation.snapread.model.RepositoryFactory;
import sensation.snapread.model.vopo.GenerateVO;
import sensation.snapread.model.vopo.PostPO;
import sensation.snapread.model.vopo.PostVO;
import sensation.snapread.presenter.GeneratePresenter;
import sensation.snapread.view.main.MainActivity;
import sensation.snapread.view.widget.ViewTool;

public class GenerateActivity extends AppCompatActivity implements GenerateContract.View {
    private static final int SELECT_PHOTO = 2;
    private static final String ARG_INTENT = "intent";

    @BindView(R.id.generate_btn)
    TextView mGenerateBtn;

    @BindView(R.id.title)
    EditText mTitleView;

    @BindView(R.id.url)
    EditText mUrlView;

    @BindView(R.id.type)
    Spinner mTypeSpinner;

    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.image)
    ImageView imageView;

    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;

    @BindView(R.id.image_overlay)
    ImageView imageOverlayView;

    @BindView(R.id.full_image)
    ImageView fullImageView;

    @BindView(R.id.web_content)
    WebView webView;

    float startY = 0, endY = 0;
    MaterialDialog waitDialog;
    GenerateVO generateVO;
    ArrayAdapter<String> adapter;
    Uri mImageUri = null;
    GenerateContract.Presenter presenter;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, GenerateActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        ButterKnife.bind(this);

        presenter = new GeneratePresenter(RepositoryFactory.getInternetRepository(), this);
        mGenerateBtn.requestFocus();
        initFetch();
        initViews();

        //stub
        List<String> list = new ArrayList<>();
        list.add("设计");
        list.add("学习");
        showTypes(list);
    }

    private void initFetch() {
        fetchFromPath();
        fetchFromMimeType();
    }

    private void initViews() {

        initScrollView();
        initSpinner();
        initToolBar();
        initImageOverlay();
        initFullImageView();
    }

    private void initFullImageView() {
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                fullImageView.setImageDrawable(imageView.getDrawable());
                if (fullImageView.getVisibility() == View.GONE) {
                    fullImageView.setVisibility(View.VISIBLE);
                    AlphaAnimation anim = new AlphaAnimation(0, 1);
                    anim.setDuration(300);
                    fullImageView.startAnimation(anim);
                }
                return true;
            }
        });
        fullImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fullImageView.getVisibility() == View.VISIBLE) {
                    AlphaAnimation anim = new AlphaAnimation(1, 0);
                    anim.setDuration(300);
                    fullImageView.startAnimation(anim);
                    fullImageView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initImageOverlay() {
        final AlphaAnimation anim = new AlphaAnimation(1, 0);
        anim.setDuration(500);
        imageOverlayView.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageOverlayView.startAnimation(anim);
                imageOverlayView.setVisibility(View.GONE);
            }
        }, 2500);
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initSpinner() {
        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == mTypeSpinner.getCount() - 1) {
                    new MaterialDialog.Builder(GenerateActivity.this)
                            .title("创建新标签")
                            .input("新建标签", "新建标签", false, new MaterialDialog.InputCallback() {
                                @Override
                                public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                                }
                            })
                            .positiveText("确认")
                            .negativeText("取消")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    if (adapter != null) {
                                        adapter.remove("+ 创建新标签");
                                        adapter.add(dialog.getInputEditText().getText().toString());
                                        adapter.add("+ 创建新标签");
                                        adapter.notifyDataSetChanged();
                                        mTypeSpinner.setSelection(adapter.getCount() - 2);
                                    }
                                    dialog.dismiss();
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    mTypeSpinner.setSelection(0);
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initScrollView() {
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (startY == 0.0) {
                            startY = event.getY();
                            if (startY == 0.0) {
                                startY = Float.valueOf("0.1");
                            }
                        }
                        endY = event.getY();
                        if (endY > startY + 5) {
                            if (mGenerateBtn.getVisibility() == View.GONE) {
                                mGenerateBtn.setVisibility(View.VISIBLE);
                                mGenerateBtn.startAnimation(anim(mGenerateBtn, 1));
                            }

                        } else if (endY < startY) {
                            if (mGenerateBtn.getVisibility() == View.VISIBLE) {
                                mGenerateBtn.startAnimation(anim(mGenerateBtn, 2));
                                mGenerateBtn.setVisibility(View.GONE);
                            }
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
    }

    /**
     * 从mimetype分享中获取uri
     */
    private void fetchFromMimeType() {
        String path = "";
        Intent intent = getIntent();
        if (intent != null) {
            String action = intent.getAction();
            String type = intent.getType();

            if (type != null) {
                if (type.startsWith("image/")) {
                    if (Intent.ACTION_VIEW.equals(action)) {
                        mImageUri = intent.getData();
                    } else if (Intent.ACTION_SEND.equals(action)) {
                        mImageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                    }
                    if (mImageUri != null) {
                        Cursor cursor = getContentResolver().query(mImageUri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
                        if (null != cursor) {
                            if (cursor.moveToFirst()) {
                                int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                                if (index > -1) {
                                    path = cursor.getString(index);
                                }
                            }
                            cursor.close();
                        }
                        if (!path.equals("")) {
                            presenter.ocr(path);
                        }

                    } else {
                        Toast.makeText(GenerateActivity.this, "图片已被删除啦~", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        }
    }

    /**
     * 从截屏监听中获取uri
     */
    private void fetchFromPath() {
        if (getIntent() != null) {
            String imagePath = getIntent().getStringExtra("imagePath");
            Uri mUri = Uri.parse("content://media/external/images/media");

            if (imagePath != null) {
                CursorLoader c = new CursorLoader(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
                        MediaStore.Images.Media.DEFAULT_SORT_ORDER);
                Cursor cursor = c.loadInBackground();
                cursor.moveToFirst();

                while (!cursor.isAfterLast()) {
                    String data = cursor.getString(cursor
                            .getColumnIndex(MediaStore.MediaColumns.DATA));
                    if (imagePath.equals(data)) {
                        int ringtoneID = cursor.getInt(cursor
                                .getColumnIndex(MediaStore.MediaColumns._ID));
                        mImageUri = Uri.withAppendedPath(mUri, "" + ringtoneID);

                        break;
                    }
                    cursor.moveToNext();
                }
                if (!imagePath.equals("")) {
                    presenter.ocr(imagePath);
                } else {
                    Toast.makeText(GenerateActivity.this, "图片已被删除啦~", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    @Override
    public void showLoading(String info) {
        if (waitDialog == null) {
            waitDialog =
                    new MaterialDialog.Builder(this)
                            .title("请等待")
                            .canceledOnTouchOutside(false)
                            .progress(true, 0)
                            .build();
        }
        waitDialog.setContent(info);
        waitDialog.show();
    }

    @Override
    public void hideLoading() {
        waitDialog.dismiss();
    }

    @Override
    public void showTypes(List<String> types) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeSpinner.setAdapter(adapter);
        adapter.add("+ 创建新标签");
    }

    @Override
    public void showPost(GenerateVO generateVO) {
        this.generateVO = generateVO;
        mTitleView.setText(generateVO.getTitle());
        mUrlView.setText(generateVO.getUrl());
        for (int i = 0; i < mTypeSpinner.getCount(); i++) {
            if (((String) mTypeSpinner.getItemAtPosition(i)).equals(generateVO.getType())) {
                mTypeSpinner.setSelection(i);
            }
        }
        if (!generateVO.getImgUrl().equals("")) {
            Glide.with(this).load(generateVO.getImgUrl()).into(imageView);
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.loadData(generateVO.getContent(), "text/html", "utf-8");
    }

    @Override
    public void saveSuccess() {
        Toast.makeText(GenerateActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void saveFail() {
        Toast.makeText(GenerateActivity.this, "保存失败，请检查网络~", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showImageError() {
        Toast.makeText(GenerateActivity.this, "图片加载失败，重新截个图~", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInternetError() {
        Toast.makeText(GenerateActivity.this, "网络有点问题哦~", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.generate_btn)
    void generate() {
        //添加到本地数据库
        String content = generateVO.getContent();
        String postID = generateVO.getPostID();
        String title = mTitleView.getText().toString();
        String url = mUrlView.getText().toString();
        String type = (String) mTypeSpinner.getSelectedItem();
        Drawable imageDrawable = imageView.getDrawable();
        PostPO postPO = new PostPO(postID, title, type, content, ViewTool.drawableToByte(imageDrawable), url);
        postPO.save();

        //发送网络请求
        PostVO postVO = new PostVO(postID, title, type, content, ViewTool.uri2path(this, mImageUri), url);
        presenter.savePost(postVO);
    }

    @OnClick(R.id.image)
    void changeImg() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "选择图片"), SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                try {
                    mImageUri = data.getData();
                    imageView.setImageBitmap(BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData())));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 出现消失的anim
     *
     * @param view
     * @param inOrOut in-1 out-2
     * @return
     */
    private AnimationSet anim(View view, int inOrOut) {
        float from, to, alphaIn, alphaOut;
        if (inOrOut == 1) {
            from = view.getHeight();
            to = 0;
            alphaIn = 0;
            alphaOut = 1;
        } else {
            to = view.getHeight();
            from = 0;
            alphaIn = 1;
            alphaOut = 0;
        }
        Animation translateAnim = new TranslateAnimation(0, 0, from, to);
        translateAnim.setInterpolator(new DecelerateInterpolator());
        Animation alphaAnim = new AlphaAnimation(alphaIn, alphaOut);
        AnimationSet set = new AnimationSet(true);
        set.setFillAfter(true);
        set.addAnimation(translateAnim);
        set.addAnimation(alphaAnim);
        set.setDuration(300);
        return set;
    }

    @Override
    public void setPresenter(GenerateContract.Presenter presenter) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
