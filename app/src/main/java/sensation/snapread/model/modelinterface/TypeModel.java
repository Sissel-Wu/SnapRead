package sensation.snapread.model.modelinterface;

import java.util.List;

import rx.Subscriber;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.AddTagPO;
import sensation.snapread.model.vopo.TypePO;

/**
 * Created by Alan on 2016/10/9.
 */
public interface TypeModel {
    void addType(Subscriber<Response<Object>> subscriber, AddTagPO addTagPO);

    void getTypeList(Subscriber<Response<List<TypePO>>> subscriber, String userID);

}
