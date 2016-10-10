package sensation.snapread.model.modelimpl;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sensation.snapread.model.modelinterface.PostModel;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.service.PostService;
import sensation.snapread.model.vopo.ContentPO;

/**
 * Created by Alan on 2016/10/9.
 */
public class InternetPostModelImpl extends InternetModelImpl implements PostModel {
    @Override
    public void getPost(Subscriber<Response<ContentPO>> subscriber, String postID) {
        PostService postService = retrofit.create(PostService.class);
        postService.getPost(postID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
