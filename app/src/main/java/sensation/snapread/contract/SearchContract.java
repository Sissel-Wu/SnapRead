package sensation.snapread.contract;

import java.util.List;

import sensation.snapread.presenter.BasePresenter;
import sensation.snapread.model.vopo.CollectionListItemVO;
import sensation.snapread.view.widget.BaseView;

/**
 * 搜索合同
 * Created by Alan on 2016/9/28.
 */
public interface SearchContract {
    interface View extends BaseView<Presenter> {

        void showSearchResult(List<CollectionListItemVO> searchList);

        void showLoading();

        void hideLoading();

        void deleteSuccess();

        void deleteFail();

        void noData();

        void showInternetError();

    }

    interface Presenter extends BasePresenter {

        void getSearchResult(String keyword, String type);

        void deleteTag(String tagID);
    }
}
