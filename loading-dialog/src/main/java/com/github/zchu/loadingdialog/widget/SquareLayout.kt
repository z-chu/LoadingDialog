package com.github.zchu.loadingdialog.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.github.zchu.loadingdialog.R

class SquareLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var squareEnabled: Boolean = true

    init {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SquareLayout)
            squareEnabled = typedArray.getBoolean(
                R.styleable.SquareLayout_square_enabled,
                false
            )
            typedArray.recycle()
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (!squareEnabled) {
            val childAt = getChildAt(0)
            if (childAt != null) {
                val layoutParams = childAt.layoutParams
                if (childAt is ViewGroup && layoutParams.width != ViewGroup.LayoutParams.WRAP_CONTENT) {
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                    childAt.requestLayout()
                }
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (squareEnabled) {
            // Children are just made to fill our space.
            val childWidthSize = measuredWidth
            val childHeightSize = measuredHeight
            //高度和宽度一样
            val localHeightMeasureSpec =
                MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY)
            val localWidthMeasureSpec =
                MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY)
            super.onMeasure(localWidthMeasureSpec, localHeightMeasureSpec)
        }
    }
}