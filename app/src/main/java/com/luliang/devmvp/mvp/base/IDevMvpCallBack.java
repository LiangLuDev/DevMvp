package com.luliang.devmvp.mvp.base;

/**
 * Created by Liang_Lu on 2017/9/8.
 */

public interface IDevMvpCallBack<T> {

    void onSuccess(T t);

    void onFail(String reason);
}
