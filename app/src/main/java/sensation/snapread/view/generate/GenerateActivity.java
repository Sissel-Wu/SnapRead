package sensation.snapread.view.generate;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
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

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sensation.snapread.R;
import sensation.snapread.contract.GenerateContract;
import sensation.snapread.model.vopo.GenerateVO;
import sensation.snapread.view.main.MainActivity;

public class GenerateActivity extends AppCompatActivity implements GenerateContract.View {
    private static final int SELECT_PHOTO = 2;

    @BindView(R.id.generate_btn)
    TextView mGenerateBtn;

    @BindView(R.id.title)
    EditText mTitleView;

    @BindView(R.id.url)
    EditText mUrlView;

    @BindView(R.id.type)
    Spinner mTypeSpinner;

    @BindView(R.id.content)
    EditText mContentView;

    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.image)
    ImageView imageView;

    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;

    float startY = 0, endY = 0;
    MaterialDialog waitDialog;
    GenerateVO generateVO;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        ButterKnife.bind(this);

        showLoading("正在识别...");
        init();
        initViews();
        List<String> list = new ArrayList<>();
        list.add("设计");
        list.add("学习");
        showTypes(list);
    }

    @Override
    public void showLoading(String info) {
        if (waitDialog == null) {
            waitDialog =
                    new MaterialDialog.Builder(this)
                            .title("请等待")
                            .cancelable(false)
                            .progress(true, 0)
                            .build();
        }
        waitDialog.setContent(info);
        waitDialog.show();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                hideLoading();
            }
        }, 1500);
    }

    public void hideLoading() {
        waitDialog.dismiss();
    }

    @Override
    public void showTypes(List<String> types) {
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeSpinner.setAdapter(adapter);
        adapter.add("+ 创建新标签");
    }

    @Override
    public void showPost(GenerateVO generateVO) {
        this.generateVO = generateVO;
        mTitleView.setText(generateVO.getTitle());
        mUrlView.setText(generateVO.getUrl());
        mContentView.setText(generateVO.getContent());
    }

    private void initViews() {

        initScrollView();
        initSpinner();
        initToolBar();

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

    private void init() {
        fetchFromPath();
        fetchFromMimeType();
    }

    /**
     * 从mimetype分享中获取uri
     */
    private void fetchFromMimeType() {
        Intent intent = getIntent();
        if (intent != null) {
            String action = intent.getAction();
            String type = intent.getType();

            if (type != null) {
                if (type.startsWith("image/")) {
                    Uri imageUri = null;
                    if (Intent.ACTION_VIEW.equals(action)) {
                        imageUri = intent.getData();
                    } else if (Intent.ACTION_SEND.equals(action)) {
                        imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                    }
                    if (imageUri != null) {
                        try {
                            imageView.setImageBitmap(BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri)));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
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
            Uri mImageUri = null;
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
                if (mImageUri != null) {
                    try {
                        imageView.setImageBitmap(BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri)));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(GenerateActivity.this, "图片已被删除啦~", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    @OnClick(R.id.generate_btn)
    void generate() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
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
