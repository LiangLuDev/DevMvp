package com.luliang.devmvp.mvp.presenter;

import android.content.Context;

import com.luliang.devmvp.mvp.base.BasePresenter;
import com.luliang.devmvp.mvp.bean.BookBean;
import com.luliang.devmvp.mvp.contract.CBook;
import com.luliang.devmvp.mvp.model.MBookImpl;
import com.luliang.devmvp.utils.rxhelper.RxObservable;

import java.util.Map;

/**
 * Created by Liang_Lu on 2017/12/21.
 * P层 此类只用于处理业务逻辑 然后把最终的结果回调给V层
 */

public class PBookImpl extends BasePresenter<CBook.IVBook, MBookImpl> implements CBook.IPBook {


    public PBookImpl(Context mContext, CBook.IVBook mView) {
        super(mContext, mView, new MBookImpl());
    }


    @Override
    public void pBook() {
        mView.showLoading();
        mModel.mBook(new RxObservable<BookBean>() {

            @Override
            public void onSuccess(BookBean bean) {
                mView.hideLoading();
                mView.vBookSuccess(bean);
            }

            @Override
            public void onFail(String reason) {
                mView.hideLoading();
                mView.vBookError(reason);
            }
        });
    }
}
