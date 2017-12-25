package ${packageName}.mvp.view.activity;

import ${packageName}.R;
import ${packageName}.mvp.base.BaseActivity;
import ${packageName}.mvp.contract.${ContractName};
import ${packageName}.mvp.presenter.${PresenterName};
import android.os.Bundle;

public class ${ActivityName} extends BaseActivity<${PresenterName}> implements ${ContractName}.${IViewName}{

		
	@Override
    protected void initView() {
        super.initView();
		
    }
	
	@Override
    public int setContentViewId() {
        return R.layout.activity_${activity_layout};
    }

     @Override
     public void createPresenter() {
        mPresenter = new ${PresenterName}(mContext, this);
     }
	
	@Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
		
}
