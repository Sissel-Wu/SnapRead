package sensation.snapread.model.modelimpl;

import okhttp3.MultipartBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sensation.snapread.model.modelinterface.GenerateModel;
import sensation.snapread.model.response.OcrResponse;
import sensation.snapread.model.service.OcrService;

/**
 * Created by Alan on 2016/9/29.
 */
public class InternetGenerateModelImpl extends InternetModelImpl implements GenerateModel {

    private static final String ocrUrl = "https://api.projectoxford.ai/vision/v1.0/ocr/";

    public InternetGenerateModelImpl() {
        super(ocrUrl);
    }

    @Override
    public void ocr(Subscriber<OcrResponse> subscriber, MultipartBody.Part image) {
        OcrService ocrService = retrofit.create(OcrService.class);

        ocrService.ocr(image)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
