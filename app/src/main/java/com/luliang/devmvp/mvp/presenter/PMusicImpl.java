package com.luliang.devmvp.mvp.presenter;

import android.content.Context;

import com.luliang.devmvp.mvp.base.BasePresenter;
import com.luliang.devmvp.mvp.contract.CMusic;
import com.luliang.devmvp.mvp.model.MMusicImpl;


/**
 * Created by Liang_Lu on 2017/12/21.
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

public class PMusicImpl extends BasePresenter<CMusic.IVMusic, MMusicImpl> implements CMusic.IPMusic {


    public PMusicImpl(Context mContext, CMusic.IVMusic mView) {
        super(mContext, mView, new MMusicImpl());
    }

}
