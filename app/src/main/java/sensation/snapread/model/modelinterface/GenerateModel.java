package sensation.snapread.model.modelinterface;

import java.util.List;

import okhttp3.MultipartBody;
import rx.Subscriber;
import sensation.snapread.model.response.OcrResponse;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.GeneratePO;
import sensation.snapread.model.vopo.PostPO;
import sensation.snapread.model.vopo.TypePO;

/**
 * Created by Alan on 2016/9/29.
 */
public interface GenerateModel {
    void ocr(Subscriber<OcrResponse> subscriber, MultipartBody.Part image);

    void getTypeList(Subscriber<Response<List<TypePO>>> subscriber, String userID);

    void addType(Subscriber<Response<Object>> subscriber, String userID, String name, String description, String imageUrl);

    void searchPost(Subscriber<Response<GeneratePO>> subscriber, String keyword);

    void savePost(Subscriber<Response<Object>> subscriber, String userID, PostPO PostPO);
}
