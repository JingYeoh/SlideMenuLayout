package com.jkb.slidemenu;

/**
 * 侧滑菜单变化时候的监听器.
 *
 * @author JingYeoh
 *         <a href="mailto:yangjing9611@foxmail.com">Email me</a>
 *         <a href="https://github.com/justkiddingbaby">Github</a>
 *         <a href="http://blog.justkiddingbaby.com">Blog</a>
 * @since Dec 25,2017
 */

public interface OnSlideChangedListener {

  /**
   * 侧滑菜单状态变化
   *
   * @param slideMenu        侧滑菜单{@link SlideMenuLayout}
   * @param isLeftSlideOpen  左滑菜单是否打开
   * @param isRightSlideOpen 右滑菜单是否打开
   */
  void onSlideChanged(SlideMenuLayout slideMenu, boolean isLeftSlideOpen, boolean isRightSlideOpen);
}
