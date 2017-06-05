package com.example.huzheyuan.scout.utilities

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Zheyuan on 6/5/2017.
 */

class SpaceItemDecoration(space:Int):RecyclerView.ItemDecoration(){
    private var space:Int = 0
    init {
        this.space = space
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        if (parent?.getChildPosition(view) != 0)
            outRect?.top = space
    }
}