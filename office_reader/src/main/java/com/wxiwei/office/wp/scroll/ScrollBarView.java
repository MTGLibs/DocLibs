package com.wxiwei.office.wp.scroll;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.wxiwei.office.R;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class ScrollBarView extends View {
    private Bitmap bitmapScroll;
    private boolean isShowScroll;
    private Rect rectBitmap;
    private RectF rectScroll;
    private Runnable runnableShow;
    private float scrollBarMarginBottom;
    private float scrollBarMarginTop;
    private Function1<? super Float, Unit> scrollTo;
    private float spaceY;
    private long timeHide;

    public ScrollBarView(Context context) {
        this(context, null, 0, 6, null);
    }

    public ScrollBarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ ScrollBarView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScrollBarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.rectScroll = new RectF();
        this.rectBitmap = new Rect();
        this.timeHide = 5000L;
//        this.runnableShow = new Runnable() { // from class: volio.tech.documentreader.framework.presentation.doc.customview.ScrollBarView$runnableShow$1
//            @Override // java.lang.Runnable
//            public final void run() {
//                Function1 function1;
//                boolean z;
//                ScrollBarView.this.isShowScroll = false;
//                function1 = ScrollBarView.this.onShowScroll;
//                z = ScrollBarView.this.isShowScroll;
//                function1.invoke(Boolean.valueOf(z));
//                ScrollBarView.this.postInvalidate();
//            }
//        };
        getScrollBitmap();
        initValue();
    }
    public static final float convertDpToPx(Resources resources, float f) {
        Intrinsics.checkNotNullParameter(resources, "resources");
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, f, resources.getDisplayMetrics());
    }


    private  void initValue() {
        Context context = getContext();
        this.scrollBarMarginTop = convertDpToPx(context.getResources(), 8);
        this.scrollBarMarginBottom = convertDpToPx(context.getResources(), 8);
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        setRectScroll();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setRectScroll() {
        this.rectScroll.set(0.0f, this.scrollBarMarginTop, getWidth(), (getWidth() * this.rectBitmap.width()) / this.rectBitmap.height());
    }

    public final void setScrollListener(Function1<? super Float, Unit> scrollTo) {
        Intrinsics.checkNotNullParameter(scrollTo, "scrollTo");
        this.scrollTo = scrollTo;
    }

//    public final void setTouchScrollListener(Function1<? super Boolean, Unit> onTouchScroll) {
//        Intrinsics.checkNotNullParameter(onTouchScroll, "onTouchScroll");
//        this.onTouchScroll = onTouchScroll;
//    }
//
//    public final void setShowScrollListener(Function1<? super Boolean, Unit> onShowScroll) {
//        Intrinsics.checkNotNullParameter(onShowScroll, "onShowScroll");
//        this.onShowScroll = onShowScroll;
//    }

    private final void getScrollBitmap() {
        bitmapScroll= BitmapFactory.decodeResource(getResources(),R.drawable.sys_title_bg_vertical );
        ScrollBarView.this.setRectScroll();

//        Glide.with(getContext()).asBitmap().load(Integer.valueOf((int) R.drawable.ic_scroll_to_page)).into((RequestBuilder<Bitmap>) new CustomTarget<Bitmap>() { // from class: volio.tech.documentreader.framework.presentation.doc.customview.ScrollBarView$getScrollBitmap$1
//            @Override // com.bumptech.glide.request.target.Target
//            public void onLoadCleared(Drawable drawable) {
//            }
//
//            @Override // com.bumptech.glide.request.target.Target
//            public /* bridge */ /* synthetic */ void onResourceReady(Object obj, Transition transition) {
//                onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
//            }
//
//            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//                Bitmap bitmap;
//                Rect rect;
//                Rect rect2;
//                Rect rect3;
//                Intrinsics.checkNotNullParameter(resource, "resource");
//                ScrollBarView.this.bitmapScroll = resource;
//                bitmap = ScrollBarView.this.bitmapScroll;
//                if (bitmap != null) {
//                    int width = bitmap.getWidth();
//                    int height = bitmap.getHeight();
//                    rect3 = ScrollBarView.this.rectBitmap;
//                    rect3.set(0, 0, height, width);

//                }
//                StringBuilder sb = new StringBuilder();
//                sb.append("onResourceReady: ");
//                rect = ScrollBarView.this.rectBitmap;
//                sb.append(rect.width());
//                sb.append(' ');
//                rect2 = ScrollBarView.this.rectBitmap;
//                sb.append(rect2.height());
//                Log.d("TAG", sb.toString());
//                ScrollBarView.this.postInvalidate();
//            }
//        });
    }

    public static /* synthetic */ void setStatusScroll$default(ScrollBarView scrollBarView, boolean z, long j, int i, Object obj) {
        if ((i & 2) != 0) {
            j = 0;
        }
        scrollBarView.setStatusScroll(z, j);
    }

    public final void setStatusScroll(boolean z, long j) {
        this.timeHide = j;
        getHandler().removeCallbacks(this.runnableShow);
        if (z) {
            getHandler().postDelayed(this.runnableShow, j);
        }
        this.isShowScroll = z;
//        this.onShowScroll.invoke(Boolean.valueOf(z));
        postInvalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.isShowScroll && canvas != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("onDraw: ");
            sb.append(this.bitmapScroll == null);
            Log.d("zzz", sb.toString());
            canvas.save();
            float width = this.rectScroll.width() / this.rectBitmap.width();
            canvas.scale(width, width, 0.0f, this.rectScroll.top);
            Bitmap bitmap = this.bitmapScroll;
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0.0f, this.rectScroll.top, (Paint) null);
            }
            canvas.restore();
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Integer valueOf = motionEvent != null ? Integer.valueOf(motionEvent.getAction()) : null;
        if (valueOf != null && valueOf.intValue() == 0) {
            if (!this.rectScroll.contains(motionEvent.getX(), motionEvent.getY()) || !this.isShowScroll) {
                return false;
            }
//            this.onTouchScroll.invoke(true);
            this.spaceY = motionEvent.getY() - this.rectScroll.top;
        } else if (valueOf != null && valueOf.intValue() == 2) {
            float y = motionEvent.getY() - this.spaceY;
            if (this.rectScroll.height() + y + this.scrollBarMarginBottom > getHeight()) {
                y = (getHeight() - this.scrollBarMarginBottom) - this.rectScroll.height();
            }
            float f = this.scrollBarMarginTop;
            if (y - f < 0) {
                y = f;
            }
            RectF rectF = this.rectScroll;
            rectF.bottom = rectF.height() + y;
            this.rectScroll.top = y;
            postInvalidate();
            this.scrollTo.invoke(Float.valueOf((this.rectScroll.top - this.scrollBarMarginTop) / (((getHeight() - this.scrollBarMarginBottom) - this.scrollBarMarginTop) - this.rectScroll.height())));
            getHandler().removeCallbacks(this.runnableShow);
            getHandler().postDelayed(this.runnableShow, this.timeHide);
        } else if (valueOf != null && valueOf.intValue() == 1) {
//            this.onTouchScroll.invoke(false);
        }
        return true;
    }

    public final void setScrollPercent(float f) {
        float f2 = 0.0f;
        if (f >= 0.0f) {
            f2 = f;
        }
        if (f > 1.0f) {
            f2 = 1.0f;
        }
        float height = this.scrollBarMarginTop + (f2 * (((getHeight() - this.scrollBarMarginBottom) - this.scrollBarMarginTop) - this.rectScroll.height()));
        RectF rectF = this.rectScroll;
        rectF.bottom = rectF.height() + height;
        this.rectScroll.top = height;
        postInvalidate();
    }
}