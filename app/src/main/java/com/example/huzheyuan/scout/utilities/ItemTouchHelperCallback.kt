package com.example.huzheyuan.scout.utilities

/**
 * Created by Zheyuan on 6/6/2017.
 */

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.example.huzheyuan.scout.interfaces.onMoveAndSwipedListener

class ItemTouchHelperCallback
(private val moveAndSwipedListener: onMoveAndSwipedListener) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            //单列的RecyclerView支持上下拖动和左右侧滑
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
            return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
        } else {
            //多列的RecyclerView支持上下左右拖动和不支持左右侧滑
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            val swipeFlags = 0
            return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
        }
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder): Boolean {
        //如果两个item不是同一个类型的，不让他拖拽
        if (viewHolder.itemViewType != target.itemViewType) {
            return false
        }
        moveAndSwipedListener.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        moveAndSwipedListener.onItemDismiss(viewHolder.adapterPosition)
    }
}