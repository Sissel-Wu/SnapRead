package sensation.snapread.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.github.piasy.rxscreenshotdetector.RxScreenshotDetector;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 符合截屏流程的抽象类
 * Created by Alan on 2016/12/5.
 */
public abstract class Snapable extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RxScreenshotDetector.start(getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        error(e);
                    }

                    @Override
                    public void onNext(String path) {
                        next(path);
                    }
                });
    }

    /**
     * 结束截屏服务流程，创建&进入新的activity
     */
    abstract public void end();

    /**
     * 在接收到截屏监听的路径后进行的业务逻辑
     *
     * @param path 图片路径
     * @return Snapable
     */
    abstract public void next(String path);

    /**
     * 获取截屏错误流程
     *
     * @param e 异常
     * @return Snapable
     */
    abstract public void error(Throwable e);
}
