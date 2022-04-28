/*
 * 文件名称:          AEventManage.java
 *
 * 编译器:            android2.2
 * 时间:              下午8:02:22
 */
package com.wxiwei.office.system.beans;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.widget.Scroller;
import android.widget.Toast;

import com.wxiwei.office.constant.EventConstant;
import com.wxiwei.office.constant.MainConstant;
import com.wxiwei.office.system.IControl;
import com.wxiwei.office.system.IMainFrame;

/**
 * 事件管理器
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2011-12-12
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:
 * <p>
 * <p>
 */
public abstract class AEventManage implements OnTouchListener,
        OnGestureListener, OnDoubleTapListener, OnClickListener {
    /**
     * @param spreadsheet
     */
    public AEventManage(Context context, IControl control) {
        this.control = control;
        gesture = new GestureDetector(context, this, null, true);
        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    /**
     * 触摸事件
     */
    private boolean isZoom = false;

    public boolean onTouch(View v, MotionEvent event) {
        boolean ret = false;
        try {
            if (gesture == null) {
                return false;
            }
            control.getMainFrame().onEventMethod(v, event, null, -1.0f, -1.0f, IMainFrame.ON_TOUCH);
            if (event.getPointerCount() == 2) {
                //view zoom
                isZoom = true;
                return zoom(event);
            } else {
                if (isZoom) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        isZoom = false;
                    }
                    return true;
                }
            }

            // 交给手型处理
            gesture.onTouchEvent(event);
            if (mVelocityTracker == null) {
                mVelocityTracker = VelocityTracker.obtain();
            }
            mVelocityTracker.addMovement(event);
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    stopFling();
                    mActivePointerId = event.getPointerId(0);
                    break;

                case MotionEvent.ACTION_MOVE:
                    break;

                case MotionEvent.ACTION_UP:
                    if (!singleTabup) {
                        final VelocityTracker velocityTracker = mVelocityTracker;
                        velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                        int initialYVelocity = (int) velocityTracker.getYVelocity(mActivePointerId);
                        int initialXVelocity = (int) velocityTracker.getXVelocity(mActivePointerId);
                        if (Math.abs(initialYVelocity) > mMinimumVelocity
                                || Math.abs(initialXVelocity) > mMinimumVelocity) {
                            if (!isScroll) {
                                isScroll = control.getApplicationType() == MainConstant.APPLICATION_TYPE_PPT;
                            }

                            if (!zoomChange) {
                                fling(-initialXVelocity, -initialYVelocity);
                            }
                            ret = true;
                        }
                        midXDoublePoint = -1;
                        midYDoublePoint = -1;
                        mActivePointerId = -1;
                        if (mVelocityTracker != null) {
                            mVelocityTracker.recycle();
                            mVelocityTracker = null;
                        }
                        toast.cancel();

                        if (isScroll) {
                            isScroll = false;
                            if (control.getApplicationType() == MainConstant.APPLICATION_TYPE_WP && zoomChange) {
                                if (!control.getMainFrame().isZoomAfterLayoutForWord()) {
                                    control.actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, null);
                                }
                            }
                            if (control.getApplicationType() == MainConstant.APPLICATION_TYPE_PPT) {
                                if (!control.isSlideShow()) {
                                    control.actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, null);
                                }
                            } else {
                                control.actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, null);
                            }

                            control.actionEvent(EventConstant.SYS_UPDATE_TOOLSBAR_BUTTON_STATUS, null);
                        }
                        if (control.getApplicationType() != MainConstant.APPLICATION_TYPE_WP) {
                            zoomChange = false;
                        }
                    }
                    singleTabup = false;

                    break;
                case MotionEvent.ACTION_CANCEL:
                    mActivePointerId = -1;
                    if (mVelocityTracker != null) {
                        mVelocityTracker.recycle();
                        mVelocityTracker = null;
                    }
                default:
                    break;
            }
        } catch (Exception e) {
            control.getSysKit().getErrorKit().writerLog(e);
        }
        return ret;
    }

    /**
     * action when zooming
     *
     * @param event
     * @return
     */
    protected boolean zoom(MotionEvent event) {
        if (!control.getMainFrame().isTouchZoom()) {
            return true;
        }
        float zoom = (Float) control.getActionValue(EventConstant.APP_ZOOM_ID, null);
        float fitZoom = (Float) control.getActionValue(EventConstant.APP_FIT_ZOOM_ID, null);

        Float maxZoom = (Float) control.getActionValue(EventConstant.APP_ZOOM_MAX_ID, null);
        if (maxZoom == null) maxZoom = 6f;
        boolean isMinZoom = (int) (zoom * MainConstant.STANDARD_RATE) == (int) (fitZoom * MainConstant.STANDARD_RATE);
        boolean zoomRateChanged = false;
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_POINTER_1_DOWN:
                float x1 = event.getX(0);
                float y1 = event.getY(0);
                float x2 = event.getX(1);
                float y2 = event.getY(1);
                float min = Math.min(x1, x2);
                midXDoublePoint = (int) (min + Math.abs(x1 - x2) / 2);
                min = Math.min(y1, y2);
                midYDoublePoint = (int) (min + Math.abs(y1 - y2) / 2);
                downDistance = calculateDistance(event);
                downZoom = zoom;
                control.actionEvent(EventConstant.APP_ZOOM_DOWN_ID, new int[]{(int) (zoom * MainConstant.STANDARD_RATE), midXDoublePoint, midYDoublePoint});
                control.getMainFrame().onEventMethod(null, event, null, -1.0f, -1.0f, IMainFrame.ON_ZOOM_START);

                break;
            case MotionEvent.ACTION_POINTER_1_UP:
                //zoomRateChanged = true;
//                if (event.getPointerCount()<2) {
                    control.actionEvent(EventConstant.APP_ZOOM_UP_ID, null);
                    control.getMainFrame().onEventMethod(null, event, null, -1.0f, -1.0f, IMainFrame.ON_ZOOM_END);
//                }
                break;

            case MotionEvent.ACTION_MOVE:
                float newDistance = calculateDistance(event);
                zoom = downZoom * newDistance / downDistance;
                if (zoom > maxZoom) {
                    zoom = maxZoom;
                } else if (zoom < fitZoom)//MainConstant.ZOOM_MIN_RATE)
                {
                    zoom = fitZoom;//MainConstant.ZOOM_MIN_RATE;
                }
                zoomRateChanged = true;
                break;
            default:
                break;
        }

        //zoom rate changed, update view
        if (zoomRateChanged) {
            isScroll = true;
            zoomChange = true;
            control.actionEvent(EventConstant.APP_ZOOM_ID, new int[]{(int) (zoom * MainConstant.STANDARD_RATE), midXDoublePoint, midYDoublePoint});
            control.getView().postInvalidate();
            control.getMainFrame().onEventMethod(null, event, null, zoom, fitZoom, IMainFrame.ON_ZOOM_CHANGE);

        }
        return true;
    }

    /**
     *
     */
    private float calculateDistance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    public boolean onDown(MotionEvent e) {
        return control.getMainFrame().onEventMethod(control.getView(), e, null, -1.0f, -1.0f, IMainFrame.ON_DOWN);
    }

    /**
     *
     */
    public void onShowPress(MotionEvent e) {
        control.getMainFrame().onEventMethod(control.getView(), e, null, -1.0f, -1.0f, IMainFrame.ON_SHOW_PRESS);
    }

    /**
     *
     */
    public boolean onSingleTapUp(MotionEvent e) {
        if (!isScroll) {
            singleTabup = true;
        }
        return control.getMainFrame().onEventMethod(control.getView(), e, null, -1.0f, -1.0f, IMainFrame.ON_SINGLE_TAP_UP);
    }

    /**
     *
     */
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        isScroll = true;
        return control.getMainFrame().onEventMethod(control.getView(), e1, e2, distanceX, distanceY, IMainFrame.ON_SCROLL);
    }

    /**
     *
     */
    public void onLongPress(MotionEvent e) {
        control.getMainFrame().onEventMethod(control.getView(), e, null, -1.0f, -1.0f, IMainFrame.ON_LONG_PRESS);
    }

    /**
     *
     */
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return control.getMainFrame().onEventMethod(control.getView(), e1, e2, velocityX, velocityY, IMainFrame.ON_FLING);
    }

    /**
     *
     */
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return control.getMainFrame().onEventMethod(control.getView(), e, null, -1.0f, -1.0f, IMainFrame.ON_SINGLE_TAP_CONFIRMED);
    }

    /**
     *
     */
    public boolean onDoubleTap(MotionEvent e) {
        float zoom = -1f;
        float fitZoom = -1f;
        try {
            zoom = (Float) control.getActionValue(EventConstant.APP_ZOOM_ID, null);
            fitZoom = (Float) control.getActionValue(EventConstant.APP_FIT_ZOOM_ID, null);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return control.getMainFrame().onEventMethod(control.getView(), e, null, zoom, fitZoom, IMainFrame.ON_DOUBLE_TAP);
    }

    /**
     *
     */
    public boolean onDoubleTapEvent(MotionEvent e) {
        return control.getMainFrame().onEventMethod(control.getView(), e, null, -1.0f, -1.0f, IMainFrame.ON_DOUBLE_TAP_EVENT);
    }


    /**
     *
     */
    public void onClick(View v) {
        control.getMainFrame().onEventMethod(control.getView(), null, null, -1.0f, -1.0f, IMainFrame.ON_CLICK);
    }

    /**
     *
     */
    public void computeScroll() {
        if (isFling && mScroller.isFinished()) {
            isFling = false;
            control.actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, null);
            control.actionEvent(EventConstant.SYS_UPDATE_TOOLSBAR_BUTTON_STATUS, null);
        }
    }

    /**
     * Fling the scroll view
     *
     * @param velocityX X方向速率
     * @param velocityY Y方向速率
     */
    public void fling(int velocityX, int velocityY) {
    }

    /**
     *
     */
    public void stopFling() {
        if (mScroller != null && !mScroller.isFinished()) {
            isFling = true;
            mScroller.abortAnimation();
        }
    }

    /**
     * get middle X axis of double point
     */
    public int getMiddleXOfDoublePoint() {
        return this.midXDoublePoint;
    }

    /**
     * get middle Y axis of double point
     */
    public int getMiddleYOfDoublePoint() {
        return this.midYDoublePoint;
    }

    /**
     *
     */
    public void dispose() {
        control = null;
        gesture = null;
        mVelocityTracker = null;
        toast = null;
        if (mScroller != null && !mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
        mScroller = null;
    }

    //
    protected boolean isFling;
    //
    protected boolean isScroll;
    //
    protected boolean singleTabup = false;
    //
    protected boolean zoomChange;
    //
    protected int mMinimumVelocity;
    //
    protected int mMaximumVelocity;
    //
    protected int midXDoublePoint;
    //
    protected int midYDoublePoint;
    //
    protected int mActivePointerId = -1;
    //
    protected IControl control;
    //
    protected GestureDetector gesture;
    //
    protected VelocityTracker mVelocityTracker;
    // 
    protected Scroller mScroller;
    //toast
    protected Toast toast = null;

    private float downZoom = 1f;
    private float downDistance = 1f;


}
