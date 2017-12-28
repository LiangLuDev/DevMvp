<?xml version="1.0" encoding="utf-8"?>
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