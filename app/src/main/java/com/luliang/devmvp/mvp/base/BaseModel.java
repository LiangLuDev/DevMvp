package com.luliang.devmvp.mvp.base;

import com.luliang.devmvp.api.DevMvpApi;
import com.luliang.devmvp.api.DevMvpService;
import com.luliang.devmvp.utils.LogUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Liang_Lu on 2017/12/21.
 */

public abstract class BaseModel {


    public CompositeDisposable mDisposable = new CompositeDisposable();

    /**
     * 初始化调用网络请求
     * @return
     */
    public DevMvpService apiService() {
        return DevMvpApi.createApi().create(DevMvpService.class);
    }
    /**
     * 取消网络请求
     */
    public void onDestroy() {

        if (mDisposable != null) {
            mDisposable.dispose();
            mDisposable.clear();
        }
    }
}
