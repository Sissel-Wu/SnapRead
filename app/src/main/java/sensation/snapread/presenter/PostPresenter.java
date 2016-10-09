package sensation.snapread.presenter;

import sensation.snapread.contract.PostContract;
import sensation.snapread.model.ModelRepository;

/**
 * Created by Alan on 2016/10/8.
 */
public class PostPresenter implements PostContract.Presenter {

    PostContract.View postView;

    public PostPresenter(ModelRepository repository, PostContract.View postView) {
        this.postView = postView;
        postView.setPresenter(this);
    }

    @Override
    public void getPost(String postID) {

    }

    @Override
    public void start() {

    }
}
