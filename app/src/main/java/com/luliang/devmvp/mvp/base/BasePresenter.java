package com.luliang.devmvp.mvp.base;


import android.content.Context;

import io.reactivex.disposables.Disposable;

/**
 * Created by Liang_Lu on 2017/12/23.
 */
public class BasePresenter<V extends IBaseView, M extends BaseModel> {
    protected V mView;
    protected M mModel;
    protected Context mContext;

    public BasePresenter(Context mContext, V mView, M mModel) {
        this.mView = mView;
        this.mModel = mModel;
        this.mContext = mContext;
    }

    public void onDestroy() {
        if (mModel!=null) {
            mModel.onDestroy();
        }
    }
}
