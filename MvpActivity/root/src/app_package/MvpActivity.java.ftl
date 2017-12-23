package ${packageName}.mvp.view.activity;

import ${packageName}.R;
import ${packageName}.mvp.base.BaseActivity;
import ${packageName}.mvp.contract.${ContractName};
import ${packageName}.mvp.presenter.${PresenterName};
import android.os.Bundle;

public class ${ActivityName} extends BaseActivity implements ${ContractName}.${IViewName}{

	private ${PresenterName} m${PresenterName};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		m${PresenterName}=new ${PresenterName}(mContext,this);
	}
		
		
	@Override
    protected void initView() {
        super.initView();
		
    }
	
	@Override
    public int setContentViewId() {
        return R.layout.activity_${activity_layout};
    }
	
	@Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
		
}
