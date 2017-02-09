package sensation.snapread.service;

import android.content.Intent;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;
import sensation.snapread.view.generate.GenerateActivity;
import sensation.snapread.view.widget.WidgetTool;

/**
 * 截屏监听服务
 * Created by Alan on 2016/9/29.
 */
public class SnapService extends Snapable {

    private SweetAlertDialog dialog;
    private String mPath;

    @Override
    public void next(String path) {
        mPath = path;
//        MaterialDialog dialog;
//        dialog = new MaterialDialog.Builder(getApplicationContext())
//                .title("SnapRead")
//                .titleColor(getResources().getColor(android.R.color.black))
//                .content("收藏这篇文章吗?")
//                .contentColor(getResources().getColor(android.R.color.darker_gray))
//                .positiveText("确认")
//                .backgroundColor(getResources().getColor(android.R.color.white))
//                .negativeText("取消")
//                .canceledOnTouchOutside(false)
//                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        dialog.dismiss();
//                        end();
//                    }
//                })
//                .build();
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//        dialog.show();
        dialog =
                WidgetTool.getDialog(SnapService.this, "收藏这篇文章吗?", SweetAlertDialog.SUCCESS_TYPE, 0).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(final SweetAlertDialog sweetAlertDialog) {
                        dialog.dismiss();
                        end();
                    }
                })
                        .showCancelButton(true)
                        .setConfirmText("确认收藏")
                        .setCancelText("取消")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        });
        dialog.setCanceledOnTouchOutside(false);
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
