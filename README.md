# SlideMenuLayout
## Introduction
An android slide menu that supports left and right swipes and slides with parallax.   
一个支持左右滑动并带有视差滑动效果的安卓滑动菜单控件。([中文版入口](README-CN.md))  
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
<img src="https://img.shields.io/badge/license-Apache 2.0-green.svg?style=flat">
[![SDK](https://img.shields.io/badge/API-12%2B-green.svg?style=flat)](https://android-arsenal.com/api?level=11)

## Demo
Encapsulate the sliding nesting of different scenarios.  
<img src="/gif/demo.gif" width="280px"/>

## Features
- [x] **Support for sliding direction configuration**
- [x] **Can be used as ``ViewGroup``**
- [x] **Handle the sliding conflicts in each scenarios**
- [x] **Option to click on content view to close menu when it's opening**
- [x] **Option to dim the content view automatically**
- [x] **Option to toggle the parallax effect**

## Version
|name|SlideMenuLayout|
|---|---|
|latest|![Download](https://api.bintray.com/packages/jkb/maven/slidemenu/images/download.svg)|

## Configure
#### Maven
```xml
<dependency>
  <groupId>com.justkiddingbaby</groupId>
  <artifactId>slidemenu</artifactId>
  <version>the latest version</version>
  <type>pom</type>
</dependency>
```
#### JCenter
First. add to project build.gradle
```gradle
repositories {
    jcenter()
}
```
Second. add to module build.gradle
```gradle
compile 'com.justkiddingbaby:slidemenu:the latest version'
```

## Attributes instruction
|attribute|instruction|value|
|---|---|---|
|[slideMode](/slidemenu/src/main/res/values/attrs.xml)|sliding mode|left right both none|
|[slidePadding](/slidemenu/src/main/res/values/attrs.xml)|the content view padding when slide menu is opened|dimension|
|[slideTime](/slidemenu/src/main/res/values/attrs.xml)|the time of slide menu open,the default value is 800ms|integer|
|[parallax](/slidemenu/src/main/res/values/attrs.xml)|option to toggle the parallax effect,default is true|boolean|
|[contentAlpha](/slidemenu/src/main/res/values/attrs.xml)|the alpha of shadow for `ContentView` when menu is opened.（0<alpha<=1.0），default is 0.5f|float|
|[contentShadowColor](/slidemenu/src/main/res/values/attrs.xml)|the color of shadow for `ContentView` when menu is sliding，default is `#000000`|color|
|[contentToggle](/slidemenu/src/main/res/values/attrs.xml)|option to click on content view to close menu when it's opening，default is false|boolean|

## Function instruction
|return|function name|instruction|
|---|---|---|
|void|[setSlideMode(int slideMode)](/slidemenu/src/main/java/com/jkb/slidemenu/SlideMenuAction.java)|set slide mode|
|void|[setSlidePadding(int slidePadding)](/slidemenu/src/main/java/com/jkb/slidemenu/SlideMenuAction.java)|set slide content padding when slide menu is open|
|void|[setSlideTime(int slideTime)](/slidemenu/src/main/java/com/jkb/slidemenu/SlideMenuAction.java)|set the time of opening slide menu|
|View|[getSlideLeftView()](/slidemenu/src/main/java/com/jkb/slidemenu/SlideMenuAction.java)|return left slide menu view|
|View|[getSlideRightView()](/slidemenu/src/main/java/com/jkb/slidemenu/SlideMenuAction.java)|return right slide menu view|
|View|[getSlideContentView()](/slidemenu/src/main/java/com/jkb/slidemenu/SlideMenuAction.java)|return content view|
|void|[toggleLeftSlide()](/slidemenu/src/main/java/com/jkb/slidemenu/SlideMenuAction.java)|open or close left slide menu|
|void|[openLeftSlide()](/slidemenu/src/main/java/com/jkb/slidemenu/SlideMenuAction.java)|open left slide menu|
|void|[closeLeftSlide()](/slidemenu/src/main/java/com/jkb/slidemenu/SlideMenuAction.java)|close left slide menu|
|boolean|[isLeftSlideOpen()](/slidemenu/src/main/java/com/jkb/slidemenu/SlideMenuAction.java)|return the result of left slide menu is open|
|void|[toggleRightSlide()](/slidemenu/src/main/java/com/jkb/slidemenu/SlideMenuAction.java)|open or close right slide menu|
|void|[openRightSlide()](/slidemenu/src/main/java/com/jkb/slidemenu/SlideMenuAction.java)|open right slide menu|
|void|[closeRightSlide()](/slidemenu/src/main/java/com/jkb/slidemenu/SlideMenuAction.java)|close right slide menu|
|boolean|[isRightSlideOpen()](/slidemenu/src/main/java/com/jkb/slidemenu/SlideMenuAction.java)|return the result of right slide menu is open|
|void|[setParallaxSwitch(boolean parallax)](/slidemenu/src/main/java/com/jkb/slidemenu/SlideMenuAction.java)|set is able to toggle the parallax effect|
|void|[setContentAlpha(float contentAlpha)](/slidemenu/src/main/java/com/jkb/slidemenu/SlideMenuAction.java)|set the alpha of shadow for `ContentView` when menu is opened.，`1.0f means the effect is not abled`|
|void|[setContentShadowColor(int color)](/slidemenu/src/main/java/com/jkb/slidemenu/SlideMenuAction.java)|set the color of shadow for `ContentView` when menu is sliding,default is `#000000`|
|void|[setContentToggle(boolean contentToggle)](/slidemenu/src/main/java/com/jkb/slidemenu/SlideMenuAction.java)|option to click on content view to close menu when it's opening.default is false|

## Usage
#### use in the layout
```xml
 <com.jkb.slidemenu.SlideMenuLayout
        android:id="@+id/mainSlideMenu"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@color/white"
        app:slideMode="both">
        <include layout="@layout/content_menu_left" />
        <include layout="@layout/content_menu_right" />
        <include layout="@layout/content_menu_content" />
 </com.jkb.slidemenu.SlideMenuLayout>
 ```
 #### Mind
 **when you use [SlideMenuLayout](/slidemenu/src/main/java/com/jkb/slidemenu/SlideMenuLayout.java) in layout,it can only host three child view,and the left slide menu view and the right slide menu view must add before the content view (for prevent the right slide menu from overlapping).**   
 if slideMode is both then the SlideViewLayout must host three child views.
 
## Release history
#### v1.2.1(2017/9/18)
1、intercept the touch action of `content view` when `contentToggle` attribute is true.
#### v1.2.0(2017/8/20)
1、add feature:Option to click on content view to close menu when it's opening.
2、add feature:Option to dim the content view automatically.
3、add feature:Option to toggle the parallax effect.
#### v1.0.1(2017/6/29)
1、alter minSdkVersion to SDK 12.
#### v1.0.0(2017/6/8)
1、release SlideMenuLayout，Handle the sliding conflicts in each scenarios.  
2、Encapsulation demo.

## License
![](https://upload.wikimedia.org/wikipedia/commons/thumb/f/f8/License_icon-mit-88x31-2.svg/128px-License_icon-mit-88x31-2.svg.png)

RollingLayout is available under the MIT license. See the [LICENSE](https://opensource.org/licenses/MIT) file for more info.