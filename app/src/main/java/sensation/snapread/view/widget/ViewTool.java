package sensation.snapread.view.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * 视图控制工具
 * Created by Alan on 2016/9/15.
 */
public class ViewTool {
    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    public static void hideFragments(@NonNull FragmentTransaction transaction, @NonNull Fragment... fragments) {
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                transaction.hide(fragment);
            }
        }
    }

    public static String checkNullEdit(String str) {
        if (str == null || !isNumber(str) || str.equals("")) {
            return "10";
        } else {
            return str;
        }
    }

    public static boolean isNumber(String str) {
        String reg = "^[0-9]*$";
        return str.matches(reg);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
