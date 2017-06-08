# SlideMenuLayout
## 简介
一个支持左右滑动并带有视差滑动效果的安卓滑动菜单控件。
### 功能介绍
1、支持滑动方向的配置
2、直接作为控件使用
3、处理各个场景下的滑动冲突

## 属性说明
属性|说明|值
---|---|---
|slideMode|滑动模式|left right both none|
|slidePadding|滑动菜单打开时候主视图预留边界|dimension|
|slideTime|滑动菜单单开的时间，默认800ms|integer|

## 方法说明
返回值|方法|说明
---|---|---
|void|setSlideMode(int slideMode)|设置滑动模式|
|void|setSlidePadding(int slidePadding)|设置滑动边界|
|void|setSlideTime(int slideTime)|设置滑动菜单打开的时间|
|View|getSlideLeftView()|返回左滑菜单|
|View|getSlideRightView()|返回右滑菜单|
|View|getSlideContentView()|返回主视图|
|void|toggleLeftSlide()|打开/关闭左滑菜单|
|void|openLeftSlide()|打开左滑菜单|
|void|closeLeftSlide()|关闭左滑此单|
|boolean|isLeftSlideOpen()|左滑菜单是否打开|
|void|toggleRightSlide()|打开/关闭右滑菜单|
|void|openRightSlide()|打开右滑菜单|
|void|closeRightSlide()|关闭右滑菜单|
|boolean|isRightSlideOpen()|右滑菜单是否打开|

## 发布历史
#### v1.0.0(2017/6/8)
1、发布SlideMenuLayout，处理各个场景下的滑动冲突。
2、封装演示demo。