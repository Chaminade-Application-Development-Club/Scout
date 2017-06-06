package com.example.huzheyuan.scout.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.example.huzheyuan.scout.R
import com.example.huzheyuan.scout.uiAdapter.EntryAdapter
import com.example.huzheyuan.scout.utilities.ItemTouchHelperCallback
import com.example.huzheyuan.scout.utilities.SpaceItemDecoration
import com.github.clans.fab.FloatingActionMenu

class EntryActivity : AppCompatActivity() {
    private lateinit var gameList:RecyclerView
    private lateinit var layoutManager:LinearLayoutManager
    private lateinit var adapter:EntryAdapter
    private lateinit var fam:FloatingActionMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
        findView()
        adapter = EntryAdapter(this)
        gameList.setHasFixedSize(true)
        gameList.layoutManager = layoutManager
        gameList.addItemDecoration(SpaceItemDecoration(90))
        gameList.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        gameList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState > 0) {
                    fam.hideMenuButton(true)
                }
                else fam.showMenuButton(true)
            }
        })

        //关联ItemTouchHelper和RecyclerView
        val callback = ItemTouchHelperCallback(adapter)
        val mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper.attachToRecyclerView(gameList)

    }

    private fun findView(){
        gameList = findViewById(R.id.GameList) as RecyclerView
        layoutManager = LinearLayoutManager(this)
        fam = findViewById(R.id.fam_function) as FloatingActionMenu
    }
}
