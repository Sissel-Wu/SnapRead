package sensation.snapread.contract;

import java.util.List;

import sensation.snapread.BasePresenter;
import sensation.snapread.view.widget.BaseView;
import sensation.snapread.model.vopo.CollectionListItemVO;

/**
 * 收藏的接口
 * Created by Alan on 2016/9/8.
 */
public interface CollectionContract {
    interface View extends BaseView<Presenter> {

        void showCollections(List<CollectionListItemVO> collectionList);

        void addCollections(List<CollectionListItemVO> collectionList);

        void showLoading();

        void hideLoading();

        void deleteCollection(List<CollectionListItemVO> deleteList);
    }

    interface Presenter extends BasePresenter {

        void loadCollections(String userID, int page);

        void deleteCollection(List<String> postIDList);
    }
}
