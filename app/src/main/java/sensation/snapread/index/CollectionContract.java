package sensation.snapread.index;

import java.util.List;

import sensation.snapread.BasePresenter;
import sensation.snapread.BaseView;
import sensation.snapread.data.PostTask;
import sensation.snapread.vopo.CollectionListItemVO;

/**
 * 收藏的接口
 * Created by Alan on 2016/9/8.
 */
public interface CollectionContract {
    interface View extends BaseView<Presenter> {

        void tabCollection();

        void tabRecommend();

        void tabUser();

        /*-----------------------------Collection Fragment-------------------------------------*/

        void showCollections(List<CollectionListItemVO> collectionList);

        void loadTypes(List<String> typeList);

        void showType(String type, List<CollectionListItemVO> collectionList);

        void showResult(String keyword, List<CollectionListItemVO> collectionList);

        void showLoading();

        void hideLoading();

        void showPost();

        /*-----------------------------Recommend Fragment-------------------------------------*/

        //TODO 推荐View接口

        /*-----------------------------User Fragment-------------------------------------*/

        //TODO 用户View接口
    }

    interface Presenter extends BasePresenter {

        void tabCollection();

        void tabRecommend();

        void tabUser();

        /*-----------------------------Collection Presenter-------------------------------------*/

        void showPost(PostTask postTask);

        void loadCollections();

        void loadTypes();

        void switchType(String type);

        void search(String keyword);

        /*-----------------------------Recommend Presenter-------------------------------------*/

        //TODO 推荐Presenter接口

         /*-----------------------------User Presenter-------------------------------------*/

        //TODO 用户Presenter接口
    }
}
