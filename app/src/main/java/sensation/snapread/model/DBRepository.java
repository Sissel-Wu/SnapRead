package sensation.snapread.model;

import android.content.Context;

import sensation.snapread.model.modelinterface.CollectionModel;
import sensation.snapread.model.modelinterface.GenerateModel;
import sensation.snapread.model.modelinterface.PostModel;
import sensation.snapread.model.modelinterface.RecommendModel;
import sensation.snapread.model.modelinterface.TypeModel;

/**
 * Created by Alan on 2016/10/9.
 */
public class DBRepository implements ModelRepository {
    @Override
    public boolean isConnected(Context context) {
        return true;
    }

    @Override
    public CollectionModel getCollectionModel() {
        return null;
    }

    @Override
    public GenerateModel getGenerateModelImpl() {
        return null;
    }

    @Override
    public PostModel getPostModelImpl() {
        return null;
    }

    @Override
    public TypeModel getTypeModel() {
        return null;
    }

    @Override
    public RecommendModel getRecommendModel() {
        return null;
    }
}
