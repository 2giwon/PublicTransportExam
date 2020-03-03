package com.egiwon.publictransport.ui.favorite

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.egiwon.publictransport.R

class SwipeToDeleteCallback(
    private val onDelete: onRemoveItemListener,
    dragDirs: Int,
    swipeDirs: Int
) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    private var startIndex = 0
    private var endIndex = 0
    private var isSwipe = false

    override fun isLongPressDragEnabled(): Boolean = true

    override fun isItemViewSwipeEnabled(): Boolean = true

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        (recyclerView.adapter as? FavoriteAdapter)?.moveItems(
            viewHolder.adapterPosition,
            target.adapterPosition
        )
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        isSwipe = true
        onDelete(viewHolder.adapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            startIndex = viewHolder?.adapterPosition ?: 0
            viewHolder?.itemView?.alpha = 0.75f
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if (isSwipe) {
            isSwipe = false
        } else {
            endIndex = viewHolder.adapterPosition
            viewHolder.itemView.alpha = 1f
            (recyclerView.adapter as? FavoriteAdapter)?.onEndMovedItem(startIndex, endIndex)
        }
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