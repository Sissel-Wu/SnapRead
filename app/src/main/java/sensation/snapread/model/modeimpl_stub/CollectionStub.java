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
        String url1 = "http://mp.weixin.qq.com/s?src=3&timestamp=1476091243&ver=1&signature=TJchTc2UqAZskmrq0NB3BpbKr6k3jrAgZj1em-tmfADVSzRTDi92I*WiZL6E2FjJjsFHZXPMeTGtElO-2s*IdtAXgzZUKHSKobWvZjwNXr8UIciGJ42lOGE-hz9qn9x4CQVQPjRZUbqL7si2N*mlqg==",
                url2 = "http://mp.weixin.qq.com/s?src=3&timestamp=1476091841&ver=1&signature=FKbzHVMuw9pLS9LYRk4GYJVUoeQ1BmHcw-i139NBzHq5H5Y2WKxaeXCVFEmM8JaWqPmTF7qtzYXAlT9stRZHois0VDFUxe986MaC-eJ6UCzjqK8tKKaarnoqng0UYGIJWxnBWch10eKax9rNrE9XdvEOnGdC6TNUb2bB*PZic7Q=",
                url3 = "http://mp.weixin.qq.com/s?src=3&timestamp=1476091862&ver=1&signature=7bv*mIDRQB8Id2VSw0akrdI*wI-bIWRkVOrPOQFNL5svgaOWly9C4AvfTasvGLEBXQOPZp7NYyxCscLKcejj0cJ7EyhTzZ0s4ICFUL*3eaxJD0G7wVPFWjqck8Di5HheUsNJtZ2fzFDNdb9LMsUB1ZiJFm3BpyXmJ6IWAwanPr0=";
        CollectionListPO collection1 = new CollectionListPO(
                "1",
                "Material Design",
                "设计",
                "Material Design语言的一些重要功能包括 系统字体Roboto的升级版本 ",
                "https://camo.githubusercontent.com/5f260ff56ba9dd4accf22a9572a9874556704bf9/687474703a2f2f75706c6f61642d696d616765732e6a69616e7368752e696f2f75706c6f61645f696d616765732f333037323536362d386564663232356566306266646263332e706e673f696d6167654d6f6772322f6175746f2d6f7269656e742f7374726970253743696d61676556696577322f322f772f31323430",
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
                "城市与记忆",
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
