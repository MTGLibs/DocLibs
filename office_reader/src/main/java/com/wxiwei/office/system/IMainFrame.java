/*
 * 文件名称:          IActivity.java
 *
 * 编译器:            android2.2
 * 时间:              下午5:24:10
 */
package com.wxiwei.office.system;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;

/**
 * activity interface
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2012-5-15
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:
 * <p>
 * <p>
 */
public interface IMainFrame {
    // onTouch
    byte ON_TOUCH = 0;
    // onDown
    byte ON_DOWN = 1;
    // onShowPresso
    byte ON_SHOW_PRESS = 2;
    // onSingleTapUp
    byte ON_SINGLE_TAP_UP = 3;
    // onScroll
    byte ON_SCROLL = 4;
    // onLongPress
    byte ON_LONG_PRESS = 5;
    // onFling
    byte ON_FLING = 6;
    // onSingleTapConfirmed
    byte ON_SINGLE_TAP_CONFIRMED = 7;
    // onDoubleTap
    byte ON_DOUBLE_TAP = 8;
    // onDoubleTapEvent
    byte ON_DOUBLE_TAP_EVENT = 9;
    // onClick
    byte ON_CLICK = 10;

    byte ON_ZOOM_START = 11;

    byte ON_ZOOM_END = 12;

    byte ON_ZOOM_CHANGE = 13;

    byte ON_HYPER_LINK_URL = 14;

    byte ON_HYPER_LINK_BOOK_MARK = 15;


    /**
     * get activity instance
     *
     * @return activity instance
     */
    Activity getActivity();

    /**
     * do action
     *
     * @param actionID action ID
     * @param obj      acValue
     * @return True if the listener has consumed the event, false otherwise.
     */
    boolean doActionEvent(int actionID, Object obj);

    /**
     * reader file finish call this method
     */
    void openFileFinish();

    /**
     * update tool bar status
     */
    void updateToolsbarStatus();

    /**
     * set the find back button and find forward button state
     *
     * @param state
     */
    void setFindBackForwardState(boolean state);

    /**
     * get bottom  bar height
     *
     * @return bottom bar height
     */
    public int getBottomBarHeight();


    String getAppName();

    /**
     * @return
     */
    File getTemporaryDirectory();

    boolean onEventMethod(View v, MotionEvent e1, MotionEvent e2, float xValue, float yValue, byte eventMethodType);

    boolean isDrawPageNumber();

    /**
     * true: show message when zooming
     * false: not show message when zooming
     *
     * @return
     */
    boolean isShowZoomingMsg();

    /**
     * true: pop up dialog when throw err
     * false: not pop up dialog when throw err
     *
     * @return
     */
    boolean isPopUpErrorDlg();

    /**
     * show progress bar or not when parsing document
     *
     * @return
     */
    boolean isShowProgressBar();

    /**
     * show txt encode dialog when parse txt file
     *
     * @return
     */
    boolean isShowTXTEncodeDlg();

    /**
     * get txt default encode when not showing txt encode dialog
     *
     * @return null if showing txt encode dialog
     */
    String getTXTDefaultEncode();

    /**
     * is support zoom in / zoom out
     *
     * @return true  touch zoom
     * false don’t touch zoom
     */

    boolean isTouchZoom();

    /**
     * normal view, changed after zoom bend, you need to re-layout
     *
     * @return true   re-layout
     * false  don't re-layout
     */
    boolean isZoomAfterLayoutForWord();

    /**
     * Word application default view (Normal or Page)
     *
     * @return 0, page view
     * 1，normal view;
     */
    byte getWordDefaultView();

    /**
     * get Internationalization resource
     *
     * @param resName Internationalization resource name
     */
    String getLocalString(String resName);

    /**
     * callback this method after zoom change
     */
    void changeZoom(int percent);

    /**
     *
     */
    void changePage();

    /**
     *
     */
    void completeLayout();

    /**
     * when engine error occurred callback this method
     */
    void error(int errorCode);

    /**
     * full screen, not show top tool bar
     */
    void fullScreen(boolean fullscreen);

    /**
     * @param visible
     */
    void showProgressBar(boolean visible);

    /**
     * @param viewList
     */
    void updateViewImages(List<Integer> viewList);


    /**
     * set change page flag, Only when effectively the PageSize greater than ViewSize.
     * (for PPT, word print mode, PDF)
     *
     * @param b = true, change page
     *          = false, don't change page
     */
    public boolean isChangePage();

    /**
     * when need destroy office engine instance callback this method
     */
    //public void destroyEngine();

    /**
     *
     * @param saveLog
     */
    /**
     * @return
     */
    public boolean isWriteLog();

    /**
     * @param isThumbnail
     */
    public void setThumbnail(boolean isThumbnail);

    /**
     * @return
     */
    public boolean isThumbnail();

    /**
     * get view backgrouond
     *
     * @return
     */
    public Object getViewBackground();

    /**
     * set flag whether fitzoom can be larger than 100% but smaller than the max zoom
     * @param ignoreOriginalSize
     */

    /**
     * @return true fitzoom may be larger than 100% but smaller than the max zoom
     * false fitzoom can not larger than 100%
     */
    public boolean isIgnoreOriginalSize();

    /**
     * page list view moving position
     *
     * @param position horizontal or vertical
     */
    public byte getPageListViewMovingPosition();

    /**
     *
     */
    void dispose();

    void onWordScrollPercentY(float scrollY);


    void pageChanged(int page, int pageCount);

    void onLoadComplete(int nbPages);

    boolean tap(MotionEvent e);

    void getSlideImages(List<Bitmap> bitmaps);

}
