package com.jkb.slidemenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorRes;
import android.support.annotation.FloatRange;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * 侧滑菜单布局
 * Created by JustKiddingBaby on 2017/6/3.
 */

public class SlideMenuLayout extends ViewGroup implements SlideMenuAction {

    private View mLeftView, mRightView, mContentView;
    //attrs
    private int mSlideMode;
    private int mSlidePadding;
    private int mSlideTime;
    private boolean mParallax;
    private float mContentAlpha;
    private int mContentShadowColor;
    private boolean mContentToggle;
    //data
    private int screenWidth;
    private int screenHeight;
    private int mSlideWidth;
    private int mContentWidth;
    private int mContentHeight;
    //slide
    private Scroller mScroller;
    private int mLastX;
    private int mLastXIntercept;//为了处理滑动冲突
    private int mLastYIntercept;//为了处理滑动冲突
    private int mDx;//滑动的距离，在手指抬起时清空
    private boolean mTriggerSlideLeft;
    private boolean mTriggerSlideRight;
    //ui
    private Paint mContentShadowPaint;

    public SlideMenuLayout(Context context) {
        this(context, null);
    }

    public SlideMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public SlideMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        screenWidth = getScreenWidth(context);
        screenHeight = getScreenHeight(context);
        initAttrs(attrs);//初始化属性
        initContentShadowPaint();
        mScroller = new Scroller(context);

    }

    /**
     * 获取手机屏幕的高度
     */
    static int getScreenHeight(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * 获取手机屏幕的宽度
     */
    static int getScreenWidth(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 初始化ContentView的阴影画笔
     */
    private void initContentShadowPaint() {
        mContentShadowPaint = new Paint();
        mContentShadowPaint.setColor(mContentShadowColor);//给画笔设置透明度变化的颜色
        mContentShadowPaint.setStyle(Paint.Style.FILL);//设置画笔类型填充
    }

    /**
     * 初始化属性
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.SlideMenuLayout);
        mSlideMode = ta.getInteger(R.styleable.SlideMenuLayout_slideMode, SLIDE_MODE_NONE);
        mSlidePadding = (int) ta.getDimension(R.styleable.SlideMenuLayout_slidePadding, screenWidth / 4);
        mSlideTime = ta.getInteger(R.styleable.SlideMenuLayout_slideTime, 700);
        mParallax = ta.getBoolean(R.styleable.SlideMenuLayout_parallax, true);
        mContentAlpha = ta.getFloat(R.styleable.SlideMenuLayout_contentAlpha, 0.5f);
        mContentShadowColor = ta.getColor(R.styleable.SlideMenuLayout_contentShadowColor,
                Color.parseColor("#000000"));
        mContentToggle = ta.getBoolean(R.styleable.SlideMenuLayout_contentToggle, false);
        ta.recycle();
    }

    @Override
    public void addView(View child) {
        if (getChildCount() > 3) {
            throw new IllegalStateException("SlideMenuLayout can host only three direct child");
        }
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        if (getChildCount() > 3) {
            throw new IllegalStateException("SlideMenuLayout can host only three direct child");
        }
        super.addView(child, index);
    }

    @Override
    public void addView(View child, LayoutParams params) {
        if (getChildCount() > 3) {
            throw new IllegalStateException("SlideMenuLayout can host only three direct child");
        }
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int index, LayoutParams params) {
        if (getChildCount() > 3) {
            throw new IllegalStateException("SlideMenuLayout can host only three direct child");
        }
        super.addView(child, index, params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //默认全屏
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthResult = 0;
        if (widthMode == MeasureSpec.EXACTLY) {
            widthResult = widthSize;
        } else {
            widthResult = screenWidth;
        }
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightResult = 0;
        if (heightMode == MeasureSpec.EXACTLY) {
            heightResult = heightSize;
        } else {
            heightResult = screenHeight;
        }
        //初始化侧滑菜单
        initSlideView(widthResult, heightResult);

        measureSlideChild(mContentView, widthMeasureSpec, heightMeasureSpec);
        measureSlideChild(mLeftView, widthMeasureSpec, heightMeasureSpec);
        measureSlideChild(mRightView, widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(mContentWidth, mContentHeight);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean result = super.drawChild(canvas, child, drawingTime);
        if (child == mContentView) {
            int rX = Math.abs(getScrollX());
            int alpha = 0;
            if (rX == 0) {
                alpha = 0;
            } else if (rX >= mSlideWidth) {
                alpha = (int) ((1.0f - mContentAlpha) * 255);
            } else {
                alpha = (int) (Math.abs((1.0f - mContentAlpha) / mSlideWidth) * rX * 255);
            }
            alpha = alpha < 0 ? 255 : alpha;
            alpha = alpha > 255 ? 255 : alpha;

            mContentShadowPaint.setAlpha(alpha);
            canvas.drawRect(mContentView.getLeft(), mContentView.getTop(), mContentView.getRight(),
                    mContentView.getBottom(), mContentShadowPaint);//画出阴影
        }
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mLeftView != null) {
            mLeftView.layout(-mSlideWidth, 0, 0, mContentHeight);
        }
        if (mRightView != null) {
            mRightView.layout(mContentWidth, 0, mContentWidth + mSlideWidth, mContentHeight);
        }
        if (mContentView != null) {
            mContentView.layout(0, 0, mContentWidth, mContentHeight);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            if (mLeftView != null) {
                leftMenuParallax();
            }
            if (mRightView != null) {
                rightMenuParallax();
            }
            postInvalidate();
            changeContentViewAlpha();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) ev.getX() - mLastXIntercept;
                int deltaY = (int) ev.getY() - mLastYIntercept;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {//横向滑动
                    if (mTriggerSlideLeft) {
                        intercept = x > mSlideWidth;
                    } else if (mTriggerSlideRight) {
                        intercept = x < mSlidePadding;
                    } else {
                        //当手指在边缘地区的时候拦截事件
                        //以下判断条件主要为了区分边界滑动
                        if (mSlideMode == SLIDE_MODE_LEFT && deltaX > 0) {//左滑
                            intercept = x <= mSlidePadding / 2;
                        } else if (mSlideMode == SLIDE_MODE_RIGHT && deltaX < 0) {//右滑
                            intercept = x >= mSlideWidth - mSlidePadding / 2;
                        } else if (mSlideMode == SLIDE_MODE_LEFT_RIGHT) {//双向滑动
                            if (deltaX > 0) intercept = x <= mSlidePadding / 2;
                            if (deltaX < 0) intercept = x >= mSlideWidth - mSlidePadding / 2;
                        } else {
                            intercept = false;
                        }
                    }
                } else {//纵向滑动
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
//                intercept = false;
                //点击ContentView后关闭侧滑菜单
                intercept = touchContentViewToCloseSlide();
                break;
        }
        mLastX = x;
        mLastXIntercept = x;
        mLastYIntercept = y;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int currentX = (int) event.getX();
                //拿到x方向的偏移量
                int dx = currentX - mLastX;
                if (dx < 0) {//向左滑动
                    scrollLeft(dx);
                } else {//向右滑动
                    scrollRight(dx);
                }
                mLastX = currentX;
                mDx = dx;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mDx > 0) {//右滑
                    inertiaScrollRight();
                } else {
                    inertiaScrollLeft();
                }
                mDx = 0;
                break;
        }
        return true;
    }

    /**
     * 点击ContentView后关闭侧滑菜单
     */
    private boolean touchContentViewToCloseSlide() {
        if (!mContentToggle) return false;
        if (Math.abs(getScrollX()) < mSlideWidth) return false;
        int dX = getScrollX();
        if (dX < 0) {//左滑菜单打开
            if (mLastX > mSlideWidth) {
                closeLeftSlide();
            } else {
                return false;
            }
        } else if (dX > 0) {
            if (mLastX < (mContentWidth - mSlideWidth)) {
                closeRightSlide();
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 惯性左滑
     */
    private void inertiaScrollLeft() {
        if (mSlideMode == SLIDE_MODE_RIGHT) {
            if (getScrollX() >= mSlideWidth / 2) {
                openRightSlide();
            } else {
                if (!mTriggerSlideRight) {
                    closeRightSlide();
                }
            }
        } else if (mSlideMode == SLIDE_MODE_LEFT) {
            if (-getScrollX() <= mSlideWidth / 2) {
                closeLeftSlide();
            } else {
                if (mTriggerSlideLeft) {
                    openLeftSlide();
                }
            }
        } else if (mSlideMode == SLIDE_MODE_LEFT_RIGHT) {
            if (!mTriggerSlideLeft && !mTriggerSlideRight) {//左右滑动菜单都关闭状态
                if (getScrollX() >= mSlideWidth / 2) {
                    openRightSlide();
                } else {
                    closeRightSlide();
                }
            } else if (mTriggerSlideLeft || getScrollX() < 0) {
                if (-getScrollX() <= mSlideWidth / 2) {
                    closeLeftSlide();
                } else {
                    openLeftSlide();
                }
            }
        }
    }

    /**
     * 惯性右滑
     */
    private void inertiaScrollRight() {
        if (mSlideMode == SLIDE_MODE_LEFT) {
            if (-getScrollX() >= mSlideWidth / 2) {
                openLeftSlide();
            } else {
                if (!mTriggerSlideLeft) {
                    closeLeftSlide();
                }
            }
        } else if (mSlideMode == SLIDE_MODE_RIGHT) {
            if (getScrollX() <= mSlideWidth / 2) {
                closeRightSlide();
            } else {
                if (mTriggerSlideRight) {
                    openRightSlide();
                }
            }
        } else if (mSlideMode == SLIDE_MODE_LEFT_RIGHT) {
            if (!mTriggerSlideLeft && !mTriggerSlideRight) {//左右滑动菜单都关闭状态
                if (-getScrollX() >= mSlideWidth / 2) {
                    openLeftSlide();
                } else {
                    closeLeftSlide();
                }
            } else if (mTriggerSlideRight || getScrollX() > 0) {
                if (getScrollX() <= mSlideWidth / 2) {
                    closeRightSlide();
                } else {
                    openRightSlide();
                }
            }
        }
    }

    /**
     * 向左滑动
     */
    private void scrollLeft(int dx) {
        if (mSlideMode == SLIDE_MODE_RIGHT) {
            //右滑菜单已经打开，不做操作
            if (mTriggerSlideRight || getScrollX() - dx >= mSlideWidth) {
                openRightSlide();
                return;
            }
            rightMenuParallax();
        } else if (mSlideMode == SLIDE_MODE_LEFT) {
            //左滑菜单未打开，不做操作
            if (!mTriggerSlideLeft || getScrollX() - dx >= 0) {
                closeLeftSlide();
                return;
            }
            leftMenuParallax();
        } else if (mSlideMode == SLIDE_MODE_LEFT_RIGHT) {
            //右滑菜单已经打开，不做操作
            if (mTriggerSlideRight || getScrollX() - dx >= mSlideWidth) {
                openRightSlide();
                return;
            }
            leftMenuParallax();
            rightMenuParallax();
        }
        scrollBy(-dx, 0);
        changeContentViewAlpha();
    }

    /**
     * 向右滑动
     */
    private void scrollRight(int dx) {
        if (mSlideMode == SLIDE_MODE_LEFT) {
            //左滑菜单已经打开，不做操作
            if (mTriggerSlideLeft || getScrollX() - dx <= -mSlideWidth) {
                openLeftSlide();
                return;
            }
            leftMenuParallax();
        } else if (mSlideMode == SLIDE_MODE_RIGHT) {
            //右滑菜单未打开，不做操作
            if (!mTriggerSlideRight || getScrollX() - dx <= 0) {
                closeRightSlide();
                return;
            }
            rightMenuParallax();
        } else if (mSlideMode == SLIDE_MODE_LEFT_RIGHT) {
            //左滑菜单已经打开，不做操作
            if (mTriggerSlideLeft || getScrollX() - dx <= -mSlideWidth) {
                openLeftSlide();
                return;
            }
            leftMenuParallax();
            rightMenuParallax();
        }
        scrollBy(-dx, 0);
        changeContentViewAlpha();
    }

    /**
     * 改变ContentView的透明度
     */
    private void changeContentViewAlpha() {
        /*int rX = Math.abs(getScrollX());
        float alpha = 1.0f;
        if (rX == 0) {
            alpha = 1.0f;
        } else if (rX == mSlideWidth) {
            alpha = mContentAlpha;
        } else {
            alpha = Math.abs(1.0f - ((1.0f - mContentAlpha) / mSlideWidth) * rX);
        }
        alpha = alpha < 0.1f ? 0.1f : alpha;
        alpha = alpha > 1.0f ? 1.0f : alpha;
        mContentView.setAlpha(alpha);*/
        postInvalidate();
    }

    /**
     * 右滑菜单的视差效果
     */
    private void rightMenuParallax() {
        if (!mParallax) return;
        int rightTranslationX = 2 * (-mSlideWidth + getScrollX()) / 3;
        //有菜单打开或者关闭的时候回恢复原位X
        if (getScrollX() == 0 || getScrollX() == mSlideWidth || getScrollX() == -mSlideWidth) {
            rightTranslationX = 0;
        }
        mRightView.setTranslationX(rightTranslationX);
    }

    /**
     * 左滑菜单的视差效果
     */
    private void leftMenuParallax() {
        if (!mParallax) return;
        int leftTranslationX = 2 * (mSlideWidth + getScrollX()) / 3;
        //有菜单打开或者关闭的时候回恢复原位
        if (getScrollX() == 0 || getScrollX() >= mSlideWidth || getScrollX() <= -mSlideWidth) {
            leftTranslationX = 0;
        }
        mLeftView.setTranslationX(leftTranslationX);
    }

    /**
     * 缓慢滑动
     */
    private void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int deltaX = destX - scrollX;
        float time = deltaX * 1.0f / (mSlideWidth * 1.0f / mSlideTime);
        time = Math.abs(time);
        mScroller.startScroll(scrollX, 0, deltaX, destY, (int) time);
        invalidate();
    }

    /**
     * 测量子视图
     */
    private void measureSlideChild(View childView, int widthMeasureSpec, int heightMeasureSpec) {
        if (childView == null) return;
        LayoutParams lp = childView.getLayoutParams();
        int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
                getPaddingLeft() + getPaddingRight(), lp.width);
        int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
                getPaddingTop() + getPaddingBottom(), lp.height);
        childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    /**
     * 初始化SlideMenu的视图
     */
    private void initSlideView(int widthResult, int heightResult) {
        if (getChildCount() == 0) {
            throw new IllegalStateException("SlideMenuLayout must host one direct child");
        }
        mSlideWidth = widthResult - mSlidePadding;
        mContentWidth = widthResult;
        mContentHeight = heightResult;
        switch (getChildCount()) {
            case 1:
                mSlideMode = SLIDE_MODE_NONE;
                mContentView = getChildAt(0);
                break;
            case 2:
                if (mSlideMode == SLIDE_MODE_LEFT) {
                    mLeftView = getChildAt(0);
                    mContentView = getChildAt(1);
                } else if (mSlideMode == SLIDE_MODE_RIGHT) {
                    mRightView = getChildAt(0);
                    mContentView = getChildAt(1);
                } else {
                    throw new IllegalStateException("SlideMenuLayout must host only three direct child when slideMode" +
                            " " +
                            "is both");
                }
                break;
            case 3:
                mLeftView = getChildAt(0);
                mRightView = getChildAt(1);
                mContentView = getChildAt(2);
                break;
        }
        if (mLeftView != null) {
            mLeftView.getLayoutParams().width = mSlideWidth;
        }
        if (mRightView != null) {
            mRightView.getLayoutParams().width = mSlideWidth;
        }

        LayoutParams contentParams = mContentView.getLayoutParams();
        contentParams.width = widthResult;
        contentParams.height = heightResult;
    }

    @Override
    public void setSlideMode(@SlideMode int slideMode) {
        mSlideMode = slideMode;
    }

    @Override
    public void setSlidePadding(int slidePadding) {
        mSlidePadding = slidePadding;
    }

    @Override
    public void setSlideTime(int slideTime) {
        mSlideTime = slideTime;
    }

    @Override
    public void setParallaxSwitch(boolean parallax) {
        mParallax = parallax;
    }

    @Override
    public void setContentAlpha(@FloatRange(from = 0f, to = 1.0f) float contentAlpha) {
        mContentAlpha = contentAlpha;
    }

    @Override
    public void setContentShadowColor(@ColorRes int color) {
        mContentShadowColor = color;
        if (mContentShadowPaint == null) initContentShadowPaint();
        mContentShadowPaint.setColor(ContextCompat.getColor(getContext(), mContentShadowColor));
        postInvalidate();
    }

    @Override
    public void setContentToggle(boolean contentToggle) {
        mContentToggle = contentToggle;
    }

    @Override
    public View getSlideLeftView() {
        return mLeftView;
    }

    @Override
    public View getSlideRightView() {
        return mRightView;
    }

    @Override
    public View getSlideContentView() {
        return mContentView;
    }

    @Override
    public void toggleLeftSlide() {
        if (mTriggerSlideLeft) {
            closeLeftSlide();
        } else {
            openLeftSlide();
        }
    }

    @Override
    public void openLeftSlide() {
        if (mSlideMode == SLIDE_MODE_RIGHT) return;
        mTriggerSlideLeft = true;
        smoothScrollTo(-mSlideWidth, 0);
    }

    @Override
    public void closeLeftSlide() {
        if (mSlideMode == SLIDE_MODE_RIGHT) return;
        mTriggerSlideLeft = false;
        smoothScrollTo(0, 0);
    }

    @Override
    public boolean isLeftSlideOpen() {
        return mTriggerSlideLeft;
    }

    @Override
    public void toggleRightSlide() {
        if (mTriggerSlideRight) {
            closeRightSlide();
        } else {
            openRightSlide();
        }
    }

    @Override
    public void openRightSlide() {
        if (mSlideMode == SLIDE_MODE_LEFT) return;
        mTriggerSlideRight = true;
        smoothScrollTo(mSlideWidth, 0);
    }

    @Override
    public void closeRightSlide() {
        if (mSlideMode == SLIDE_MODE_LEFT) return;
        mTriggerSlideRight = false;
        smoothScrollTo(0, 0);
    }

    @Override
    public boolean isRightSlideOpen() {
        return mTriggerSlideRight;
    }
}
