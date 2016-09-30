package sensation.snapread.presenter;

import sensation.snapread.contract.GenerateContract;
import sensation.snapread.model.InternetModelRepository;
import sensation.snapread.model.modelinterface.InternetGenerateModel;
import sensation.snapread.model.vopo.GenerateVO;

/**
 * Created by Alan on 2016/9/29.
 */
public class GeneratePresenter implements GenerateContract.Presenter {

    GenerateContract.View generateView;
    InternetGenerateModel generateModel;

    public GeneratePresenter(InternetModelRepository repository, GenerateContract.View view) {
        generateModel = repository.getGenerateModelImpl();
        generateView = view;
        generateView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void getTypes(String userID) {

    }

    @Override
    public void searchPost(String keyword) {

    }

    @Override
    public void savePost(GenerateVO generateVO) {

    }
}
