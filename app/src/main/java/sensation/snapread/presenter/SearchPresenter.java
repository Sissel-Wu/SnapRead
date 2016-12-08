package sensation.snapread.presenter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import sensation.snapread.contract.SearchContract;
import sensation.snapread.model.ModelRepository;
import sensation.snapread.model.RepositoryFactory;
import sensation.snapread.model.modeimpl_stub.SearchStub;
import sensation.snapread.model.modelinterface.SearchModel;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.CollectionListItemVO;
import sensation.snapread.model.vopo.CollectionListPO;
import sensation.snapread.view.widget.ViewTool;

/**
 * Created by Alan on 2016/10/8.
 */
public class SearchPresenter implements SearchContract.Presenter {

    ModelRepository repository;
    SearchModel searchModel;
    SearchContract.View searchView;

    public SearchPresenter(SearchContract.View searchView) {
        update();
        this.searchView = searchView;
        searchView.setPresenter(this);
    }

    @Override
    public void getSearchResult(String keyword, String type) {
        update();
        searchView.showLoading();
        if (type.equals(ViewTool.TYPE_TAG)) {
            searchModel.getTagContent(new Subscriber<Response<List<CollectionListPO>>>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    searchView.hideLoading();
                    searchView.showInternetError();
                }

                @Override
                public void onNext(Response<List<CollectionListPO>> listResponse) {
                    List<CollectionListPO> collectionList = listResponse.getData();
                    List<CollectionListItemVO> collectionListItem = new ArrayList<>();
                    for (CollectionListPO po : collectionList) {
                        collectionListItem.add(po.toVO());
                    }
                    searchView.hideLoading();
                    searchView.showSearchResult(collectionListItem);
                }
            }, keyword);
        } else {
            searchModel.getSearchContent(new Subscriber<Response<List<CollectionListPO>>>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    searchView.hideLoading();
                    searchView.showInternetError();
                }

                @Override
                public void onNext(Response<List<CollectionListPO>> listResponse) {
                    List<CollectionListPO> collectionList = listResponse.getData();
                    if (collectionList.size() == 0) {
                        searchView.noData();
                    } else {
                        List<CollectionListItemVO> collectionListItem = new ArrayList<>();
                        for (CollectionListPO po : collectionList) {
                            collectionListItem.add(po.toVO());
                        }
                        searchView.showSearchResult(collectionListItem);
                    }
                    searchView.hideLoading();
                }
            }, keyword);
        }
    }

    @Override
    public void deleteTag(String tagID) {
        repository = RepositoryFactory.getInternetRepository();
        searchModel = repository.getSearchModel();
        searchModel.deleteTag(new Subscriber<Response<Object>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                searchView.deleteFail();
            }

            @Override
            public void onNext(Response<Object> objectResponse) {
                if (objectResponse != null) {
                    searchView.deleteSuccess();
                } else {
                    searchView.deleteFail();
                }
            }
        }, tagID);
    }

    @Override
    public void start() {
    }

    private void update() {
//        repository = RepositoryFactory.getDBRepository();
//        searchModel = repository.getSearchModel();
        searchModel = new SearchStub();
    }
}
