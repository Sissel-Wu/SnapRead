package sensation.snapread.model.modelimpl;

import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sensation.snapread.model.modelinterface.GenerateModel;
import sensation.snapread.model.response.OcrResponse;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.service.AddTagService;
import sensation.snapread.model.service.GenerateService;
import sensation.snapread.model.service.OcrService;
import sensation.snapread.model.service.SavePostService;
import sensation.snapread.model.service.TagService;
import sensation.snapread.model.vopo.AddTagPO;
import sensation.snapread.model.vopo.GeneratePO;
import sensation.snapread.model.vopo.SavePostPO;
import sensation.snapread.model.vopo.TypePO;

/**
 * Created by Alan on 2016/9/29.
 */
public class InternetGenerateModelImpl extends InternetModelImpl implements GenerateModel {

    private static final String ocrUrl = "https://api.projectoxford.ai/vision/v1.0/ocr/";

    @Override
    public void ocr(Subscriber<OcrResponse> subscriber, MultipartBody.Part image) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(ocrUrl)
                .build();

        OcrService ocrService = retrofit.create(OcrService.class);

        ocrService.ocr(image)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void getTypeList(Subscriber<Response<List<TypePO>>> subscriber, String userID) {
        TagService tagService = retrofit.create(TagService.class);
        tagService.getTagList(userID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void addType(Subscriber<Response<Object>> subscriber, AddTagPO addTagPO) {
        AddTagService addTagService = retrofit.create(AddTagService.class);
        Gson gson = new Gson();
        addTagService.addTag(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(addTagPO)))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void searchPost(Subscriber<Response<GeneratePO>> subscriber, String keyword) {
        GenerateService generateService = retrofit.create(GenerateService.class);
        generateService.searchPost(keyword)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    @Override
    public void savePost(Subscriber<Response<Object>> subscriber, SavePostPO savePostPO) {
        Gson gson = new Gson();
        String savePostStr = gson.toJson(savePostPO);
        SavePostService savePostService = retrofit.create(SavePostService.class);
        savePostService.savePost(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), savePostStr))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
