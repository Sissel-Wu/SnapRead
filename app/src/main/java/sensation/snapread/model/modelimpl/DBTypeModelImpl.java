package sensation.snapread.model.modelimpl;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sensation.snapread.model.modelinterface.TypeModel;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.TypePO;

/**
 * Created by Alan on 2016/10/9.
 */
public class DBTypeModelImpl implements TypeModel {

    @Override
    public void addType(Subscriber<Response<Object>> subscriber, String userID, String name, String description, String imgUrl) {
        subscriber.onNext(null);
    }

    @Override
    public void getTypeList(Subscriber<Response<List<TypePO>>> subscriber, String userID) {
        Observable.create(new Observable.OnSubscribe<Response<List<TypePO>>>() {
            @Override
            public void call(Subscriber<? super Response<List<TypePO>>> subscriber) {
                List<TypePO> typeList = TypePO.findAll(TypePO.class);
                subscriber.onNext(new Response<>("success", "", "", typeList));
            }
        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
