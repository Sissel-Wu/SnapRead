package sensation.snapread.view.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 小部件的工具类
 * Created by Alan on 2016/3/12.
 */
public class WidgetTool {

    /**
     * 获得默认的ProgressDialog
     *
     * @param context 上下文对象
     * @return 默认的ProgressDialog
     */
    public static SweetAlertDialog getDefaultProgressDialog(Context context) {
        return getProgressDialog(context, "正在加载...");
    }

    public static SweetAlertDialog getProgressDialog(Context context, String content) {
        return getDialog(context, content, SweetAlertDialog.PROGRESS_TYPE, 0);
    }

    public static SweetAlertDialog getDialog(Context context, String content, int alertType, int imageResourceId) {
        SweetAlertDialog progressDialog = new SweetAlertDialog(context, alertType);
        if (alertType == SweetAlertDialog.CUSTOM_IMAGE_TYPE) {
            progressDialog.setCustomImage(imageResourceId);
        }
        if (alertType == SweetAlertDialog.PROGRESS_TYPE) {
            progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        }
        progressDialog.setTitleText(content);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public static int getHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        return height;
    }

    public static int getWidth(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int width = view.getMeasuredWidth();
        return width;
    }

}
