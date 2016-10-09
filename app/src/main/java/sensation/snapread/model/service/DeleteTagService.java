package sensation.snapread.model.service;

import retrofit2.http.GET;
import rx.Observable;
import sensation.snapread.model.response.Response;

/**
 * Created by Alan on 2016/10/9.
 */
public interface DeleteTagService {
    @GET("deleteTag")
    Observable<Response<Object>> deleteTag(String tagID);
}
