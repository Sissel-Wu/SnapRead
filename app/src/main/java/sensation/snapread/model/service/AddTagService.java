package sensation.snapread.model.service;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import sensation.snapread.model.response.Response;

/**
 * Created by Alan on 2016/10/9.
 */
public interface AddTagService {
    @GET("addTag")
    Observable<Response<Object>> addTag(@Query("user_id") String userID, @Query("tag_name") String name, @Query("description") String description, @Query("tag_img") String imgUrl);
}
