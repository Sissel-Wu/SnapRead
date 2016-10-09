package sensation.snapread.model;

import sensation.snapread.model.modelinterface.CollectionModel;
import sensation.snapread.model.modelinterface.GenerateModel;
import sensation.snapread.model.modelinterface.PostModel;
import sensation.snapread.model.modelinterface.RecommendModel;
import sensation.snapread.model.modelinterface.TypeModel;

/**
 * Created by Alan on 2016/9/28.
 */
public interface ModelRepository extends Repository {
    CollectionModel getCollectionModel();

    GenerateModel getGenerateModelImpl();

    PostModel getPostModelImpl();

    TypeModel getTypeModel();

    RecommendModel getRecommendModel();
}
