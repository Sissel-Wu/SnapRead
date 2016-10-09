package sensation.snapread.model.modelinterface;

import java.util.List;

import rx.Subscriber;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.CollectionListPO;

/**
 * Created by Alan on 2016/9/28.
 */
public interface CollectionModel {

    void getCollectionList(Subscriber<Response<List<CollectionListPO>>> subscriber, String userID);

    void deleteCollections(Subscriber<Response<Object>> subscriber, List<String> collectionIDList);
}
