package sensation.snapread.model.service;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.GeneratePO;

/**
 * Created by Alan on 2016/10/9.
 */
public interface GenerateService {
    @GET("searchPost")
    Observable<Response<GeneratePO>> searchPost(@Query("keyword") String keyword);
}
