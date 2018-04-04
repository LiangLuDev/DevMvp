package ${packageName}.mvp.presenter;

import android.content.Context;

import ${packageName}.mvp.contract.${ContractName};
import ${packageName}.mvp.model.${ModelName};
import ${packageName}.mvp.base.BasePresenter;


/**
 * Created by Liang_Lu on 2017/12/21.
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

public class ${PresenterName} extends BasePresenter<${ContractName}.${IViewName}, ${ModelName}> implements ${ContractName}.${IPresenterName} {


    public ${PresenterName}(Context mContext, ${ContractName}.${IViewName} mView) {
        super(mContext, mView, new ${ModelName}());
    }

}
