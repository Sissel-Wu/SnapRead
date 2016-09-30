package sensation.snapread.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import sensation.snapread.model.modelimpl.InternetCollectionModelImpl;
import sensation.snapread.model.modelimpl.InternetGenerateModelImpl;
import sensation.snapread.model.modelinterface.InternetCollectionModel;
import sensation.snapread.model.modelinterface.InternetGenerateModel;

/**
 * 创建数据服务的工厂类
 * Created by Alan on 2016/9/15.
 */
public class InternetRepository implements InternetModelRepository {
    private static InternetRepository internetRepository;

    private InternetRepository() {
    }

    public static InternetRepository getInstance() {
        if (internetRepository == null) {
            internetRepository = new InternetRepository();
        }
        return internetRepository;
    }

    @Override
    public boolean isConnected(Context context) {
        try {
            ConnectivityManager connectivity =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    // 判断当前网络是否已经连接
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            Log.d("Internet Error", e.toString());
        }
        return false;
    }

    @Override
    public InternetCollectionModel getCollectionModel() {
        return new InternetCollectionModelImpl();
    }

    @Override
    public InternetGenerateModel getGenerateModelImpl() {
        return new InternetGenerateModelImpl();
    }
}
