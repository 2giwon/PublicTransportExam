package com.egiwon.publictransport.ui.favorite

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.egiwon.publictransport.R

typealias onDeleteListener = (position: Int) -> Unit

class SwipeToDeleteCallback(
    private val onDelete: onDeleteListener,
    dragDirs: Int,
    swipeDirs: Int
) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onDelete(viewHolder.adapterPosition)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(
            c, recyclerView, viewHolder, dX,
            dY, actionState, isCurrentlyActive
        )

        with(viewHolder.itemView) {
            val background = ColorDrawable(recyclerView.context.getColor(R.color.colorPrimary))
            val backgroundCornerOffset = 20
            val icon = recyclerView.context.getDrawable(R.drawable.ic_delete_24px)
            icon?.let { iconDrawable ->
                val iconMargin = (height - iconDrawable.intrinsicHeight) / 2
                val iconTop = top + (height - iconDrawable.intrinsicHeight) / 2
                val iconBottom = iconTop + iconDrawable.intrinsicHeight

                if (dX < 0) {
                    val iconLeft = right - iconMargin - iconDrawable.intrinsicWidth
                    val iconRight = right - iconMargin
                    iconDrawable.setBounds(iconLeft, iconTop, iconRight, iconBottom)

                    background.setBounds(
                        right + dX.toInt() - backgroundCornerOffset,
                        top, right, bottom
                    )
                } else {
                    background.setBounds(0, 0, 0, 0)
                }

                background.draw(c)
                iconDrawable.draw(c)
            }

        }

    }
}