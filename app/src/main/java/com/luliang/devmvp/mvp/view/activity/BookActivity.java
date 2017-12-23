package com.luliang.devmvp.mvp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.luliang.devmvp.R;
import com.luliang.devmvp.mvp.base.BaseActivity;
import com.luliang.devmvp.mvp.contract.CBook;
import com.luliang.devmvp.mvp.presenter.PBookImpl;


/**
 * Created by Liang_Lu on 2017/12/21.
 * V层 用于数据和页面UI展示（Fragment Dialog 同理）
 */

public class BookActivity extends BaseActivity implements CBook.IVBook {

    private PBookImpl mPBook;
    private TextView mTv;
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPBook = new PBookImpl(mContext, this);
    }

    @Override
    protected void initView() {
        super.initView();
        mBtn = findViewById(R.id.btn);
        mTv = findViewById(R.id.tv);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPBook.pBook();
            }
        });

    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_book;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void vBookSuccess(String result) {
        mTv.setText(result);
    }

    @Override
    public void vBookError(String reason) {
        mTv.setText(reason);
    }
}
