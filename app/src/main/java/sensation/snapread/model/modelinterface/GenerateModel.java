package sensation.snapread.model.modelinterface;

import okhttp3.MultipartBody;
import rx.Subscriber;
import sensation.snapread.model.response.OcrResponse;

/**
 * Created by Alan on 2016/9/29.
 */
public interface GenerateModel {
    void ocr(Subscriber<OcrResponse> subscriber, MultipartBody.Part image);
}
