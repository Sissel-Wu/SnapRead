package sensation.snapread.model.service;

import okhttp3.MultipartBody;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;
import sensation.snapread.model.response.OcrResponse;

/**
 * OCR识别
 * Created by Alan on 2016/10/1.
 */


public interface OcrService {
    @Headers("Ocp-Apim-Subscription-Key: 9bab70d683ae4272b386042aaa222b56")
    @Multipart
    @POST("https://api.projectoxford.ai/vision/v1.0/ocr")
    Observable<OcrResponse> ocr(@Part MultipartBody.Part file);
}
