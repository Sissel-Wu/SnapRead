package sensation.snapread.model;

import android.content.Context;
import android.widget.Toast;

/**
 * 获取连接
 * Created by Alan on 2016/9/15.
 */
public class RepositoryFactory {

    public static InternetRepository getInternetRepository() {
        return InternetRepository.getInstance();
    }

    public static DBRepository getDBRepository() {
        return DBRepository.getInstance();
    }

    /**
     * 获取合适的仓库，根据网络连接情况
     *
     * @param context 从界面传过来的context
     * @return
     */
    public static ModelRepository getProperRepository(Context context) {
        ModelRepository internetRepo = getInternetRepository(),
                dbRepo = getDBRepository();
        if (internetRepo.isConnected(context)) {
            return internetRepo;
        } else {
            Toast.makeText(context, "网络不通，正在使用本地数据,请检查网络设置~", Toast.LENGTH_SHORT).show();
            return dbRepo;
        }
    }
}
