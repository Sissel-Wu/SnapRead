package sensation.snapread.model.service;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.CollectionListPO;

/**
 * Created by Alan on 2016/10/10.
 */
public interface RecommendService {
    @GET("getUserRecommend")
    Observable<Response<List<CollectionListPO>>> getRecommend(@Query("user_id") String userID);
}
