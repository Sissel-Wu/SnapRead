package sensation.snapread.contract;

import java.util.List;

import sensation.snapread.BasePresenter;
import sensation.snapread.model.vopo.CollectionListItemVO;
import sensation.snapread.view.widget.BaseView;

/**
 * 收藏的接口
 * Created by Alan on 2016/9/8.
 */
public interface CollectionContract {
    interface View extends BaseView<Presenter> {

        void showCollections(List<CollectionListItemVO> collectionList);

        void showLoading();

        void hideLoading();

        void deleteSuccess();

        void deleteFail();

        void showInternetError();
    }

    interface Presenter extends BasePresenter {

        void loadCollections(String userID);

        void deleteCollection(List<String> postIDList);
    }
}
