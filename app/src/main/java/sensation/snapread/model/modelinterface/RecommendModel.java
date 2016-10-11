package sensation.snapread.model.modelinterface;

import java.util.List;

import rx.Subscriber;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.CollectionListPO;

/**
 * Created by Alan on 2016/10/9.
 */
public interface RecommendModel {
    void getRecommend(Subscriber<Response<List<CollectionListPO>>> subscriber, String userID);
}
