package sensation.snapread.model.modelimpl;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sensation.snapread.model.modelinterface.TypeModel;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.service.AddTagService;
import sensation.snapread.model.service.DeleteTagService;
import sensation.snapread.model.service.TagService;
import sensation.snapread.model.vopo.TypePO;

/**
 * Created by Alan on 2016/10/9.
 */
public class InternetTypeModelImpl extends InternetModelImpl implements TypeModel {

    @Override
    public void addType(Subscriber<Response<Object>> subscriber, String userID, String name, String description, String imgUrl) {
        AddTagService addTagService = retrofit.create(AddTagService.class);
        addTagService.addTag(imgUrl, userID, name, description)
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

    @Override
    public void deleteType(Subscriber<Response<Object>> subscriber, String tagID) {
        DeleteTagService deleteTagService = retrofit.create(DeleteTagService.class);
        deleteTagService.deleteTag(tagID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
