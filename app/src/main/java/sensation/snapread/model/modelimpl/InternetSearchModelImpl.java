package sensation.snapread.model.modelimpl;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sensation.snapread.model.modelinterface.SearchModel;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.service.DeleteTagService;
import sensation.snapread.model.vopo.CollectionListPO;

/**
 * Created by Alan on 2016/10/10.
 */
public class InternetSearchModelImpl extends InternetModelImpl implements SearchModel {
    @Override
    public void getTagContent(Subscriber<Response<List<CollectionListPO>>> subscriber, String tagName) {
        subscriber.onNext(null);
    }

    @Override
    public void getSearchContent(Subscriber<Response<List<CollectionListPO>>> subscriber, String keyword) {
        subscriber.onNext(null);
    }


    @Override
    public void deleteTag(Subscriber<Response<Object>> subscriber, String tagID) {
        DeleteTagService deleteTagService = retrofit.create(DeleteTagService.class);
        deleteTagService.deleteTag(tagID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
