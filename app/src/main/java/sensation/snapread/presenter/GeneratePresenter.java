package sensation.snapread.presenter;

import android.util.Log;

import java.io.File;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import sensation.snapread.contract.GenerateContract;
import sensation.snapread.model.ModelRepository;
import sensation.snapread.model.MyApplication;
import sensation.snapread.model.modelinterface.GenerateModel;
import sensation.snapread.model.response.OcrResponse;
import sensation.snapread.model.vopo.GenerateVO;
import sensation.snapread.model.vopo.LinePO;
import sensation.snapread.model.vopo.PostVO;
import sensation.snapread.model.vopo.RegionPO;
import sensation.snapread.model.vopo.WordPO;

/**
 * Created by Alan on 2016/9/29.
 */
public class GeneratePresenter implements GenerateContract.Presenter {

    GenerateContract.View generateView;
    GenerateModel generateModel;

    public GeneratePresenter(ModelRepository repository, GenerateContract.View view) {
        generateModel = repository.getGenerateModelImpl();
        generateView = view;
        generateView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void ocr(String path) {
        generateView.showLoading("正在识别...");
        File file = new File(path);
        Compressor
                .getDefault(MyApplication.getContext())
                .compressToFileAsObservable(file)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestBody);
                        generateModel.ocr(new Subscriber<OcrResponse>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onNext(OcrResponse ocrResponse) {
                                String content = "";
                                List<RegionPO> regionList = ocrResponse.getRegions();
                                for (RegionPO region : regionList) {
                                    List<LinePO> lineList = region.getLines();
                                    for (LinePO line : lineList) {
                                        List<WordPO> wordList = line.getWords();
                                        for (WordPO word : wordList) {
                                            content += word.getText();
                                        }
                                    }
                                }
                                int size = content.length();
                                int start = (int) (size * 0.2),
                                        end = (int) (size * 0.8);
                                searchPost(content.substring(start, end));
                            }
                        }, body);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        generateView.showImageError();
                    }
                });
    }


    @Override
    public void getTypes(String userID) {

    }

    @Override
    public void searchPost(String keyword) {
        Log.d("SnapRead", "searchPost: " + keyword);
        String content =
                "<!DOCTYPE html>" +
                        "<head><meta charset=\"utf-8\"></head>" +
                        "<html>\n" +
                        "<body>\n" +
                        "<p>\n" +
                        "test from W3School.com.cn：\n" +
                        "<img src=\"http://www.w3school.com.cn/i/w3school_logo_white.gif\" />\n" +
                        "</p>\n" +
                        "</body>\n" +
                        "</html>";
        generateView.showPost(new GenerateVO("01", "Test", "设计", content, "", "http://www.baidu.com"));
        generateView.hideLoading();
    }

    @Override
    public void savePost(PostVO postVO) {
        generateView.saveSuccess();
    }
}
