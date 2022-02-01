package com.example.drippledesign

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.sin

class CustomLinearLayoutManager(private val context: Context, private var horizontalScrollOffset: Int = 0) :
    LinearLayoutManager(context,HORIZONTAL,false) {

    private val tag:String = "ArcLayoutManager"

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
        val viewWidth = pxFromDp(90f)
        val viewHeight = pxFromDp(90f)

        for (itemIndex in 0 until itemCount) {

            Log.e(tag, "fill: ${state?.targetScrollPosition}" )
            val view = recycler.getViewForPosition(itemIndex)
            Log.e(tag, "fill: ${view.id}" )
            addView(view)


            val left = (itemIndex * viewWidth) - horizontalScrollOffset
            val right = left + viewWidth
            val top = computeYComponent((left + right) / 2, viewHeight)
            val bottom = top.first + viewHeight

            val alpha = top.second
            view.rotation = (alpha * (180 / PI)).toFloat() - 90f

            measureChildWithMargins(view ?: return, viewWidth.toInt(), viewHeight.toInt())
            layoutDecoratedWithMargins(view, left.toInt(), top.first, right.toInt(), bottom.toInt())
        }

        recycler.scrapList.toList().forEach {
            recycler.recycleView(it.itemView)
        }
    }

    private fun computeYComponent(viewCenterX: Float, h: Float): Pair<Int, Double> {
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