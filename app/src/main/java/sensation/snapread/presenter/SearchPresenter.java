package sensation.snapread.presenter;

import java.util.ArrayList;
import java.util.List;

import sensation.snapread.contract.SearchContract;
import sensation.snapread.model.ModelRepository;
import sensation.snapread.model.vopo.CollectionListItemVO;

/**
 * Created by Alan on 2016/10/8.
 */
public class SearchPresenter implements SearchContract.Presenter {

    SearchContract.View searchView;

    public SearchPresenter(ModelRepository repository, SearchContract.View searchView) {
        this.searchView = searchView;
        searchView.setPresenter(this);
    }

    @Override
    public void getSearchResult(String keyword, String type) {
        searchView.showLoading();
        final String imgUrl1 = "https://camo.githubusercontent.com/5f260ff56ba9dd4accf22a9572a9874556704bf9/687474703a2f2f75706c6f61642d696d616765732e6a69616e7368752e696f2f75706c6f61645f696d616765732f333037323536362d386564663232356566306266646263332e706e673f696d6167654d6f6772322f6175746f2d6f7269656e742f7374726970253743696d61676556696577322f322f772f31323430",
                imgUrl2 = "https://github.com/Clans/FloatingActionButton/raw/master/screenshots/main_screen.png";
        searchView.showLoading();
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
        searchView.showSearchResult(collectionList);
        searchView.hideLoading();
    }

    @Override
    public void start() {
    }
}
