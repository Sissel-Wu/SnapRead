package sensation.snapread.contract;

import java.util.List;

import sensation.snapread.presenter.BasePresenter;
import sensation.snapread.model.vopo.TypeItemVO;
import sensation.snapread.view.widget.BaseView;

/**
 * 类别合同
 * Created by Alan on 2016/9/28.
 */
public interface TypeContract {
    interface View extends BaseView<Presenter> {

        void showLoading();

        void hideLoading();

        void setTypesList(List<TypeItemVO> typeList);

        void addSuccess();

        void addFail();

        void showInternetError();

        void showImageError();

    }

    interface Presenter extends BasePresenter {

        void getTypeList(String userID);

        void addType(String typeName, String description, String imgPath);

    }
}
