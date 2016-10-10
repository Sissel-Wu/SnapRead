package sensation.snapread.model.modelimpl;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alan on 2016/9/17.
 */
public class InternetModelImpl {
    static final String BASE_URL = "http://120.27.117.222:8080/api/";
    static final int DEFAULT_TIMEOUT = 10;
    Retrofit retrofit;

    protected InternetModelImpl(String baseUrl) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    protected InternetModelImpl() {
        this(BASE_URL);
    }
}
