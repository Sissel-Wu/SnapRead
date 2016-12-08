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
        String url1 = "http://mp.weixin.qq.com/s?src=3&timestamp=1476096565&ver=1&signature=NqC3FuLyuFhEnWvCZPlL92UiGg4jsOwmtVn0UON2YGglnAMfQrgg7DAb2FgfiozvKLzPIj0RKBZg-dKJMRI0V21ljmlF-wKnBByygEna*NgXVBgbZdfhoNa8x8V0NN9zsFpxi6GhUpRn8cv47r12Sw==",
                url2 = "http://mp.weixin.qq.com/s?src=3&timestamp=1476096658&ver=1&signature=XZ9zR8rqGdJufQTL9gbtM*d1s20XBIlk2u8tdtd9XX*urNyI7bNGAvDUCN2YECNAr0RrirdjNlU6aXnGYqPqM85WIopvfumPKpK0rstiJhA6q-yTtenWTwIKWXNoE2wMJUrN1D8jZnKaEv-UJxSJ8NOjS5slRONxs3fBfA9xIQ0=",
                url3 = "http://mp.weixin.qq.com/s?src=3&timestamp=1476096586&ver=1&signature=pPMS2YaiedmMkcOzdsb4pv6rFv4exIwJ1XUaYJ3EsDtbV-yWYUvonoKE2EOGWZTcZfEdlj7Aisfs-EIgiyHEJQH75stt9blk3O2RUaeCc5JldgtT*nnYNutRuNs3QF2bhJZhRM6p1wxgLoMpvDE2Kd5d9rqqWOdN*VqTj4t-azo=";
        CollectionListPO collection1 = new CollectionListPO(
                "1",
                "Material Design是什么鬼？",
                "设计",
                "谷歌的Material Design 发布已久，氢OS的设计也将借鉴这种设计语言的精髓。你能秒懂Material Design要求的Do/Don't吗？",
                "http://mmbiz.qpic.cn/mmbiz/XQcVPz66ISApeqZzzbv0wmibicHn2bHQ1yrZvPWNejibvTo4mwCGhYrGUkPoJCEpLxSichHv31CGRicwlf5AYT0CiaIg/640?tp=webp&wxfrom=5",
                url1);
        CollectionListPO collection2 = new CollectionListPO(
                "2",
                "城市在下雨，而我在想你",
                "城市",
                "这个秋夜，有雨敲窗。",
                "http://mmsns.qpic.cn/mmsns/zKBwDdu8U8Ggbq2hSHdIjyg9VPPMLNmnGB3XGqpq9G1AcTdeOtykWw/0?wx_lazy=1",
                url2);
        CollectionListPO collection3 = new CollectionListPO(
                "3",
                "越野生活",
                "生活",
                "越野车是摩托产品中一个特殊的群体。如果说跨骑车是“男人车”，那么，隶属于跨骑车系的越野车就是“男人中的男人”",
                "http://mmbiz.qpic.cn/mmbiz_jpg/LMlfPMR39LP3JP9ibibPLPOODibnLEFSnMRxrjjTAibQwU8CHbyugJsZAdaF0fIqeqh4rLSNRlXmjTibWg655ZD5Nicw/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1",
                url3);
        List<CollectionListPO> collectionListPOList = new ArrayList<>();
        collectionListPOList.add(collection1);
        collectionListPOList.add(collection2);
        collectionListPOList.add(collection3);
        subscriber.onNext(new Response<>("", "", "", collectionListPOList));
    }
}
