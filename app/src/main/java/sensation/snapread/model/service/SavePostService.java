package sensation.snapread.model.service;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import sensation.snapread.model.response.Response;

/**
 * Created by Alan on 2016/10/10.
 */
public interface SavePostService {
    @POST("editPost")
    Observable<Response<Object>> savePost(@Body RequestBody body);
}
