### DevMvp-快速开发框架

#### 序言
> 每个程序员看到一堆烂代码都有一颗重构的心。烂代码写起来嘴上 笑嘻嘻，心里mmp。特别是有代码洁癖的人。重构不易且行且珍惜，此框架将减少开发时间。如果你们的项目结构跟我的不一样，这也不用担心，你看了我这个，简单修改一下模板，照样能生成你想要的代码。
#### MVP架构
> 相信大家对于MVP耳熟能详了，理解的直接往下看，如果概念比较模糊，可以网上查一查理解理解，我这边简单的介绍一下，可以配合下图来理解
> - **View层：** View层也是视图层，只负责对数据的展示，提供友好的界面与用户进行交互。开发中通常将Activity或者Fragment作为View层。
> - **Model层：** Model层也是数据层。它区别于MVC架构中的Model，在这里Model它负责对数据的存取操作，例如对数据库的读写，网络的数据的请求等。
> - **Presenter层：** 是连接View层与Model层的桥梁并对业务逻辑进行处理。在MVP架构中Model与View无法直接进行交互。所以在Presenter层它会从Model层获得所需要的数据，进行一些适当的处理后交由View层进行显示。这样通过Presenter将View与Model进行隔离，使得View和Model之间不存在耦合，同时也将业务逻辑从View中抽离
![mvp.png](https://upload-images.jianshu.io/upload_images/2635045-504f48c823a5d21a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/600)
#### 项目介绍
> 项目采用MVP架构，使用RxAndroid2+Retrofit开源框架封装，结合Android Studio模板快速生成MVP基础代码。新项目或者重构项目值得拥有。此开发框架是我2017年底重构项目开发的，使用了几个月，项目重构完成了，完美使用，特别省事省时省心
#### 项目结构
```
DevMvp
    ├─api//URL、接口管理、网络请求封装类
    ├─mvp//项目主体
    │  ├─base//基础类封装
	   ├─bean//实体类
    │  ├─contract//契约类 用于统一管理view和presenter的接口
    │  ├─model//M层-数据处理
    │  ├─presenter//P层-逻辑业务处理
    │  └─view//V层-页面渲染
    │      ├─activity
		   ├─adapter
    │      └─fragment
		    ...//类似Dialog、PopupWindow也可以放在view下
    └─utils//工具类
        └─rxhelper//Rx封装工具
```
#### 代码详解
##### 1.api-网络请求
Retrofit网络请求封装，项目里面只是对Retrofit网络请求基本参数，需要header、cache等参数，可在网上查查资料，这类文章介绍很多。[Retrofit 2.0使用文档](https://blog.csdn.net/carson_ho/article/details/73732076)
- DevMvpApi -Retrofit初始化
```java
    public static Retrofit createApi() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder().
                connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);

        httpClientBuilder.addInterceptor(new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY));
        mRetrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();
        return mRetrofit;
    }
```
- DevMvpService-接口管理
这边直接使用BookBean对象接收返回值
```java
@GET(Url.BOOK_CLASSIFY)
Observable<BookBean> bookClassify();
```
##### 2.contract-契约类，v层和p层接口管理
- CBook-测试契约类
Contract用于存放mvp三层接口类  放在一起便于管理，不用生成太多的类（因为model没有需要处理的数据，所以没有使用接口，可自行生成实现）
```java
interface IPBook extends IBasePresenter {
        void pBook();
    }
interface IVBook extends IBaseView {
        void vBookSuccess(BookBean bean);
        void vBookError(String reason);
    }
```
##### 3.model-M层
- BaseModel-M层基类
 网络请求初始化和网络请求取消处理
```java
    public CompositeDisposable mDisposable = new CompositeDisposable();
    /**
     * 初始化调用网络请求
     * @return
     */
    public DevMvpService apiService() {
        return DevMvpApi.createApi().create(DevMvpService.class);
    }
    /**
     * 取消网络请求
     */
    public void onDestroy() {
        if (mDisposable != null) {
            mDisposable.isDisposed();
            mDisposable.clear();
        }
    }
```
- MBookImpl-M层数据处理
由于不需要对数据进行处理，我就直接返回给P层，如果数据可能会从数据库、缓存、网络获取，需要在这里进行处理。
```java
public void mBook(RxObservable rxObservable) {
        apiService()
                .bookClassify()
                .compose(RxTransformer.switchSchedulers(this))
                .subscribe(rxObservable);
    }

```
##### 4.presenter-P层
- BasePresenter-P层基类
连接V层和M层
```java
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
```

- PBookImpl-P层业务逻辑处理

```java
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
```
##### 5.view-V层
- BaseActivity-V层基类（BaseFragment类似）
在基类跟P层建立连接，这里可以根据项目需求丰富BaseActivity。这里只把添加基础必备功能。
```java
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    public Context mContext;
    public T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        if (setContentViewId() != 0) {
            setContentView(setContentViewId());
        } else {
            throw new RuntimeException("layoutResID==-1 have u create your layout?");
        }
        createPresenter();
        ButterKnife.bind(this);
        initView();
    }
    /**
     * 初始化方法
     */
    protected void initView() {
    }
    /**
     * 获取contentView 资源id
     */
    public abstract int setContentViewId();
    /**
     * 创建presenter实例
     */
    public abstract void createPresenter();
    /**
     * activity跳转（无参数）
     *
     * @param className
     */
    public void startActivity(Class<?> className) {
        Intent intent = new Intent(mContext, className);
        startActivity(intent);
    }
    /**
     * activity跳转（有参数）
     *
     * @param className
     */
    public void startActivity(Class<?> className, Bundle bundle) {
        Intent intent = new Intent(mContext, className);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();//页面销毁 网络请求同销毁
        }
    }
}
```
- BookActivity-V层页面渲染处理
V层只需要操作页面控件，网络数据显示到页面上等等。
```java
/**
 * Created by Liang_Lu on 2017/12/21.
 * V层 用于数据和页面UI展示（Fragment Dialog 同理）
 */
public class BookActivity extends BaseActivity<PBookImpl> implements CBook.IVBook {
    private TextView mTv;
    private Button mBtn;
    @Override
    protected void initView() {
        super.initView();
        mBtn = findViewById(R.id.btn);
        mTv = findViewById(R.id.tv);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.pBook();
            }
        });
    }
    @Override
    public int setContentViewId() {
        return R.layout.activity_book;
    }
    @Override
    public void createPresenter() {
        mPresenter = new PBookImpl(mContext, this);
    }
    @Override
    public void showLoading() {
    }
    @Override
    public void hideLoading() {
    }
    @Override
    public void vBookSuccess(BookBean bean) {
        mTv.setText("网络请求成功");
    }
    @Override
    public void vBookError(String reason) {
        mTv.setText(reason);
    }
}
```
#### 基础设置模板详解（Fragment类似）
##### globals.xml.ftl-声明全局变量
```xml
<globals>
	<#assign Collection=extractLetters(ActivityClass)>//从输入的title中获取输入字符
    <#assign collection_name=Collection?lower_case>//获取到的字符转成小写
	<!-- 这里声明全局变量-->
	<global id="activity_layout" value="${Collection?lower_case}" />//作为activity的layout的命名
    <global id="ActivityName" value="${Collection}Activity" />//作为activity类名
    <global id="PresenterName" value="P${Collection}Impl" />//作为presenter类名
    <global id="ModelName" value="M${Collection}Impl" />//作为model类名
    <global id="ContractName" value="C${Collection}" />//契约类-contract类名
	<global id="IViewName" value="IV${Collection}" />//契约类-view层接口名
	<global id="IPresenterName" value="IP${Collection}" />//契约类-presenter层接口名
	<global id="packageName" value="com.luliang.devmvp" />//项目包名(此处填写为自己的项目包名)
</globals>
```
##### recipe.xml.ftl-文件生成指定目录
> PS.这里需要注意一下，这里的根目录（root）是包名的那个目录，此项目例：com.luliang.devmvp
```xml
<recipe>
<!--merge 表示需要合并到指定文件的内容 （表示AndroidManifest声明新建的Activity） -->
   <merge from="root/AndroidManifest.xml.ftl"
             to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />

	<!-- instantiate 表示创建文件到指定文件夹	（把需要创建文件的模板放在根目录文件夹下对应文件夹,针对自己的项目修改路径）	  -->
    <instantiate from="root/res/layout/activity_main.xml.ftl" 
					to="${escapeXmlAttribute(resOut)}/layout/activity_${activity_layout}.xml" />
    <instantiate from="root/src/app_package/MvpActivity.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/mvp/view/activity/${ActivityName}.java" />
    <instantiate from="root/src/app_package/MvpPresenter.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/mvp/presenter/${PresenterName}.java" />
    <instantiate from="root/src/app_package/MvpContract.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/mvp/contract/${ContractName}.java" />
	<instantiate from="root/src/app_package/MvpModel.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/mvp/model/${ModelName}.java" />

</recipe>
```
##### template.xml-创建模板页面设置
> 这里主要设置模板名称、分类、生成模板需要填写的信息等等
```xml
<template
    format="5"
    revision="5"
    name="Mvp Activity"
    minApi="9"
    minBuildApi="14"
    description="Creates a new mvp activity">
    
    <category value="Activity" />
    <formfactor value="Mobile" />
    
	<!--parameter 标签表示创建时需要输入的属性 -->
	<parameter
        id="ActivityClass"
        name="Activity ClassName"
        type="string"
        constraints="nonempty"
        default="name" />
        
	<!--复选框属性-->
	<parameter
        id="isTitleBar"
        name="是否需要titleBar"
        type="boolean"
        default="false"
        help="选中即添加默认TitleBar" />
    <thumbs>
        <!-- 模板预览图片 -->
        <thumb>template_blank_activity.png</thumb>
    </thumbs>
    <globals file="globals.xml.ftl" />
    <execute file="recipe.xml.ftl" />
</template>
```
#### Mvp基础代码生成设置详解
> 文件的名称对应-文件生成指定目录文件（recipe.xml.ftl）里面的名称。
##### MvpActivity.java.ftl-类基础文件代码生成设置
> MvpContract.java.ftl、MvpModel.java.ftl、MvpPresenter.java.ftl几个文件类似，就不一一列出来
> ${ContractName} 等，对应全局变量文件（globals.xml.ftl）的变量。
```java
package ${packageName}.mvp.view.activity;

import ${packageName}.R;
import ${packageName}.mvp.base.BaseActivity;
import ${packageName}.mvp.contract.${ContractName};
import ${packageName}.mvp.presenter.${PresenterName};
import android.os.Bundle;
/**
 * Created by Liang_Lu on 2017/12/21.
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

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

```
##### activity_main.xml.ftl-布局基础文件代码生成设置
```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    >
<!-- 根据template文件的parameter属性判断是否需要添加 -->
	<#if isTitleBar>
	<include layout="@layout/toolbar_layout"/>
	</#if>
</LinearLayout>
```
##### AndroidManifest.xml.ftl-Activity声明文件设置
> PS.声明只针对于Activity，Fragment不需要此文件
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android" >
    <application>
        <activity android:name=".mvp.view.activity.${ActivityName}"/>
    </application>
</manifest>
```
#### 一键生成MVP基础代码
> - copy项目下的MvpActivity文件夹到Android Studio安装目录 例：C:\Android\Android Studio 3.0 release\plugins\android\lib\templates\activities文件夹下.
> - MvpFragment 的路径是 C:\Android\Android Studio 3.0 release\plugins\android\lib\templates\other
> - 重启Android Studio。


- 重启Android Studio之后，选中包名路径

![mvp-1.png](https://upload-images.jianshu.io/upload_images/2635045-a8a0cacc66a25934.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/800)
- 生成MvpActivity基础代码（MvpFragment可选中Fragment里面生成）

![mvp-2.png](https://upload-images.jianshu.io/upload_images/2635045-b91715bcaa298728.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/800)

#### 依赖库使用
> 将所有依赖的版本控制提取到根目录下的config.gradle做统一管理
> - supportVersion   : "26.1.0"
> - retrofit         : "2.2.0",
> - rxjava           : "2.0.1",
> - rxandroid        : "2.0.1",
> - okhttp3          : "3.4.1",
> - constraint_layout: "1.0.2",
> - rxjava2_adapter  : "1.0.0",
> - logging          : "3.4.0-RC1",
> - butterknife      : "8.8.1",
#### 意见反馈
如果遇到问题或者好的优化建议，请反馈到：Issues、927195249@qq.com 或者LiangLuDev@gmail.com

如果觉得还行的话，右上角的星星点一下吧! 谢谢啦！