package sensation.snapread.model.modeimpl_stub;

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
        String url1 = "http://mp.weixin.qq.com/s?src=3&timestamp=1481176417&ver=1&signature=ylf6wmfnv1RRFpAXg2X4ROfRJPGOcy7*XrY5biHC6SJGoRMQNa5j7jtShJOIk0W1bT4N2ZWFYmRa2Vtqv6ecCNqgGUX8slYQK1e2gkYPtmnmv8RuCa3mZ1h40n19wD8rAW*NJfAYhzFVJfCVk6*Pcg==",
                url2 = "http://mp.weixin.qq.com/s?src=3&timestamp=1476091841&ver=1&signature=FKbzHVMuw9pLS9LYRk4GYJVUoeQ1BmHcw-i139NBzHq5H5Y2WKxaeXCVFEmM8JaWqPmTF7qtzYXAlT9stRZHois0VDFUxe986MaC-eJ6UCzjqK8tKKaarnoqng0UYGIJWxnBWch10eKax9rNrE9XdvEOnGdC6TNUb2bB*PZic7Q=";
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
        if (postID.equals("1")) {
            subscriber.onNext(new Response<ContentPO>("success", "", "", contentPO1));
        } else {
            subscriber.onNext(new Response<ContentPO>("success", "", "", contentPO2));
        }
    }
}
