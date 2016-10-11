package sensation.snapread.model.modelimpl;

import java.util.List;

import rx.Subscriber;
import sensation.snapread.model.modelinterface.RecommendModel;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.CollectionListPO;

/**
 * Created by Alan on 2016/10/9.
 */
public class DBRecommendModelImpl implements RecommendModel {
    @Override
    public void getRecommend(Subscriber<Response<List<CollectionListPO>>> subscriber, String userID) {
        subscriber.onNext(null);
    }
}
