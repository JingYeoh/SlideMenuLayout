package com.jkb.slidemenu;

import android.support.annotation.ColorRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntDef;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 侧滑菜单的动作
 * Created by JustKiddingBaby on 2017/6/3.
 */

public interface SlideMenuAction {

  /**
   * 只支持左侧滑
   */
  int SLIDE_MODE_LEFT = 1001;
  /**
   * 只支持右侧滑
   */
  int SLIDE_MODE_RIGHT = 1002;
  /**
   * 支持左侧滑和右侧滑
   */
  int SLIDE_MODE_LEFT_RIGHT = 1003;
  /**
   * 左滑右滑均不支持
   */
  int SLIDE_MODE_NONE = 1004;

  /**
   * set slide mode.(设置Slide模式)
   *
   * @param slideMode {@link #SLIDE_MODE_LEFT},{@link #SLIDE_MODE_LEFT_RIGHT},
   *                  {@link #SLIDE_MODE_RIGHT},{@link #SLIDE_MODE_NONE}
   */
  void setSlideMode(@SlideMode int slideMode);

  /**
   * 设置侧滑菜单打开时候距离主视图的padding
   *
   * @param slidePadding 单位px
   */
  void setSlidePadding(int slidePadding);

  /**
   * 设置滑动菜单打开的时间
   *
   * @param slideTime 单位ms
   */
  void setSlideTime(int slideTime);

  /**
   * 设置视差效果开关
   *
   * @param parallax 视差效果开关，Default:true
   */
  void setParallaxSwitch(boolean parallax);

  /**
   * 设置在侧滑菜单打开时候的ContentView的透明度，该值会在侧滑的时候不断变化，从1.0变化至设置的值.
   *
   * @param contentAlpha 0<contentAlpha<=1.0，值为1.0时表示侧滑时候ContentView无透明度变化.
   *                     Default:0.5
   */
  void setContentAlpha(@FloatRange(from = 0f, to = 1.0f) float contentAlpha);

  /**
   * 设置ContentView在滑动过程中的阴影颜色
   *
   * @param color 颜色，默认色值：#000000
   */
  void setContentShadowColor(@ColorRes int color);

  /**
   * 设置ContentView是否在侧滑菜单打开时候点击关闭侧滑菜单.
   *
   * @param contentToggle Default:false
   */
  void setContentToggle(boolean contentToggle);

  /**
   * 设置是否运行拖动侧滑菜单
   *
   * @param allowTogging Default:true
   */
  void setAllowTogging(boolean allowTogging);

  /**
   * 返回左侧滑视图
   *
   * @return {@link View}
   */
  View getSlideLeftView();

  /**
   * 返回右侧滑视图
   *
   * @return {@link View}
   */
  View getSlideRightView();

  /**
   * 返回侧滑主体视图
   *
   * @return {@link View}
   */
  View getSlideContentView();

  /**
   * 打开/关闭左侧滑菜单
   */
  void toggleLeftSlide();

  /**
   * 打开左侧滑菜单
   */
  void openLeftSlide();

  /**
   * 关闭左侧滑菜单
   */
  void closeLeftSlide();

  /**
   * 左侧滑菜单是否打开
   */
  boolean isLeftSlideOpen();

  /**
   * 打开/关闭右侧滑菜单
   */
  void toggleRightSlide();

  /**
   * 打开右滑菜单
   */
  void openRightSlide();

  /**
   * 关闭右侧滑菜单
   */
  void closeRightSlide();

  /**
   * 右侧滑菜单是否打开
   */
  boolean isRightSlideOpen();

  /**
   * 设置侧滑菜单变化的监听器
   *
   * @param listener {@link OnSlideChangedListener}
   */
  void addOnSlideChangedListener(OnSlideChangedListener listener);

  /**
   * Slide Mode.（滑动模式）
   *
   * @hide
   */
  @IntDef({SLIDE_MODE_LEFT, SLIDE_MODE_RIGHT, SLIDE_MODE_LEFT_RIGHT, SLIDE_MODE_NONE})
  @Retention(RetentionPolicy.SOURCE)
  public @interface SlideMode {

  }
}
