package com.luliang.devmvp.mvp.contract;


import com.luliang.devmvp.mvp.base.IBasePresenter;
import com.luliang.devmvp.mvp.base.IBaseView;
import com.luliang.devmvp.mvp.bean.BookBean;

/**
 * Created by Liang_Lu on 2017/12/22.
 * Contract用于存放mvp三层接口类  放在一起便于管理，不用生成太多的类（因为model没有需要处理的数据，所以没有使用接口，可自行生成实现）
 */

public interface CBook {

    interface IPBook extends IBasePresenter {

        void pBook();
    }

    interface IVBook extends IBaseView {

        void vBookSuccess(BookBean bean);

        void vBookError(String reason);

    }
}
