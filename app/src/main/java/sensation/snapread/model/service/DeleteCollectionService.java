package sensation.snapread.model.service;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import sensation.snapread.model.response.Response;

/**
 * Created by Alan on 2016/10/9.
 */
public interface DeleteCollectionService {
    @GET("deletePost")
    Observable<Response<Object>> deleteCollections(@Query("post_id") List<String> collectionIDList);
}
