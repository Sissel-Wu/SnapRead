package sensation.snapread.model;

import android.content.Context;

/**
 * 仓库
 * Created by Alan on 2016/9/15.
 */
public interface Repository {
    /**
     * 是否连接网络
     *
     * @param context
     * @return 网络连接情况
     */
    boolean isConnected(Context context);
}
