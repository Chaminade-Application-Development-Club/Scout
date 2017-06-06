package com.example.huzheyuan.scout.uiAdapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

import com.example.huzheyuan.scout.R
import com.example.huzheyuan.scout.activities.Frc2017Activity
import com.example.huzheyuan.scout.activities.Vex2016Activity
import com.example.huzheyuan.scout.activities.Vex2017Activity
import com.example.huzheyuan.scout.dataSet.GameSet
import com.example.huzheyuan.scout.interfaces.onMoveAndSwipedListener
import java.util.*

/**
 * Created by Zheyuan on 6/5/2017.
 */

class EntryAdapter(private val context: Context)
    : RecyclerView.Adapter<EntryAdapter.ViewHolder>(),  onMoveAndSwipedListener{

    private val vex2016:GameSet = GameSet("Vex 2016",R.mipmap.vexstarstruck)
    private val frc2017:GameSet = GameSet("FRC Steamwork",R.mipmap.splashscreenfrc)
    private val vex2017:GameSet = GameSet("Vex In The Zone", R.mipmap.vexzoneentry)
    private val gameName = mutableListOf(vex2016,frc2017,vex2017)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): EntryAdapter.ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.entry_adapter, viewGroup, false)
        val vh = ViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(viewHolder: EntryAdapter.ViewHolder, i: Int) {
        val j = i
        viewHolder.game_Pic.setImageResource(gameName[i].picID)
        viewHolder.titleTxt.text = gameName[i].gameName

        //为btn_share btn_readMore cardView设置点击事件
        viewHolder.playBtn.setOnClickListener {
            var intent = Intent()
            if (gameName[j].gameName.equals("FRC Steamwork", ignoreCase = true)) {
                intent = Intent(context, Frc2017Activity::class.java)
            } else if (gameName[j].gameName.equals("Vex 2016", ignoreCase = true)) {
                intent = Intent(context, Vex2016Activity::class.java)
            }
            else if (gameName[j].gameName.equals("Vex In The Zone", ignoreCase = true)){
                intent = Intent(context, Vex2017Activity::class.java)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return gameName.size
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(gameName,fromPosition,toPosition)
        notifyItemMoved(fromPosition,toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        gameName.removeAt(position)
        notifyItemRemoved(position)
    }

    //自定义ViewHolder类
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardView: CardView
        var game_Pic: ImageView
        var titleTxt: TextView
        var playBtn: ImageButton

        init {
            cardView = itemView.findViewById(R.id.cardView) as CardView
            game_Pic = itemView.findViewById(R.id.entry_pic_img) as ImageView
            titleTxt = itemView.findViewById(R.id.entry_name_txt) as TextView
            playBtn = itemView.findViewById(R.id.playBtn) as ImageButton
            titleTxt.setBackgroundColor(Color.argb(0, 0, 0, 0))
        }
    }
}