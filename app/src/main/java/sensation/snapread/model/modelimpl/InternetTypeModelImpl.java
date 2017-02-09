package sensation.snapread.model.modelimpl;

import com.google.gson.Gson;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sensation.snapread.model.modelinterface.TypeModel;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.service.AddTagService;
import sensation.snapread.model.service.TagService;
import sensation.snapread.model.vopo.AddTagPO;
import sensation.snapread.model.vopo.TypePO;

/**
 * Created by Alan on 2016/10/9.
 */
public class InternetTypeModelImpl extends InternetModelImpl implements TypeModel {

    @Override
    public void addType(Subscriber<Response<Object>> subscriber, AddTagPO addTagPO) {
        AddTagService addTagService = retrofit.create(AddTagService.class);
        Gson gson = new Gson();
        addTagService.addTag(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(addTagPO)))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void getTypeList(Subscriber<Response<List<TypePO>>> subscriber, String userID) {
        TagService tagService = retrofit.create(TagService.class);
        tagService.getTagList(userID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
