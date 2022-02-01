package com.example.drippledesign

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.sin

class ArcLayoutManager(private val context: Context, private var horizontalScrollOffset: Int = 0)
    : RecyclerView.LayoutManager() {

    private val tag:String = "ArcLayoutManager"

    private val firstVisiblePosition: Int get() {
        if (childCount == 0) { return 0 }
        return getPosition(getChildAt(0)!!)
    }

    private val lastVisiblePosition: Int get() {
        if (childCount == 0) { return 0 }
        return getPosition(getChildAt(childCount-1)!!)
    }


    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams = RecyclerView.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    override fun canScrollHorizontally(): Boolean = true

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        horizontalScrollOffset += dx
        fill(recycler, state)
        return dx
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        fill(recycler, state)
    }

    private fun fill(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {

        detachAndScrapAttachedViews(recycler ?: return)
        val viewWidth = pxFromDp(65f)
        val viewHeight = pxFromDp(90f)

        for (itemIndex in 0 until itemCount) {
            val view = recycler.getViewForPosition(itemIndex)
            Log.e(tag, "fill: ${view.id}" )
            addView(view)

            val left = (itemIndex * viewWidth) - horizontalScrollOffset
            val right = left + viewWidth
            val top = computeYComponent((left + right) / 2, viewHeight)
            val bottom = top.first + viewHeight
            val alpha = top.second
            view.rotation = (alpha * (180 / PI)).toFloat() - 90f

            measureChild(view, viewWidth.toInt(), viewHeight.toInt())
            layoutDecorated(view, left.toInt(), top.first, right.toInt(), bottom.toInt())
        }

        recycler.scrapList.toList().forEach {
            recycler.recycleView(it.itemView)
        }
    }

    private fun computeYComponent(viewCenterX: Float,
                                  h: Float): Pair<Int, Double> {
        val screenWidth = context.resources.displayMetrics.widthPixels
        val s = screenWidth.toDouble() / 2
        val radius = (h * h + s * s) / (h * 2)

        val xScreenFraction = viewCenterX.toDouble() / screenWidth.toDouble()
        val beta = acos(s / radius)

        val alpha = beta + (xScreenFraction * (Math.PI - (2 * beta)))
        val yComponent = radius - (radius * sin(alpha))

        return Pair(yComponent.toInt(), alpha)
    }

    private fun pxFromDp(dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }


}