package sensation.snapread.presenter;

import java.util.ArrayList;
import java.util.List;

import sensation.snapread.contract.CollectionContract;
import sensation.snapread.model.InternetModelRepository;
import sensation.snapread.model.modelinterface.InternetCollectionModel;
import sensation.snapread.model.vopo.CollectionListItemVO;

/**
 * 加载收藏列表，进入列表详情
 * Created by Alan on 2016/9/8.
 */
public class CollectionPresenter implements CollectionContract.Presenter {

    InternetModelRepository repository;
    InternetCollectionModel collectionModel;
    CollectionContract.View collectionView;

    public CollectionPresenter(InternetModelRepository repository, CollectionContract.View collectionView) {
        this.repository = repository;
        collectionModel = repository.getCollectionModel();
        this.collectionView = collectionView;
        collectionView.setPresenter(this);
    }

    @Override
    public void loadCollections(String userID, int page) {
        //TODO 加载文章
    }

    @Override
    public void deleteCollection(List<String> postIDList) {
//TODO 删除收藏请求
    }

    @Override
    public void start() {
        loadDefaultCollections();
    }

    private void loadDefaultCollections() {
        //TODO 从SharedPreferrence拿历史用户ID数据
        final String imgUrl1 = "https://camo.githubusercontent.com/5f260ff56ba9dd4accf22a9572a9874556704bf9/687474703a2f2f75706c6f61642d696d616765732e6a69616e7368752e696f2f75706c6f61645f696d616765732f333037323536362d386564663232356566306266646263332e706e673f696d6167654d6f6772322f6175746f2d6f7269656e742f7374726970253743696d61676556696577322f322f772f31323430",
                imgUrl2 = "https://github.com/Clans/FloatingActionButton/raw/master/screenshots/main_screen.png";
        collectionView.showLoading();
        List<CollectionListItemVO> collectionList = new ArrayList<>();
        CollectionListItemVO vo1 = new CollectionListItemVO("001", "Design", "Material Design", "Material Design is popular nowadays", "http://www.baidu.com", imgUrl1),
                vo2 = new CollectionListItemVO("001", "Design", "Material Design", "Material Design is popular nowadays", "http://www.baidu.com", ""),
                vo3 = new CollectionListItemVO("001", "Design", "Material Design", "Material Design is popular nowadays", "http://www.baidu.com", ""),
                vo4 = new CollectionListItemVO("001", "Design", "Material Design", "Material Design is popular nowadays", "http://www.baidu.com", imgUrl2),
                vo5 = new CollectionListItemVO("001", "Design", "Material Design", "Material Design is popular nowadays", "http://www.baidu.com", "");
        collectionList.add(vo1);
        collectionList.add(vo2);
        collectionList.add(vo3);
        collectionList.add(vo4);
        collectionList.add(vo5);
        collectionView.showCollections(collectionList);
        collectionView.hideLoading();
    }
}