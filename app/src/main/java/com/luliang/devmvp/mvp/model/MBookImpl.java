package com.luliang.devmvp.mvp.model;


import com.luliang.devmvp.mvp.base.BaseModel;
import com.luliang.devmvp.utils.rxhelper.RxObservable;
import com.luliang.devmvp.utils.rxhelper.RxTransformer;

/**
 * Created by Liang_Lu on 2017/12/21.
 * model层用于处理数据,例：网络数据 数据库缓存数据 在此类处理以后返回给P层
 */

public class MBookImpl extends BaseModel {

    public void mBook(RxObservable rxObservable) {
        apiService()
                .bookClassify()
                .compose(RxTransformer.switchSchedulers(this))
                .subscribe(rxObservable);
    }


}

