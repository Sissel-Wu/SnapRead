package sensation.snapread.model.modelimpl;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sensation.snapread.model.modelinterface.PostModel;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.ContentPO;
import sensation.snapread.model.vopo.PostPO;

/**
 * Created by Alan on 2016/10/9.
 */
public class DBPostModelImpl implements PostModel {
    @Override
    public void getPost(Subscriber<Response<ContentPO>> subscriber, final String postID) {
        Observable.create(new Observable.OnSubscribe<Response<ContentPO>>() {
            @Override
            public void call(Subscriber<? super Response<ContentPO>> subscriber) {
                List<PostPO> postPOList = PostPO.where("postid = ?", postID).find(PostPO.class);
                PostPO postPO = postPOList.get(0);
                ContentPO contentPO = new ContentPO(postPO.getTitle(), postPO.getContent(), postPO.getUrl(),
                        postPO.getImgUrl(), postPO.getType());
                subscriber.onNext(new Response<ContentPO>("success", "", "", contentPO));
            }
        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
