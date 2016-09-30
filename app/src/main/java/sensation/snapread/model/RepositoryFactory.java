package sensation.snapread.model;

/**
 * 获取连接
 * Created by Alan on 2016/9/15.
 */
public class RepositoryFactory {

    public static InternetRepository getInternetRepository() {
        return InternetRepository.getInstance();
    }


    /**
     * 获取合适的仓库，根据网络连接情况
     *
     * @param context 从界面传过来的context
     * @return
     */
//    public static Repository getProperRepository(Context context) {
//        Repository internetRepo = getInternetRepository(),
//                dbRepo = getDBRepository();
//        if (internetRepo.isConnected(context)) {
//            return internetRepo;
//        } else {
//            return dbRepo;
//        }
//    }
}
