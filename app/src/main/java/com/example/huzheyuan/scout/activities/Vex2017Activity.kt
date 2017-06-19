package com.example.huzheyuan.scout.activities

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Window
import android.widget.*
import com.afollestad.materialdialogs.MaterialDialog
import com.example.huzheyuan.scout.R
import com.example.huzheyuan.scout.utilities.IconUtil
import com.example.huzheyuan.scout.utilities.VibrateUtil
import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu

class Vex2017Activity : AppCompatActivity() {
    private lateinit var map: ImageView
    private lateinit var toolbar:android.support.v7.widget.Toolbar
    private lateinit var frame:RelativeLayout
    private lateinit var super_frame:RelativeLayout
    private lateinit var fam:FloatingActionMenu
    private lateinit var fabEdit:FloatingActionButton
    private lateinit var gameNumber:EditText
    private lateinit var sideSwitch:Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_vex2017)
        findViews()
        toolbarInit()
        val icon = IconUtil(this)
        frame.addView(icon)
        fam.setOnMenuButtonClickListener({
            creatGame()
        })
        map.setOnTouchListener { v, event ->
            //config pointers
            var movePointer:Int
            var moveIndex:Int
            if (event.pointerCount > 1) {
                movePointer = event.getPointerId(0) //1st
                moveIndex = event.findPointerIndex(movePointer)
                // multi touch, handling the second pointer for action
                // 坐标系是相对于处理这个事件的View或者Activity的
                icon.bitmapX = event.getX(moveIndex) - 36*resources.displayMetrics.density
                icon.bitmapY = event.getY(moveIndex) - 44*resources.displayMetrics.density
                // 36 and 44 are magic numbers get from image calculation!
                icon.invalidate()
                if (event.actionIndex == 1 && event.actionMasked == 5) {
                    VibrateUtil(this).buzz() //Constants are from official docs!
                }
                if (event.actionIndex == 1 && event.actionMasked == 6){

                }
            } else {
                //单点触控, handling first pointer for movement
                icon.bitmapX = event.x - 36*resources.displayMetrics.density
                icon.bitmapY = event.y - 44*resources.displayMetrics.density
                // 36 and 44 are magic numbers get from image calculation!
                icon.invalidate()
            }
            true}
    }

    private fun findViews(){
        map = findViewById(R.id.img_vex_2017_map) as ImageView
        toolbar = findViewById(R.id.toolbar_vex_2017) as Toolbar
        frame = findViewById(R.id.vex_2017_map_frame) as RelativeLayout
        super_frame = findViewById(R.id.super_frame) as RelativeLayout
        fam = findViewById(R.id.multi_fam) as FloatingActionMenu
        fabEdit = FloatingActionButton(this)
        fabEdit.buttonSize = FloatingActionButton.SIZE_MINI
        fabEdit.setImageResource(R.drawable.ic_edit_black_24dp)
    }

    private fun toolbarInit(){
        //Toolbar Init
        toolbar.title = "Vex Zone"
        toolbar.setTitleTextColor(Color.WHITE)
    }

    private fun creatGame(){
        val positionArray = arrayOf("Top", "Middle", "Bottom")
        val dialog = MaterialDialog.Builder(this)
                .title("Create New Game")
                .customView(R.layout.newgamedialog, true)
                .positiveText("Start")
                .negativeText("Cancel")
                .onPositive { dialog, which ->
                }
                .onNegative { dialog, which -> fam.close(true) }
                .build()
        val gameNumber = dialog.customView?.findViewById(R.id.inputTeamId) as EditText
        var sideSwitch = dialog.customView!!.findViewById(R.id.sideSwitch) as Switch
        dialog.show()
    }
}
