<?xml version="1.0"?>
<recipe>

<!--merge 表示需要合并到指定文件的内容 （表示AndroidManifest声明新建的Activity） -->
   <merge from="root/AndroidManifest.xml.ftl"
             to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />

	<!-- instantiate 表示创建文件到指定文件夹	（把需要创建文件的模板放在root文件夹下对应文件夹,针对自己的项目修改路径）	  -->
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
