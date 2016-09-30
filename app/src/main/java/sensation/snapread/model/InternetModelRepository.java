package sensation.snapread.model;

import sensation.snapread.model.modelinterface.InternetCollectionModel;
import sensation.snapread.model.modelinterface.InternetGenerateModel;

/**
 * Created by Alan on 2016/9/28.
 */
public interface InternetModelRepository extends Repository {
    InternetCollectionModel getCollectionModel();

    InternetGenerateModel getGenerateModelImpl();
}
