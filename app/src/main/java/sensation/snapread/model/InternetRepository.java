package sensation.snapread.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import sensation.snapread.model.modelimpl.InternetCollectionModelImpl;
import sensation.snapread.model.modelimpl.InternetGenerateModelImpl;
import sensation.snapread.model.modelinterface.CollectionModel;
import sensation.snapread.model.modelinterface.GenerateModel;
import sensation.snapread.model.modelinterface.PostModel;
import sensation.snapread.model.modelinterface.RecommendModel;
import sensation.snapread.model.modelinterface.TypeModel;

/**
 * 创建数据服务的工厂类
 * Created by Alan on 2016/9/15.
 */
public class InternetRepository implements ModelRepository {
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
    public CollectionModel getCollectionModel() {
        return new InternetCollectionModelImpl();
    }

    @Override
    public GenerateModel getGenerateModelImpl() {
        return new InternetGenerateModelImpl();
    }

    @Override
    public PostModel getPostModelImpl() {
        return null;
    }

    @Override
    public TypeModel getTypeModel() {
        return null;
    }

    @Override
    public RecommendModel getRecommendModel() {
        return null;
    }
}
