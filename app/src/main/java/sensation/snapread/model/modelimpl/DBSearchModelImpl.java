package sensation.snapread.model.modelimpl;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sensation.snapread.model.modelinterface.SearchModel;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.CollectionListPO;
import sensation.snapread.model.vopo.PostPO;

/**
 * Created by Alan on 2016/10/10.
 */
public class DBSearchModelImpl implements SearchModel {
    @Override
    public void getTagContent(Subscriber<Response<List<CollectionListPO>>> subscriber, final String tagName) {
        Observable.create(new Observable.OnSubscribe<Response<List<CollectionListPO>>>() {
            @Override
            public void call(Subscriber<? super Response<List<CollectionListPO>>> subscriber) {
                List<PostPO> postList = PostPO.where("type = ?", tagName).find(PostPO.class);
                List<CollectionListPO> collectionListPOList = new ArrayList<>();
                for (PostPO po : postList) {
                    collectionListPOList.add(po.toListPO());
                }
                subscriber.onNext(new Response<>("success", "", "", collectionListPOList));
            }
        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    @Override
    public void getSearchContent(Subscriber<Response<List<CollectionListPO>>> subscriber, final String keyword) {
        Observable.create(new Observable.OnSubscribe<Response<List<CollectionListPO>>>() {
            @Override
            public void call(Subscriber<? super Response<List<CollectionListPO>>> subscriber) {
                List<PostPO> postList = PostPO
                        .where("title like ? or content like ?", "%" + keyword + "%", "%" + keyword + "%")
                        .find(PostPO.class);
                List<CollectionListPO> collectionListPOList = new ArrayList<>();
                for (PostPO po : postList) {
                    collectionListPOList.add(po.toListPO());
                }
                subscriber.onNext(new Response<>("success", "", "", collectionListPOList));
            }
        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void deleteTag(Subscriber<Response<Object>> subscriber, String tagID) {
        subscriber.onNext(null);
    }
}
