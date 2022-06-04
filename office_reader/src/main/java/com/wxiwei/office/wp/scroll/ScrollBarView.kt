package com.wxiwei.office.wp.scroll

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.wxiwei.office.R

class ScrollBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var scrollBarMarginTop = 0f
    private var scrollBarMarginBottom = 0f
    private var bitmapScroll: Bitmap? = null
    private var rectScroll = RectF()
    private var rectBitmap = Rect()
    private var isShowScroll = true
    private var scrollTo: ((scrollY: Float) -> Unit) = {
    }
    private var onTouchScroll: ((isTouchDown: Boolean) -> Unit) = {

    }
    private var onShowScroll: ((isShowScroll: Boolean) -> Unit) = {

    }
    private var runnableShow: Runnable = Runnable {
        isShowScroll = false
        onShowScroll(isShowScroll)
        postInvalidate()
    }

    init {
        getScrollBitmap()
        initValue()
    }

    private fun initValue() {
        scrollBarMarginTop = convertDpToPx(8).toFloat()
        scrollBarMarginBottom = convertDpToPx(8).toFloat()

    }

    private fun convertDpToPx(dp: Int): Int {
        val dip = dp.toFloat()
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip,
            resources.displayMetrics
        ).toInt()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        setRectScroll()
    }

    private fun setRectScroll() {
        rectScroll.set(
            0f,
            scrollBarMarginTop,
            width.toFloat(),
            width.toFloat() * rectBitmap.width() / rectBitmap.height()
        )
    }

    public fun setScrollListener(scrollTo: ((scrollY: Float) -> Unit)) {
        this.scrollTo = scrollTo
    }

    public fun setTouchScrollListener(onTouchScroll: ((isTouchDown: Boolean) -> Unit)) {
        this.onTouchScroll = onTouchScroll
    }

    public fun setShowScrollListener(onShowScroll: ((isShowScroll: Boolean) -> Unit)) {
        this.onShowScroll = onShowScroll
    }

    private fun getScrollBitmap() {
        Glide.with(context).asBitmap().load(R.drawable.ic_scroll_to_page)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmapScroll = resource
                    bitmapScroll?.let {
                        val imageHeight: Int = it.width
                        val imageWidth: Int = it.height
                        rectBitmap.set(0, 0, imageWidth, imageHeight)
                        setRectScroll()
                    }
                    Log.d("TAG", "onResourceReady: ${rectBitmap.width()} ${rectBitmap.height()}")
                    postInvalidate()
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })


    }

     fun setStatusScroll(isShow: Boolean, timeHide: Long = 0) {
        handler.removeCallbacks(runnableShow)
        if (isShow) {
            handler.postDelayed(runnableShow, timeHide)
        }
        isShowScroll = isShow
        onShowScroll(isShowScroll)
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        if (isShowScroll) {
            canvas?.apply {
                Log.d("zzz", "onDraw: ${bitmapScroll == null}")
                save()
                val scale = rectScroll.width() / rectBitmap.width()
                scale(scale, scale, 0f, rectScroll.top)
                bitmapScroll?.let { bitmap ->
                    drawBitmap(bitmap, 0f, rectScroll.top, null)
                }
                restore()
            }
        }
    }

    private var spaceY = 0f
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!rectScroll.contains(event.x, event.y) || !isShowScroll) {
                    return false
                }
                onTouchScroll(true)
                spaceY = event.y - rectScroll.top
            }
            MotionEvent.ACTION_MOVE -> {
                var newTop = event.y - spaceY
                if (newTop + rectScroll.height() + scrollBarMarginBottom > height) {
                    newTop = height - scrollBarMarginBottom - rectScroll.height()
                }
                if (newTop - scrollBarMarginTop < 0) {
                    newTop = scrollBarMarginTop
                }
                rectScroll.bottom = newTop + rectScroll.height()
                rectScroll.top = newTop
                postInvalidate()
                scrollTo((rectScroll.top - scrollBarMarginTop) / (height - scrollBarMarginBottom - scrollBarMarginTop - rectScroll.height()))
//                handler.removeCallbacks(runnableShow)
//                handler.postDelayed(runnableShow, timeHide)
            }
            MotionEvent.ACTION_UP -> {
                onTouchScroll(false)
            }
        }
        return true
    }

    fun setScrollPercent(scrollY: Float) {
        var newScroll = scrollY
        if (scrollY < 0f) newScroll = 0f
        if (scrollY > 1f) newScroll = 1f
        val top =
            scrollBarMarginTop + newScroll * (height - scrollBarMarginBottom - scrollBarMarginTop - rectScroll.height())
        rectScroll.bottom = top + rectScroll.height()
        rectScroll.top = top
        postInvalidate()
    }

}