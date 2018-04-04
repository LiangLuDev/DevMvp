package com.luliang.devmvp.api;

import com.luliang.devmvp.mvp.bean.BookBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Liang_Lu on 2017/9/1.
 */

public interface DevMvpService {


    @GET(Url.BOOK_CLASSIFY)
    Observable<BookBean> bookClassify();

}
