package com.example.huzheyuan.scout.interfaces

/**
 * Created by Zheyuan on 6/6/2017.
 */

interface onMoveAndSwipedListener {
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean
    fun onItemDismiss(position: Int)
}
