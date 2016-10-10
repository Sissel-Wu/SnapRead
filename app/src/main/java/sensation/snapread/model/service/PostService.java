package sensation.snapread.model.service;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.ContentPO;

/**
 * Created by Alan on 2016/10/10.
 */
public interface PostService {
    @GET("getPost")
    Observable<Response<ContentPO>> getPost(@Query("post_id") String postID);
}
