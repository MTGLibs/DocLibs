package com.github.barteksc.pdfviewer.scroll;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.Util;
import com.wxiwei.office.R;
import com.wxiwei.office.system.MainControl;

public class DefaultScrollHandle extends RelativeLayout implements ScrollHandle {

    private final static int HANDLE_LONG = 40;
    private final static int HANDLE_SHORT = 40;
    private final static int DEFAULT_TEXT_SIZE = 16;
    private MainControl control;

    public MainControl getControl() {
        return control;
    }

    private float relativeHandlerMiddle = 0f;

    protected TextView textView;
    protected Context context;
    private boolean inverted;
    private PDFView pdfView;
    private float currentPos;

    private Handler handler = new Handler();
    private Runnable hidePageScrollerRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    private Bitmap bitmapScroll;

    public DefaultScrollHandle(Context context, MainControl control) {
        this(context, false);
        this.control = control;
    }

    public DefaultScrollHandle(Context context, boolean inverted) {
        super(context);
        this.context = context;
        this.inverted = inverted;
        textView = new TextView(context);
        setVisibility(INVISIBLE);
        setTextColor(Color.BLACK);
        setTextSize(DEFAULT_TEXT_SIZE);
        Glide.with(context).asBitmap().load(R.drawable.ic_scroll_to_page).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                bitmapScroll = resource;
                postInvalidate();
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });


    }

    @Override
    public void setupLayout(PDFView pdfView) {
        int align, width, height;
        Drawable background;
        // determine handler position, default is right (when scrolling vertically) or bottom (when scrolling horizontally)
        if (pdfView.isSwipeVertical()) {
            width = HANDLE_LONG;
            height = HANDLE_SHORT;

        } else {
            width = HANDLE_SHORT;
            height = HANDLE_LONG;

        }

        setBackgroundResource(R.drawable.ic_scroll_to_page);

        LayoutParams lp = new LayoutParams(Util.getDP(context, width), Util.getDP(context, height));
        lp.setMargins(0, 0, 0, 0);

//        LayoutParams tvlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        tvlp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

//        addView(textView, tvlp);

        lp.addRule(ALIGN_PARENT_RIGHT);
        pdfView.addView(this, lp);

        this.pdfView = pdfView;
    }

    @Override
    public void destroyLayout() {
        pdfView.removeView(this);
    }

    @Override
    public void setScroll(float position) {
        if (!shown()) {
            show();
        } else {
            handler.removeCallbacks(hidePageScrollerRunnable);
        }
        setPosition((pdfView.isSwipeVertical() ? pdfView.getHeight() : pdfView.getWidth()) * position);
    }

    private void setPosition(float pos) {
        if (Float.isInfinite(pos) || Float.isNaN(pos)) {
            return;
        }
        float pdfViewSize;
        if (pdfView.isSwipeVertical()) {
            pdfViewSize = pdfView.getHeight();
        } else {
            pdfViewSize = pdfView.getWidth();
        }
        pos -= relativeHandlerMiddle;

        if (pos < 0) {
            pos = 0;
        } else if (pos > pdfViewSize - Util.getDP(context, HANDLE_SHORT)) {
            pos = pdfViewSize - Util.getDP(context, HANDLE_SHORT);
        }

        if (pdfView.isSwipeVertical()) {
            setY(pos);
        } else {
            setX(pos);
        }

        calculateMiddle();
        invalidate();
    }

    private void calculateMiddle() {
        float pos, viewSize, pdfViewSize;
        if (pdfView.isSwipeVertical()) {
            pos = getY();
            viewSize = getHeight();
            pdfViewSize = pdfView.getHeight();
        } else {
            pos = getX();
            viewSize = getWidth();
            pdfViewSize = pdfView.getWidth();
        }
        relativeHandlerMiddle = ((pos + relativeHandlerMiddle) / pdfViewSize) * viewSize;
    }

    @Override
    public void hideDelayed() {
        handler.postDelayed(hidePageScrollerRunnable, 1000);
    }

    @Override
    public void setPageNum(int pageNum) {
        String text = String.valueOf(pageNum);
        if (!textView.getText().equals(text)) {
            textView.setText(text);
        }
    }

    @Override
    public boolean shown() {
        return getVisibility() == VISIBLE;
    }

    @Override
    public void show() {
        setVisibility(VISIBLE);
    }

    @Override
    public void hide() {
        setVisibility(INVISIBLE);
    }

    public void setTextColor(int color) {
        textView.setTextColor(color);
    }

    /**
     * @param size text size in dp
     */
    public void setTextSize(int size) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
    }

    private boolean isPDFViewReady() {
        return pdfView != null && pdfView.getPageCount() > 0 && !pdfView.documentFitsView();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!isPDFViewReady()) {
            return super.onTouchEvent(event);
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                control.getMainFrame().touchPDFScroll();
            case MotionEvent.ACTION_POINTER_DOWN:
                pdfView.stopFling();
                handler.removeCallbacks(hidePageScrollerRunnable);
                if (pdfView.isSwipeVertical()) {
                    currentPos = event.getRawY() - getY();
                } else {
                    currentPos = event.getRawX() - getX();
                }
            case MotionEvent.ACTION_MOVE:
                if (pdfView.isSwipeVertical()) {
                    setPosition(event.getRawY() - currentPos + relativeHandlerMiddle);
                    pdfView.setPositionOffset(relativeHandlerMiddle / (float) getHeight(), false);
                } else {
                    setPosition(event.getRawX() - currentPos + relativeHandlerMiddle);
                    pdfView.setPositionOffset(relativeHandlerMiddle / (float) getWidth(), false);
                }
                return true;
            case MotionEvent.ACTION_UP:
                control.getMainFrame().hidePDFScroll();
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_POINTER_UP:
                hideDelayed();
                pdfView.performPageSnap();
                return true;
        }

        return super.onTouchEvent(event);
    }
}
