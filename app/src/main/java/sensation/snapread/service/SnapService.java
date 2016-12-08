package sensation.snapread.service;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.WindowManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Timer;
import java.util.TimerTask;

import sensation.snapread.view.generate.GenerateActivity;

/**
 * 截屏监听服务
 * Created by Alan on 2016/9/29.
 */
public class SnapService extends Snapable {

    private String mPath;

    @Override
    public void next(String path) {
        MaterialDialog dialog;
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
                        end();
                    }
                })
                .build();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }

    @Override
    public void end() {
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

    @Override
    public void error(Throwable e) {
        e.printStackTrace();
    }
}
