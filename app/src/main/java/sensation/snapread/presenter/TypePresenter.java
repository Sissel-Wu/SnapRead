package sensation.snapread.presenter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import sensation.snapread.contract.TypeContract;
import sensation.snapread.model.ModelRepository;
import sensation.snapread.model.MyApplication;
import sensation.snapread.model.RepositoryFactory;
import sensation.snapread.model.modelinterface.TypeModel;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.TypeItemVO;
import sensation.snapread.model.vopo.TypePO;

/**
 * Created by Alan on 2016/10/7.
 */
public class TypePresenter implements TypeContract.Presenter {

    ModelRepository repository;
    TypeModel typeModel;
    TypeContract.View view;

    public TypePresenter(TypeContract.View view) {
        update();
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void getTypeList(String userID) {
        view.showLoading();
        update();
        typeModel.getTypeList(new Subscriber<Response<List<TypePO>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.hideLoading();
                view.showInternetError();
            }

            @Override
            public void onNext(Response<List<TypePO>> listResponse) {
                List<TypePO> data = listResponse.getData();
                List<TypeItemVO> tagList = new ArrayList<>();
                for (TypePO po : data) {
                    tagList.add(po.toVO());
                }
                view.hideLoading();
                view.setTypesList(tagList);
            }
        }, userID);
    }

    @Override
    public void addType(String typeName, String description, String imgPath) {
        view.showLoading();
        update();
        typeModel.addType(new Subscriber<Response<Object>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                view.hideLoading();
                view.addFail();
            }

            @Override
            public void onNext(Response<Object> objectResponse) {
                if (objectResponse != null) {
                    view.addSuccess();
                } else {
                    view.addFail();
                }
                view.hideLoading();
            }
        }, PresenterCache.getInstance().getUserID(), typeName, description, imgPath);
    }


    @Override
    public void start() {
        getTypeList(PresenterCache.getInstance().getUserID());
    }

    private void update() {
        repository = RepositoryFactory.getProperRepository(MyApplication.getContext());
        typeModel = repository.getTypeModel();
    }
}
