package sensation.snapread.view;

import android.view.View;

/**
 * activity隐藏和显示Floating Button和ToolBar的接口
 * Created by Alan on 2016/9/23.
 */
public interface NavigationInterface {
    View getAppBar();

    void hideNavigation();

    void showNavigation();

    void setNotification(String title, int position);

    /**
     * 默认在推荐tab显示
     *
     * @param title
     */
    void setNotification(String title);
}
