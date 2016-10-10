package sensation.snapread.model.modelinterface;

import java.util.List;

import rx.Subscriber;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.CollectionListPO;

/**
 * Created by Alan on 2016/10/10.
 */
public interface SearchModel {
    void getTagContent(Subscriber<Response<List<CollectionListPO>>> subscriber, String tagName);

    void getSearchContent(Subscriber<Response<List<CollectionListPO>>> subscriber, String keyword);

    void deleteTag(Subscriber<Response<Object>> subscriber, String tagID);
}
