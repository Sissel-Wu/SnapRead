package sensation.snapread.model;

import android.content.Context;

import sensation.snapread.model.modelimpl.DBCollectionModelImpl;
import sensation.snapread.model.modelimpl.DBPostModelImpl;
import sensation.snapread.model.modelimpl.DBRecommendModelImpl;
import sensation.snapread.model.modelimpl.DBSearchModelImpl;
import sensation.snapread.model.modelimpl.DBTypeModelImpl;
import sensation.snapread.model.modelinterface.CollectionModel;
import sensation.snapread.model.modelinterface.GenerateModel;
import sensation.snapread.model.modelinterface.PostModel;
import sensation.snapread.model.modelinterface.RecommendModel;
import sensation.snapread.model.modelinterface.SearchModel;
import sensation.snapread.model.modelinterface.TypeModel;

/**
 * Created by Alan on 2016/10/9.
 */
public class DBRepository implements ModelRepository {

    private static DBRepository dbRepository;

    private DBRepository() {
    }

    public static DBRepository getInstance() {
        if (dbRepository == null) {
            dbRepository = new DBRepository();
        }
        return dbRepository;
    }

    @Override
    public boolean isConnected(Context context) {
        return true;
    }

    @Override
    public CollectionModel getCollectionModel() {
        return new DBCollectionModelImpl();
    }

    @Override
    public GenerateModel getGenerateModelImpl() {
        return null;
    }

    @Override
    public PostModel getPostModelImpl() {
        return new DBPostModelImpl();
    }

    @Override
    public TypeModel getTypeModel() {
        return new DBTypeModelImpl();
    }

    @Override
    public RecommendModel getRecommendModel() {
        return new DBRecommendModelImpl();
    }

    @Override
    public SearchModel getSearchModel() {
        return new DBSearchModelImpl();
    }
}
