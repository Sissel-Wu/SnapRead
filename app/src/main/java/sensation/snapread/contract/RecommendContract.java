package sensation.snapread.contract;

import java.util.List;

import sensation.snapread.presenter.BasePresenter;
import sensation.snapread.model.vopo.CollectionListItemVO;
import sensation.snapread.view.widget.BaseView;

/**
 * 推荐合同
 * Created by Alan on 2016/9/28.
 */
public interface RecommendContract {
    interface View extends BaseView<Presenter> {
        void showLoading();

        void hideLoading();

        void showInternetError();

        void showList(List<CollectionListItemVO> recommendList);
    }

    interface Presenter extends BasePresenter {
        void getRecommend(String userID);
    }
}
