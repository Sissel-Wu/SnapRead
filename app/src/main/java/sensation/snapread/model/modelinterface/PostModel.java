package sensation.snapread.model.modelinterface;

import rx.Subscriber;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.ContentPO;

/**
 * Created by Alan on 2016/10/9.
 */
public interface PostModel {
    void getPost(Subscriber<Response<ContentPO>> subscriber, String postID);
}
