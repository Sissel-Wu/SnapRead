package sensation.snapread.presenter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import sensation.snapread.contract.CollectionContract;
import sensation.snapread.model.ModelRepository;
import sensation.snapread.model.modeimpl_stub.CollectionStub;
import sensation.snapread.model.modelinterface.CollectionModel;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.CollectionListItemVO;
import sensation.snapread.model.vopo.CollectionListPO;
import sensation.snapread.model.vopo.PostPO;

/**
 * 加载收藏列表，进入列表详情
 * Created by Alan on 2016/9/8.
 */
public class CollectionPresenter implements CollectionContract.Presenter {

    ModelRepository repository;
    CollectionModel collectionModel;
    CollectionContract.View collectionView;

    public CollectionPresenter(CollectionContract.View collectionView) {
        update();
        this.collectionView = collectionView;
        collectionView.setPresenter(this);
    }

    @Override
    public void loadCollections(String userID) {
        collectionView.showLoading();
        update();
        collectionModel.getCollectionList(new Subscriber<Response<List<CollectionListPO>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                collectionView.hideLoading();
                collectionView.showInternetError();
            }

            @Override
            public void onNext(Response<List<CollectionListPO>> listResponse) {
                List<CollectionListPO> data = listResponse.getData();
                List<CollectionListItemVO> collectionList = new ArrayList<>();
                for (CollectionListPO po : data) {
                    collectionList.add(po.toVO());
                }
                collectionView.showCollections(collectionList);
                collectionView.hideLoading();
            }
        }, userID);
    }

    @Override
    public void deleteCollection(final List<String> postIDList) {
        collectionView.showLoading();
        update();
        collectionModel.deleteCollections(new Subscriber<Response<Object>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                collectionView.hideLoading();
                collectionView.deleteFail();
            }

            @Override
            public void onNext(Response<Object> objectResponse) {
                collectionView.hideLoading();
                if (objectResponse != null) {
                    //删除本地数据
                    for (String id : postIDList) {
                        PostPO.deleteAll(PostPO.class, "postid = ?", id);
                    }
                    collectionView.deleteSuccess();
                } else {
                    collectionView.deleteFail();
                }
            }
        }, postIDList);
    }

    @Override
    public void start() {
//        loadCollections(PresenterCache.getInstance().getUserID());
        loadDefaultCollections();
    }

    private void loadDefaultCollections() {
        final String imgUrl1 = "https://camo.githubusercontent.com/5f260ff56ba9dd4accf22a9572a9874556704bf9/687474703a2f2f75706c6f61642d696d616765732e6a69616e7368752e696f2f75706c6f61645f696d616765732f333037323536362d386564663232356566306266646263332e706e673f696d6167654d6f6772322f6175746f2d6f7269656e742f7374726970253743696d61676556696577322f322f772f31323430",
                imgUrl2 = "https://github.com/Clans/FloatingActionButton/raw/master/screenshots/main_screen.png";
        collectionView.showLoading();
        List<CollectionListItemVO> collectionList = new ArrayList<>();
        CollectionListItemVO vo1 = new CollectionListItemVO("001", "Design", "Material Design", "Material Design is popular nowadays", "http://www.baidu.com", imgUrl1),
                vo2 = new CollectionListItemVO("001", "Design", "Material Design", "Material Design is popular nowadays", "http://www.baidu.com", ""),
                vo3 = new CollectionListItemVO("001", "Design", "Material Design", "Material Design is popular nowadays", "http://www.baidu.com", ""),
                vo4 = new CollectionListItemVO("001", "Design", "Material Design", "Material Design is popular nowadays", "http://www.baidu.com", imgUrl2),
                vo5 = new CollectionListItemVO("001", "Book", "Nice Book", "Material Design is popular nowadays", "http://www.baidu.com", "");
        collectionList.add(vo1);
        collectionList.add(vo2);
        collectionList.add(vo3);
        collectionList.add(vo4);
        collectionList.add(vo5);
        collectionView.showCollections(collectionList);
        collectionView.hideLoading();
    }

    /**
     * 更新网络和本地数据库连接状态
     */
    private void update() {
//        repository = RepositoryFactory.getProperRepository(MyApplication.getContext());
//        collectionModel = repository.getCollectionModel();
        collectionModel = new CollectionStub();
    }
}
