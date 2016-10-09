package sensation.snapread.model.service;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import sensation.snapread.model.response.Response;

/**
 * Created by Alan on 2016/10/10.
 */
public interface SavePostService {
    @GET("editPost")
    Observable<Response<Object>> savePost(@Query("user_id") String userID,
                                          @Query("post_id") String postID,
                                          @Query("title") String title,
                                          @Query("content") String content,
                                          @Query("description") String description,
                                          @Query("post_url") String postUrl,
                                          @Query("img_url") String imgUrl,
                                          @Query("type") String type);
}
