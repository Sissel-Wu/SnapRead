package sensation.snapread.view.widget;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

import java.io.ByteArrayOutputStream;

/**
 * 视图控制工具
 * Created by Alan on 2016/9/15.
 */
public class ViewTool {

    public final static String TYPE_SEARCH = "keyword", TYPE_TAG = "tag";

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

    /**
     * 检查EditText是否为空
     *
     * @param str
     * @return
     */
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

    /**
     * 隐藏显示动画
     *
     * @param view
     * @param inOrOut out-1 in-2
     * @param offset
     * @return
     */
    public static Animation getAnim(View view, int inOrOut, int offset) {
        float from, to;
        if (inOrOut == 1) {
            from = 0;
            if (view != null) {
                to = view.getHeight() + offset;
            } else {
                to = offset;
            }
        } else {
            if (view != null) {
                from = view.getHeight() + offset;
            } else {
                from = offset;
            }
            to = 0;
        }
        TranslateAnimation anim = new TranslateAnimation(0, 0, from, to);
        anim.setDuration(300);
        anim.setInterpolator(new DecelerateInterpolator());
        return anim;
    }

    /**
     * URI转实际地址
     *
     * @param context
     * @param uri
     * @return
     */
    public static String uri2path(Context context, Uri uri) {
        String path = "";
        String scheme = uri.getScheme();
        if (scheme == null)
            path = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            path = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        path = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return path;
    }

    /**
     * base64转drawable
     *
     * @param icon
     * @return
     */
    public static Drawable byteToDrawable(String icon) {

        byte[] img = Base64.decode(icon.getBytes(), Base64.DEFAULT);
        Bitmap bitmap;
        if (img != null) {


            bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bitmap);

            return drawable;
        }
        return null;

    }

    /**
     * drawable转base64
     *
     * @param drawable
     * @return
     */
    public static String drawableToByte(Drawable drawable) {

        if (drawable != null) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            int size = bitmap.getWidth() * bitmap.getHeight() * 4;

            // 创建一个字节数组输出流,流的大小为size
            ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
            // 设置位图的压缩格式，质量为100%，并放入字节数组输出流中
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            // 将字节数组输出流转化为字节数组byte[]
            byte[] imagedata = baos.toByteArray();

            String icon = Base64.encodeToString(imagedata, Base64.DEFAULT);
            return icon;
        }
        return null;
    }
}
