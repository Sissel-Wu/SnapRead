package sensation.snapread.view.generate;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sensation.snapread.R;
import sensation.snapread.contract.GenerateContract;
import sensation.snapread.model.vopo.GenerateVO;
import sensation.snapread.model.vopo.PostVO;
import sensation.snapread.presenter.GeneratePresenter;
import sensation.snapread.view.main.MainActivity;

public class GenerateActivity extends AppCompatActivity implements GenerateContract.View {
    private static final int SELECT_PHOTO = 2, SELECT_PHOTO_TYPE = 3;
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
    MaterialDialog waitDialog, dialog;
    GenerateVO generateVO;
    ArrayAdapter<String> adapter;
    Uri mImageUri = null;
    GenerateContract.Presenter presenter;
    ImageView imageView1, imageView2, imageView3;
    View card1, card2, card3;
    String imgPath = "https://camo.githubusercontent.com/5f260ff56ba9dd4accf22a9572a9874556704bf9/687474703a2f2f75706c6f61642d696d616765732e6a69616e7368752e696f2f75706c6f61645f696d616765732f333037323536362d386564663232356566306266646263332e706e673f696d6167654d6f6772322f6175746f2d6f7269656e742f7374726970253743696d61676556696577322f322f772f31323430";

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, GenerateActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        ButterKnife.bind(this);

        presenter = new GeneratePresenter(this);
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
                    if (dialog == null) {
                        dialog = new MaterialDialog.Builder(GenerateActivity.this)
                                .title("新建标签")
                                .customView(R.layout.type_builder, true)
                                .positiveText("确认")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        View view = dialog.getCustomView();
                                        if (view != null) {
                                            TextView tagNameView = (TextView) view.findViewById(R.id.tag);
                                            String tagName = tagNameView.getText().toString();
                                            TextView tagDesView = (TextView) view.findViewById(R.id.des);
                                            String des = tagDesView.getText().toString();

                                            presenter.addType(tagName, des, imgPath);
                                        }
                                        dialog.dismiss();
                                    }
                                })
                                .negativeText("取消")
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        mTypeSpinner.setSelection(0);
                                        dialog.dismiss();
                                    }
                                })
                                .cancelable(true)
                                .canceledOnTouchOutside(false)
                                .show();

                        View customView = dialog.getCustomView();
                        if (customView != null) {
                            if (imageView1 == null) {
                                card1 = customView.findViewById(R.id.card1);
                                imageView1 = (ImageView) customView.findViewById(R.id.image1);
                            }
                            if (imageView2 == null) {
                                card2 = customView.findViewById(R.id.card2);
                                imageView2 = (ImageView) customView.findViewById(R.id.image2);
                            }
                            if (imageView3 == null) {
                                card3 = customView.findViewById(R.id.card3);
                                imageView3 = (ImageView) customView.findViewById(R.id.image3);
                            }
                            imageView1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    imgPath = "https://camo.githubusercontent.com/5f260ff56ba9dd4accf22a9572a9874556704bf9/687474703a2f2f75706c6f61642d696d616765732e6a69616e7368752e696f2f75706c6f61645f696d616765732f333037323536362d386564663232356566306266646263332e706e673f696d6167654d6f6772322f6175746f2d6f7269656e742f7374726970253743696d61676556696577322f322f772f31323430";
                                    clearElevation();
                                    card1.setElevation(18);
                                }
                            });
                            imageView2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    imgPath = "http://img.taopic.com/uploads/allimg/140226/234991-14022609204234.jpg";
                                    clearElevation();
                                    card2.setElevation(18);
                                }
                            });
                            imageView3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    imgPath = "http://img2.3lian.com/2014/f7/11/d/97.jpg";
                                    clearElevation();
                                    card3.setElevation(18);
                                }
                            });
                        }
                    } else {
                        dialog.show();
                    }
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

    private void clearElevation() {
        card1.setElevation(3);
        card2.setElevation(3);
        card3.setElevation(3);
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
    public void onBackPressed() {
        if (fullImageView.getVisibility() == View.VISIBLE) {
            AlphaAnimation anim = new AlphaAnimation(1, 0);
            anim.setDuration(300);
            fullImageView.startAnimation(anim);
            fullImageView.setVisibility(View.GONE);
        }
        super.onBackPressed();
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
        Glide.with(this).load(generateVO.getImgUrl()).into(imageView);

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
        mTypeSpinner.setSelection(0);
    }

    @Override
    public void showAddSuccess(String tagName) {
        Toast.makeText(GenerateActivity.this, "添加新标签成功!", Toast.LENGTH_SHORT).show();
        if (adapter != null) {
            adapter.remove("+ 创建新标签");
            adapter.add(tagName);
            adapter.add("+ 创建新标签");
            adapter.notifyDataSetChanged();
            mTypeSpinner.setSelection(adapter.getCount() - 2);
        }
    }

    @OnClick(R.id.generate_btn)
    void generate() {
        PostVO postVO = new PostVO(generateVO.getPostID(),
                mTitleView.getText().toString(),
                (String) mTypeSpinner.getSelectedItem(),
                generateVO.getContent(),
                generateVO.getImgUrl(),
                mUrlView.getText().toString());
        presenter.savePost(postVO, generateVO.getDescription());
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
        this.presenter = presenter;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
