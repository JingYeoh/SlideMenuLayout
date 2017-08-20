package com.jkb.slidemenu;

import android.view.View;

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
     * 设置Slide模式
     *
     * @param slideMode {@link #SLIDE_MODE_LEFT},{@link #SLIDE_MODE_LEFT_RIGHT},
     *                  {@link #SLIDE_MODE_RIGHT},{@link #SLIDE_MODE_NONE}
     */
    void setSlideMode(int slideMode);

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
}
