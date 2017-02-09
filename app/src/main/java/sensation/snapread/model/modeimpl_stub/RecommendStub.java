package sensation.snapread.model.modeimpl_stub;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import sensation.snapread.model.modelinterface.RecommendModel;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.CollectionListPO;

/**
 * Created by Alan on 2016/10/10.
 */
public class RecommendStub implements RecommendModel {
    @Override
    public void getRecommend(Subscriber<Response<List<CollectionListPO>>> subscriber, String userID) {
        String url4 = "https://www.zhihu.com/question/49354299",
                url5 = "https://3g.ishuo.cn/doc/xujwbiqf.html",
                url6 = "http://m.jiemian.com/article/629387.html";
        CollectionListPO collection1 = new CollectionListPO(
                "4",
                "为什么 Material Design 在国内没有被大量推行？",
                "设计",
                "对照国外高使用频率的app大多都采用了Material Design或类Material Design风格，而国内仅有像酷安，bilibili和网易云音乐，类似app完全或部分采取了MD设计，而QQ，微信等甚至还有iOS风格的残余？",
                "http://mmbiz.qpic.cn/mmbiz/XQcVPz66ISApeqZzzbv0wmibicHn2bHQ1yrZvPWNejibvTo4mwCGhYrGUkPoJCEpLxSichHv31CGRicwlf5AYT0CiaIg/640?tp=webp&wxfrom=5",
                url4);
        CollectionListPO collection2 = new CollectionListPO(
                "5",
                "城市在下雨，而我在想你",
                "城市",
                "这个秋夜，有雨敲窗。",
                "http://mmsns.qpic.cn/mmsns/zKBwDdu8U8Ggbq2hSHdIjyg9VPPMLNmnGB3XGqpq9G1AcTdeOtykWw/0?wx_lazy=1",
                url5);
        CollectionListPO collection3 = new CollectionListPO(
                "6",
                "“越野”让生活变得有情趣！",
                "生活",
                "但凡男人味十足的男人都喜欢越野，外人眼里很不理解，说什么瞎胡闹，浪费了钱财，还处处充满危险",
                "http://mmbiz.qpic.cn/mmbiz_jpg/LMlfPMR39LP3JP9ibibPLPOODibnLEFSnMRxrjjTAibQwU8CHbyugJsZAdaF0fIqeqh4rLSNRlXmjTibWg655ZD5Nicw/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1",
                url6);
        List<CollectionListPO> collectionListPOList = new ArrayList<>();
        collectionListPOList.add(collection1);
        collectionListPOList.add(collection2);
        collectionListPOList.add(collection3);
        subscriber.onNext(new Response<>("", "", "", collectionListPOList));
    }
}
