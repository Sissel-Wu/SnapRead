package sensation.snapread.model.modelimpl;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sensation.snapread.model.modelinterface.CollectionModel;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.service.CollectionListService;
import sensation.snapread.model.service.DeleteCollectionService;
import sensation.snapread.model.vopo.CollectionListPO;

/**
 * Created by Alan on 2016/9/27.
 */
public class InternetCollectionModelImpl extends InternetModelImpl implements CollectionModel {
    @Override
    public void getCollectionList(Subscriber<Response<List<CollectionListPO>>> subscriber, String userID) {
        CollectionListService collectionListService = retrofit.create(CollectionListService.class);
        collectionListService.getPostList(userID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void deleteCollections(Subscriber<Response<Object>> subscriber, List<String> collectionIDList) {
        DeleteCollectionService deleteCollectionService = retrofit.create(DeleteCollectionService.class);
        deleteCollectionService.deleteCollections(collectionIDList)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
