package com.example.huzheyuan.scout.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.huzheyuan.scout.R
import com.example.huzheyuan.scout.dataSet.GameSet
import com.example.huzheyuan.scout.uiAdapter.EntryAdapter
import com.example.huzheyuan.scout.utilities.SpaceItemDecoration
import kotlin.collections.ArrayList

class EntryActivity : AppCompatActivity() {
    private lateinit var gameList:RecyclerView
    private lateinit var layoutManager:LinearLayoutManager
    private lateinit var adapter:EntryAdapter
    private val vex2016:GameSet = GameSet("Vex 2016",R.mipmap.vexstarstruck)
    private val frc2017:GameSet = GameSet("FRC Steamwork",R.mipmap.splashscreenfrc)
    private val gameName:List<GameSet> = mutableListOf(vex2016,frc2017)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
        findView()
        adapter = EntryAdapter(gameName,this)
        gameList.setHasFixedSize(true)
        gameList.layoutManager = layoutManager
        gameList.addItemDecoration(SpaceItemDecoration(90))
        gameList.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
    }

    private fun findView(){
        gameList = findViewById(R.id.GameList) as RecyclerView
        layoutManager = LinearLayoutManager(this)
    }
}
