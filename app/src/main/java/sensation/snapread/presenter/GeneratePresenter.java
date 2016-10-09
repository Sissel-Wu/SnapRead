package sensation.snapread.presenter;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
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
import sensation.snapread.model.RepositoryFactory;
import sensation.snapread.model.modelinterface.GenerateModel;
import sensation.snapread.model.response.OcrResponse;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.GeneratePO;
import sensation.snapread.model.vopo.LinePO;
import sensation.snapread.model.vopo.PostPO;
import sensation.snapread.model.vopo.PostVO;
import sensation.snapread.model.vopo.RegionPO;
import sensation.snapread.model.vopo.TypePO;
import sensation.snapread.model.vopo.WordPO;

/**
 * Created by Alan on 2016/9/29.
 */
public class GeneratePresenter implements GenerateContract.Presenter {

    ModelRepository repository;
    GenerateContract.View generateView;
    GenerateModel generateModel;

    public GeneratePresenter(GenerateContract.View view) {
        repository = RepositoryFactory.getInternetRepository();
        if (!repository.isConnected(MyApplication.getContext())) {
            generateView.showInternetError();
        }
        generateModel = repository.getGenerateModelImpl();
        generateView = view;
        generateView.setPresenter(this);
    }

    @Override
    public void start() {
        getTypes(PresenterCache.getInstance().getUserID());
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
                        generateView.hideLoading();
                        generateView.showImageError();
                    }
                });
    }


    @Override
    public void getTypes(String userID) {
        generateModel.getTypeList(new Subscriber<Response<List<TypePO>>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                generateView.showInternetError();
            }

            @Override
            public void onNext(Response<List<TypePO>> listResponse) {
                List<TypePO> data = listResponse.getData();
                List<String> types = new ArrayList<>();
                for (TypePO po : data) {
                    types.add(po.getTag_name());
                }
                generateView.showTypes(types);
            }
        }, userID);
    }

    @Override
    public void addType(final String typeName, String description, String imgPath) {
        generateModel.addType(new Subscriber<Response<Object>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                generateView.showInternetError();
            }

            @Override
            public void onNext(Response<Object> objectResponse) {
                generateView.showAddSuccess(typeName);
            }
        }, PresenterCache.getInstance().getUserID(), typeName, description, imgPath);
    }

    @Override
    public void searchPost(String keyword) {
        Log.d("SnapRead", "searchPost: " + keyword);
        generateModel.searchPost(new Subscriber<Response<GeneratePO>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                generateView.hideLoading();
                generateView.showInternetError();
            }

            @Override
            public void onNext(Response<GeneratePO> generatePOResponse) {
                generateView.hideLoading();
                generateView.showPost(generatePOResponse.getData().toVO());
            }
        }, keyword);
    }

    @Override
    public void savePost(PostVO postVO, String description) {
        generateView.showLoading("正在保存文章...");
        final PostPO postPO = new PostPO(postVO.getPostID(), postVO.getTitle(), postVO.getType(),
                postVO.getContent(), description, postVO.getImgUrl(), postVO.getUrl());
        generateModel.savePost(new Subscriber<Response<Object>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                generateView.hideLoading();
                generateView.saveFail();
            }

            @Override
            public void onNext(Response<Object> objectResponse) {
                generateView.hideLoading();
                generateView.saveSuccess();
                postPO.save();
            }
        }, PresenterCache.getInstance().getUserID(), postPO);
    }
}
