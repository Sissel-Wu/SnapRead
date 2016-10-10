package sensation.snapread.presenter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import sensation.snapread.contract.RecommendContract;
import sensation.snapread.model.ModelRepository;
import sensation.snapread.model.RepositoryFactory;
import sensation.snapread.model.modelinterface.RecommendModel;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.CollectionListItemVO;
import sensation.snapread.model.vopo.CollectionListPO;

/**
 * Created by Alan on 2016/10/10.
 */
public class RecommendPresenter implements RecommendContract.Presenter {

    ModelRepository repository;
    RecommendModel recommendModel;
    RecommendContract.View view;

    public RecommendPresenter(RecommendContract.View view) {
        this.view = view;
        update();
        view.setPresenter(this);
    }

    @Override
    public void getRecommend(String userID) {
        update();
        view.showLoading();
        recommendModel.getRecommend(new Subscriber<Response<List<CollectionListPO>>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                view.hideLoading();
                view.showInternetError();
            }

            @Override
            public void onNext(Response<List<CollectionListPO>> listResponse) {
                List<CollectionListPO> data = listResponse.getData();
                List<CollectionListItemVO> recommendList = new ArrayList<>();
                for (CollectionListPO po : data) {
                    recommendList.add(po.toVO());
                }
                view.hideLoading();
                view.showList(recommendList);
            }
        }, userID);
    }

    @Override
    public void start() {
        getRecommend(PresenterCache.getInstance().getUserID());
    }

    private void update() {
        repository = RepositoryFactory.getInternetRepository();
        recommendModel = repository.getRecommendModel();
    }
}
