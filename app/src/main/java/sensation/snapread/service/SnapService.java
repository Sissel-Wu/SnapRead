package sensation.snapread.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.piasy.rxscreenshotdetector.RxScreenshotDetector;

import java.util.Timer;
import java.util.TimerTask;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sensation.snapread.view.generate.GenerateActivity;

/**
 * 截屏监听服务
 * Created by Alan on 2016/9/29.
 */
public class SnapService extends Service {

    private String mPath;
    private MaterialDialog dialog;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RxScreenshotDetector.start(getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String path) {
                        mPath = path;
                        dialog = new MaterialDialog.Builder(getApplicationContext())
                                .title("SnapRead")
                                .titleColor(getResources().getColor(android.R.color.black))
                                .content("收藏这篇文章吗?")
                                .contentColor(getResources().getColor(android.R.color.darker_gray))
                                .positiveText("确认")
                                .backgroundColor(getResources().getColor(android.R.color.white))
                                .negativeText("取消")
                                .canceledOnTouchOutside(false)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                        startGenerate();
                                    }
                                })
                                .build();
                        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                        dialog.show();
                    }
                });
    }

    private void startGenerate() {
        final Intent intent = new Intent(SnapService.this, GenerateActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("imagePath", mPath);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getApplication().startActivity(intent);
            }
        }, 300);

    }
}
