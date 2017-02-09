package sensation.snapread.model.modelinterface;

import java.util.List;

import okhttp3.MultipartBody;
import rx.Subscriber;
import sensation.snapread.model.response.OcrResponse;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.AddTagPO;
import sensation.snapread.model.vopo.GeneratePO;
import sensation.snapread.model.vopo.SavePostPO;
import sensation.snapread.model.vopo.TypePO;

/**
 * Created by Alan on 2016/9/29.
 */
public interface GenerateModel {
    void ocr(Subscriber<OcrResponse> subscriber, MultipartBody.Part image);

    void getTypeList(Subscriber<Response<List<TypePO>>> subscriber, String userID);

    void addType(Subscriber<Response<Object>> subscriber, AddTagPO addTagPO);

    void searchPost(Subscriber<Response<GeneratePO>> subscriber, String keyword);

    void savePost(Subscriber<Response<Object>> subscriber, SavePostPO savePostPO);
}
