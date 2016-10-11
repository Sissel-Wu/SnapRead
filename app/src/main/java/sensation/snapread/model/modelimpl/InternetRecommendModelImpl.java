package sensation.snapread.model.modelimpl;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sensation.snapread.model.modelinterface.RecommendModel;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.service.RecommendService;
import sensation.snapread.model.vopo.CollectionListPO;

/**
 * Created by Alan on 2016/10/9.
 */
public class InternetRecommendModelImpl extends InternetModelImpl implements RecommendModel {
    @Override
    public void getRecommend(Subscriber<Response<List<CollectionListPO>>> subscriber, String userID) {
        RecommendService recommendService = retrofit.create(RecommendService.class);
        recommendService.getRecommend(userID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
