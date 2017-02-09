package sensation.snapread.model.modeimpl_stub;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import sensation.snapread.model.modelinterface.PostModel;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.ContentPO;

/**
 * Created by Alan on 2016/10/10.
 */
public class PostStub implements PostModel {
    @Override
    public void getPost(Subscriber<Response<ContentPO>> subscriber, String postID) {
        String url1 = "http://www.uisdc.com/comprehensive-material-design-note",
                url2 = "http://pianke.me/posts/57e1cc4801334dfc3b9f3e8d",
                url3 = "https://wapbaike.baidu.com/item/%E5%9F%8E%E5%B8%82%E7%9A%84%E8%AE%B0%E5%BF%86/2896859",
                url4 = "https://www.zhihu.com/question/49354299",
                url5 = "https://3g.ishuo.cn/doc/xujwbiqf.html",
                url6 = "http://m.jiemian.com/article/629387.html";
        ContentPO contentPO1 = new ContentPO(
                "Material Design",
                url1,
                url1,
                "http://mmbiz.qpic.cn/mmbiz/8cu01Kavc5YIicYZCerdL3icYAibag6BB1pia0BG2u4kAwuvyF74Mm42fdVpCoF863ZgOWgbfDB0k8QLoNP0tZaM4A/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1",
                "设计"
        );
        ContentPO contentPO2 = new ContentPO(
                "生活 禅",
                url2,
                url2,
                "",
                "生活"
        );
        ContentPO contentPO3 = new ContentPO(
                "城市的记忆",
                url3,
                url3,
                "http://mmbiz.qpic.cn/mmbiz_jpg/yu5zDAT988HP1ga0mPbOicibr9CPuIiaXiaA1ANB1KLH1SuaibrJvKNOkP7XauQm907FicjsHQ6sQfick4BkJjicbbby1w/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1",
                "城市");
        ContentPO contentPO4 = new ContentPO(
                "为什么 Material Design 在国内没有被大量推行？",
                url4,
                url4,
                "http://mmbiz.qpic.cn/mmbiz/XQcVPz66ISApeqZzzbv0wmibicHn2bHQ1yrZvPWNejibvTo4mwCGhYrGUkPoJCEpLxSichHv31CGRicwlf5AYT0CiaIg/640?tp=webp&wxfrom=5",
                "设计");
        ContentPO contentPO5 = new ContentPO(
                "城市在下雨，而我在想你",
                url5,
                url5,
                "http://mmsns.qpic.cn/mmsns/zKBwDdu8U8Ggbq2hSHdIjyg9VPPMLNmnGB3XGqpq9G1AcTdeOtykWw/0?wx_lazy=1",
                "城市");
        ContentPO contentPO6 = new ContentPO(
                "“越野”让生活变得有情趣！",
                url6,
                url6,
                "http://mmbiz.qpic.cn/mmbiz_jpg/LMlfPMR39LP3JP9ibibPLPOODibnLEFSnMRxrjjTAibQwU8CHbyugJsZAdaF0fIqeqh4rLSNRlXmjTibWg655ZD5Nicw/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1",
                "生活");
        List<ContentPO> contentPOList = new ArrayList<>();
        contentPOList.add(contentPO1);
        contentPOList.add(contentPO2);
        contentPOList.add(contentPO3);
        contentPOList.add(contentPO4);
        contentPOList.add(contentPO5);
        contentPOList.add(contentPO6);

        int intPostID = Integer.parseInt(postID);
        subscriber.onNext(new Response<ContentPO>("success", "", "", contentPOList.get(intPostID - 1)));
    }
}
