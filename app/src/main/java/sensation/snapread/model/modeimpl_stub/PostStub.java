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
        String url1 = "http://mp.weixin.qq.com/s?src=3&timestamp=1476091243&ver=1&signature=TJchTc2UqAZskmrq0NB3BpbKr6k3jrAgZj1em-tmfADVSzRTDi92I*WiZL6E2FjJjsFHZXPMeTGtElO-2s*IdtAXgzZUKHSKobWvZjwNXr8UIciGJ42lOGE-hz9qn9x4CQVQPjRZUbqL7si2N*mlqg==",
                url2 = "http://mp.weixin.qq.com/s?src=3&timestamp=1476091841&ver=1&signature=FKbzHVMuw9pLS9LYRk4GYJVUoeQ1BmHcw-i139NBzHq5H5Y2WKxaeXCVFEmM8JaWqPmTF7qtzYXAlT9stRZHois0VDFUxe986MaC-eJ6UCzjqK8tKKaarnoqng0UYGIJWxnBWch10eKax9rNrE9XdvEOnGdC6TNUb2bB*PZic7Q=";
        ContentPO contentPO1 = new ContentPO(
                "Material Design",
                url1,
                url1,
                "https://camo.githubusercontent.com/5f260ff56ba9dd4accf22a9572a9874556704bf9/687474703a2f2f75706c6f61642d696d616765732e6a69616e7368752e696f2f75706c6f61645f696d616765732f333037323536362d386564663232356566306266646263332e706e673f696d6167654d6f6772322f6175746f2d6f7269656e742f7374726970253743696d61676556696577322f322f772f31323430",
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
