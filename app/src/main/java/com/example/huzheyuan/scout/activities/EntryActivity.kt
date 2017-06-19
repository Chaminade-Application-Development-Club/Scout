package com.example.huzheyuan.scout.activities

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Window
import com.example.huzheyuan.scout.R
import com.example.huzheyuan.scout.uiAdapter.EntryAdapter
import com.example.huzheyuan.scout.utilities.SpaceItemDecoration
import com.marshalchen.ultimaterecyclerview.ObservableScrollState
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks
import com.marshalchen.ultimaterecyclerview.URLogs
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView
import com.marshalchen.ultimaterecyclerview.itemTouchHelper.SimpleItemTouchHelperCallback


class EntryActivity : AppCompatActivity() {
    private lateinit var gameList:UltimateRecyclerView
    private lateinit var layoutManager:LinearLayoutManager
    private lateinit var adapter:EntryAdapter
    private lateinit var toolbar:Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_entry)
        findView()
        //RecyclerView Init
        adapter = EntryAdapter(this)
        gameList.setHasFixedSize(true)
        gameList.layoutManager = layoutManager
        gameList.addItemDecoration(SpaceItemDecoration(90))
        gameList.setAdapter(adapter)
        gameList.showFloatingActionMenu()
        gameList.showFloatingButtonView()
        //Toolbar Init
        toolbar.setTitle("Scout")
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.inflateMenu(R.layout.entry_toolbar_menu)
    }

    override fun onStart() {
        super.onStart()
        //关联ItemTouchHelper和RecyclerView
        val callback = SimpleItemTouchHelperCallback(adapter)
        val mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper.attachToRecyclerView(gameList.mRecyclerView)
        auto_fam()
    }

    private fun findView(){
        gameList = findViewById(R.id.GameList) as UltimateRecyclerView
        layoutManager = LinearLayoutManager(this)
        toolbar = findViewById(R.id.toolbar_entry) as Toolbar
    }

    private fun auto_fam(){
        var lastY = 0
        gameList.setScrollViewCallbacks(object : ObservableScrollViewCallbacks {
            override fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {
                Log.e("First", firstScroll.toString())
                Log.e("Y", scrollY.toString())
                Log.e("drag", dragging.toString())
                if (firstScroll) lastY = scrollY
                else{
                    if (lastY < scrollY) { //scroll down
                        gameList.hideFloatingActionMenu()
                        lastY = scrollY
                    }
                    else if (lastY > scrollY) { //scroll up
                        gameList.showFloatingActionMenu()
                        lastY = scrollY
                    }
                }
            }
            override fun onDownMotionEvent() {}
            override fun onUpOrCancelMotionEvent(observableScrollState: ObservableScrollState) {
                if (observableScrollState == ObservableScrollState.UP)
                    gameList.hideFloatingActionMenu()
                else if (observableScrollState == ObservableScrollState.DOWN)
                    gameList.showFloatingActionMenu()
            }
        })
    }
}
