package sensation.snapread.model.modeimpl_stub;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import sensation.snapread.model.modelinterface.CollectionModel;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.CollectionListPO;

/**
 * Created by Alan on 2016/10/10.
 */
public class CollectionStub implements CollectionModel {
    @Override
    public void getCollectionList(Subscriber<Response<List<CollectionListPO>>> subscriber, String userID) {
        String url1 = "http://www.uisdc.com/comprehensive-material-design-note",
                url2 = "http://pianke.me/posts/57e1cc4801334dfc3b9f3e8d",
                url3 = "https://wapbaike.baidu.com/item/%E5%9F%8E%E5%B8%82%E7%9A%84%E8%AE%B0%E5%BF%86/2896859";
        CollectionListPO collection1 = new CollectionListPO(
                "1",
                "Material Design",
                "设计",
                "Material Design语言的一些重要功能包括 系统字体Roboto的升级版本 ",
                "http://mmbiz.qpic.cn/mmbiz/8cu01Kavc5YIicYZCerdL3icYAibag6BB1pia0BG2u4kAwuvyF74Mm42fdVpCoF863ZgOWgbfDB0k8QLoNP0tZaM4A/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1",
                url1);
        CollectionListPO collection2 = new CollectionListPO(
                "2",
                "生活 禅",
                "生活",
                "生活设计，实木水平遮阳百叶方形烤漆金属框架枕木水流造景原生玉兰花树木。",
                "",
                url2);
        CollectionListPO collection3 = new CollectionListPO(
                "3",
                "城市的记忆",
                "城市",
                "从那里出发，向东方走三天，你会到达迪奥米拉，这城市有六十个银色的圆屋顶，诸神的青铜塑像，铺铅板的街道，一个水晶剧场。",
                "http://mmbiz.qpic.cn/mmbiz_jpg/yu5zDAT988HP1ga0mPbOicibr9CPuIiaXiaA1ANB1KLH1SuaibrJvKNOkP7XauQm907FicjsHQ6sQfick4BkJjicbbby1w/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1",
                url3);
        List<CollectionListPO> collectionList = new ArrayList<>();
        collectionList.add(collection1);
        collectionList.add(collection2);
        collectionList.add(collection3);
        subscriber.onNext(new Response<>("success", "", "", collectionList));
    }

    @Override
    public void deleteCollections(Subscriber<Response<Object>> subscriber, List<String> collectionIDList) {
        subscriber.onNext(null);
    }
}
