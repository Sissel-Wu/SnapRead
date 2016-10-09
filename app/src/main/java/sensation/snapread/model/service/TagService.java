package sensation.snapread.model.service;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.TypePO;

/**
 * Created by Alan on 2016/10/9.
 */
public interface TagService {
    @GET("getTag")
    Observable<Response<List<TypePO>>> getTagList(@Query("user_id") String userID);
}
