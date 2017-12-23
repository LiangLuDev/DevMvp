<?xml version="1.0"?>
<globals>
	<#assign Collection=extractLetters(ActivityClass)>//从输入的title中获取输入字符
    <#assign collection_name=Collection?lower_case>//获取到的字符转成小写

	<!-- 这里声明全局变量-->
	<global id="activity_layout" value="${Collection?lower_case}" />//作为activity的layout的命名
    <global id="ActivityName" value="${Collection}Activity" />//作为activity类名
    <global id="PresenterName" value="P${Collection}Impl" />//作为presenter类名
    <global id="ModelName" value="M${Collection}Impl" />//作为model类名
    <global id="ContractName" value="C${Collection}" />//作为contract类名
	<global id="IViewName" value="IV${Collection}" />//作为view层接口名
	<global id="IPresenterName" value="IP${Collection}" />//作为presenter层接口名
	
	
	<global id="packageName" value="com.luliang.devmvp" />//项目包名
</globals>
