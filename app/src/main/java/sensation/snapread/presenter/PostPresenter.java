package sensation.snapread.presenter;

import rx.Subscriber;
import sensation.snapread.contract.PostContract;
import sensation.snapread.model.ModelRepository;
import sensation.snapread.model.MyApplication;
import sensation.snapread.model.RepositoryFactory;
import sensation.snapread.model.modelinterface.PostModel;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.ContentPO;
import sensation.snapread.model.vopo.PostVO;

/**
 * Created by Alan on 2016/10/8.
 */
public class PostPresenter implements PostContract.Presenter {

    ModelRepository repository;
    PostModel postModel;
    PostContract.View postView;

    public PostPresenter(PostContract.View postView) {

        update();
        this.postView = postView;
        postView.setPresenter(this);
    }

    @Override
    public void getPost(final String postID) {
        update();
        postModel.getPost(new Subscriber<Response<ContentPO>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Response<ContentPO> contentPOResponse) {
                ContentPO contentPO = contentPOResponse.getData();
                PostVO postVO = new PostVO(postID, contentPO.getTitle(), contentPO.getType(),
                        contentPO.getContent(), contentPO.getPost_img(), contentPO.getPost_url());
                postView.showPost(postVO);
            }
        }, postID);
    }

    @Override
    public void start() {
    }

    private void update() {
        repository = RepositoryFactory.getProperRepository(MyApplication.getContext());
        postModel = repository.getPostModelImpl();
    }
}
