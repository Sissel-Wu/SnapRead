package sensation.snapread.model.modelimpl;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sensation.snapread.model.modelinterface.CollectionModel;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.CollectionListPO;
import sensation.snapread.model.vopo.PostPO;

/**
 * Created by Alan on 2016/10/9.
 */
public class DBCollectionModelImpl implements CollectionModel {
    @Override
    public void getCollectionList(Subscriber<Response<List<CollectionListPO>>> subscriber, String userID) {
        Observable.create(new Observable.OnSubscribe<Response<List<CollectionListPO>>>() {
            @Override
            public void call(Subscriber<? super Response<List<CollectionListPO>>> subscriber) {
                List<PostPO> postList = PostPO.findAll(PostPO.class);
                List<CollectionListPO> collectionListList = new ArrayList<>();
                for (PostPO post : postList) {
                    collectionListList.add(post.toListPO());
                }
                subscriber.onNext(new Response<>("success", "", "", collectionListList));
            }
        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void deleteCollections(Subscriber<Response<Object>> subscriber, final List<String> collectionIDList) {
        subscriber.onNext(null);
    }
}
