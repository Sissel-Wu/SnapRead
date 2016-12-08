package sensation.snapread.contract;

import sensation.snapread.presenter.BasePresenter;
import sensation.snapread.model.vopo.PostVO;
import sensation.snapread.view.widget.BaseView;

/**
 * 文章合同
 * Created by Alan on 2016/10/7.
 */
public interface PostContract {
    interface View extends BaseView<Presenter> {

        void showPost(PostVO postVO);

    }

    interface Presenter extends BasePresenter {
        void getPost(String postID);
    }
}
