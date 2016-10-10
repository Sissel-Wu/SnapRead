package sensation.snapread.contract;

import java.util.List;

import sensation.snapread.BasePresenter;
import sensation.snapread.model.vopo.GenerateVO;
import sensation.snapread.model.vopo.PostVO;
import sensation.snapread.view.widget.BaseView;

/**
 * 生成合同
 * Created by Alan on 2016/9/28.
 */
public interface GenerateContract {
    interface View extends BaseView<Presenter> {
        void showLoading(String info);

        void hideLoading();

        void showTypes(List<String> types);

        void showPost(GenerateVO generateVO);

        void saveSuccess();

        void saveFail();

        void showImageError();

        void showInternetError();

        void showAddSuccess(String typeName);
    }

    interface Presenter extends BasePresenter {

        void ocr(String path);

        void getTypes(String userID);

        void addType(String typeName, String description, String imgPath);

        void searchPost(String postID);

        void savePost(PostVO postVO, String description);
    }
}
