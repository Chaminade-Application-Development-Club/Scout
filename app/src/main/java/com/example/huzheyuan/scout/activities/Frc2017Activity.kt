package com.example.huzheyuan.scout.activities

import android.Manifest
import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.TextView

import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.example.huzheyuan.scout.R
import com.example.huzheyuan.scout.qRCode.QRUtils
import com.example.huzheyuan.scout.realmService.FrcSteamRealm
import com.example.huzheyuan.scout.realmService.RealmUtils
import com.example.huzheyuan.scout.utilities.FloatActionUtil
import com.example.huzheyuan.scout.utilities.IconUtil
import com.example.huzheyuan.scout.utilities.ScoreUtil
import com.example.huzheyuan.scout.utilities.ScreenUtil
import com.example.huzheyuan.scout.utilities.TimerUtils
import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu
import com.google.zxing.WriterException
import com.yanzhenjie.permission.AndPermission

import java.util.LinkedList

import butterknife.ButterKnife
import io.realm.Realm
import android.content.ContentValues.TAG

class Frc2017Activity : AppCompatActivity() {

    internal lateinit var frame: RelativeLayout
    internal lateinit var fabAuto: FloatingActionButton
    internal lateinit var fabTeleop: FloatingActionButton
    internal lateinit var fabQR: FloatingActionButton
    internal lateinit var fabEdit: FloatingActionButton
    internal lateinit var fabReset: FloatingActionButton
    internal lateinit var fabLeftGear: FloatingActionButton
    internal lateinit var fabRightGear: FloatingActionButton
    internal lateinit var fabLeftFuel: FloatingActionButton
    internal lateinit var fabRightFuel: FloatingActionButton
    internal var famAction: FloatingActionMenu? = null
    internal lateinit var famFunction: FloatingActionMenu
    internal lateinit var fAU: FloatActionUtil
    internal lateinit var countDownFRC: TextView
    internal lateinit var teamIDText: TextView
    internal lateinit var xyText: TextView
    internal lateinit var gameNumber: EditText
    internal lateinit var toolbar: Toolbar
    internal lateinit var sideSwitch: Switch
    internal lateinit var positionPicker: NumberPicker

    internal var gear = 0
    internal var lowGoal = 0
    internal var highGoal = 0
    internal var startTime = 0
    internal var nowTime = 0
    internal var time: Long = 0
    internal var x = 0f
    internal var y = 0f
    internal var gameID = ""
    internal var teamID = ""
    internal var initPos = "Top"
    internal var editLabel = "Edit Game Setting"
    internal var positionArray = arrayOf("Top", "Middle", "Bottom")
    internal var leftSide = true
    internal var createGame = false
    internal var inGame = false
    internal var lifted = false
    internal var gameModeAuto = true
    internal var waste = true
    internal var isHighGoal = false
    private var mProgressTypes: LinkedList<FloatActionUtil.ProgressType>? = null
    private val mMaxProgress = 100

    internal var realm: Realm ?= null
    internal var autoTimer: CountDownTimer? = null
    internal var teleopTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frc2017)
        ButterKnife.bind(this)
        runTimePermission()
        Realm.init(this)
        realm = RealmUtils.openOrCreateRealm(this, "frcData.realm")
        findView() // bind all view components
        setSupportActionBar(toolbar)
        val iconUtil = IconUtil(this@Frc2017Activity, "Frc2017")
        initFamFunction(iconUtil)
        initScout(iconUtil)
        frame.addView(iconUtil)
    }

    override fun onDestroy() {
        super.onDestroy()
        val tU = TimerUtils()
        tU.timerBug(autoTimer, teleopTimer)
        realm?.close() // Remember to close Realm when done.
    }

    private fun findView() {
        famFunction = findViewById(R.id.multiFunctionFAB) as FloatingActionMenu
        fabAuto = findViewById(R.id.fabAuto) as FloatingActionButton
        fabTeleop = findViewById(R.id.fabTeleop) as FloatingActionButton
        fabQR = findViewById(R.id.fabGenerateQR) as FloatingActionButton
        fabEdit = findViewById(R.id.fabEdit) as FloatingActionButton
        fabReset = findViewById(R.id.fabReset) as FloatingActionButton
        fabLeftGear = findViewById(R.id.redGear) as FloatingActionButton
        fabRightGear = findViewById(R.id.blueGear) as FloatingActionButton
        fabLeftFuel = findViewById(R.id.leftFuel) as FloatingActionButton
        fabRightFuel = findViewById(R.id.rightFuel) as FloatingActionButton
        toolbar = findViewById(R.id.toolbar) as Toolbar
        countDownFRC = findViewById(R.id.countDownFrc) as TextView
        teamIDText = findViewById(R.id.teamIDText) as TextView
        xyText = findViewById(R.id.xyText) as TextView
        frame = findViewById(R.id.include) as RelativeLayout
    }

    private fun showNewGame(iconUtil: IconUtil) {
        val dialog = MaterialDialog.Builder(this)
                .title("Create New Game")
                .customView(R.layout.newgamedialog, true)
                .positiveText("Start")
                .negativeText("Cancel")
                .onPositive { dialog, which ->
                    gameNumber = dialog.customView!!.findViewById(R.id.inputTeamId) as EditText
                    teamID = gameNumber.text.toString()
                    teamIDText.text = "Team " + teamID
                    initPos = positionArray[positionPicker.value]
                    initPosition(leftSide, positionArray[positionPicker.value], iconUtil)
                    createGame = true
                    famFunction.close(true)
                    fabEdit.showButtonInMenu(true)
                }
                .onNegative { dialog, which -> famFunction.close(true) }
                .build()
        positionPicker = dialog.customView!!.findViewById(R.id.positionPicker) as NumberPicker
        pickerInit(positionPicker)
        sideSwitch = dialog.customView!!.findViewById(R.id.sideSwitch) as Switch
        changeSide(sideSwitch)
        dialog.show()
    }

    private fun pickerInit(picker: NumberPicker) {
        picker.minValue = 0
        picker.maxValue = positionArray.size - 1
        picker.displayedValues = positionArray
        picker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS // disable keyboard
        picker.wrapSelectorWheel = false
    }

    private fun initPosition(side: Boolean, position: String, iconUtil: IconUtil) {
        val screenUtil = ScreenUtil()
        val x: Float
        val y: Float
        if (side)
            x = 130.toFloat() / 1024.toFloat() * screenUtil.getWidth(this).toFloat()
        else
            x = 830.toFloat() / 1024.toFloat() * screenUtil.getWidth(this).toFloat()
        if (position == "Top")
            y = 80.toFloat() / 552.toFloat() * screenUtil.getHeight(this).toFloat()
        else if (position == "Middle")
            y = 160.toFloat() / 552.toFloat() * screenUtil.getHeight(this).toFloat()
        else
            y = 240.toFloat() / 552.toFloat() * screenUtil.getHeight(this).toFloat()
        iconUtil.bitmapX = x
        iconUtil.bitmapY = y
        iconUtil.invalidate()
    }

    private fun changeSide(mSwitch: Switch) {
        mSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                leftSide = false
            else
                leftSide = true
        }
    }

    fun moveIcon(iconUtil: IconUtil) {
        iconUtil.setOnTouchListener { v, event ->
            if (!famFunction.isOpened) { //User Friendly Feature, prevent misbehaviour
                iconUtil.bitmapX = event.x - 36 * resources.displayMetrics.density
                iconUtil.bitmapY = event.y - 44 * resources.displayMetrics.density
                invalidateIcon(this@Frc2017Activity, iconUtil)
            }
            true
        }
    }

    fun invalidateIcon(activity: Activity, iconUtil: IconUtil) {
        //调用重绘方法
        val su = ScoreUtil()
        su.mapBoundary(activity, iconUtil)
        iconUtil.invalidate()
        x = iconUtil.bitmapX
        y = iconUtil.bitmapY
    }

    internal fun editAction(iconUtil: IconUtil) {
        fabEdit.setOnClickListener { showNewGame(iconUtil) }
    }

    internal fun resetAction() {
        fabReset.setOnClickListener {
            if (autoTimer != null) {
                autoTimer!!.cancel()
                autoTimer!!.onFinish()
            }
            if (teleopTimer != null) {
                teleopTimer!!.cancel()
                teleopTimer!!.onFinish()
            }
            resetValue()
            famFunction.close(true)
        }
    }

    internal fun gearAction() {
        fabLeftGear.setOnClickListener {
            if (inGame) {
                gear++
            }
        }
        fabRightGear.setOnClickListener {
            if (inGame) {
                gear++
            }
        }
    }

    internal fun fuelAction() {
        fAU = FloatActionUtil()
        fabLeftFuel.setOnClickListener {
            if (inGame) {
                lowGoal++
            }
        }
        fabRightFuel.setOnClickListener {
            if (inGame) {
                lowGoal++
            }
        }
        progressUtil(fabLeftFuel)
        progressUtil(fabRightFuel)
    }

    private fun initFamFunction(iconUtil: IconUtil) {
        fAU = FloatActionUtil()
        famFunction.setOnMenuToggleListener { opened ->
            if (!opened && !createGame) {
                fabEdit.hideButtonInMenu(true)
                fAU.createMenuAnimation(famFunction)
            }
            if (opened && !createGame && !inGame) {
                fabEdit.hideButtonInMenu(true)
                fabReset.hideButtonInMenu(true)
                showNewGame(iconUtil)
            }
            if (opened && createGame) {
                editAction(iconUtil)
                resetAction()
            }
        }
    }

    private fun initScout(iconUtil: IconUtil) {
        val t = TimerUtils()
        fabAuto.setOnClickListener {
            famFunction.close(true)
            inGame = true
            moveIcon(iconUtil)
            fabReset.showButtonInMenu(true)
            t.timerBug(autoTimer, teleopTimer)
            autoTimer = timerSystem(15000, 1000, iconUtil)
            gearAction()
            fuelAction()
            gameModeAuto = true
        }
        fabTeleop.setOnClickListener {
            famFunction.close(true)
            inGame = true
            moveIcon(iconUtil)
            fabReset.showButtonInMenu(true)
            t.timerBug(autoTimer, teleopTimer)
            teleopTimer = timerSystem(105000, 1000, iconUtil)
            gearAction()
            fuelAction()
            gameModeAuto = false
        }
    }

    private fun timerSystem(range: Long, tick: Long, iconUtil: IconUtil): CountDownTimer {
        val sUtil = ScoreUtil()
        return object : CountDownTimer(range, tick) {
            override fun onTick(millisUntilFinished: Long) {
                //insertRealm();
                fabAuto.hideButtonInMenu(true)
                fabTeleop.hideButtonInMenu(true)
                time = millisUntilFinished / 1000
                countDownFRC.text = "Ends in: " + millisUntilFinished / 1000
                insertRealm()
            }

            override fun onFinish() {
                inGame = false
                fabAuto.showButtonInMenu(true)
                fabTeleop.showButtonInMenu(true)
                fabLeftFuel.hideProgress()
                fabRightFuel.hideProgress()
                initPosition(leftSide, initPos, iconUtil)
                countDownFRC.text = "End!"
                //summarize()
            }
        }.start()
    }

    private fun insertRealm() {
        realm?.executeTransaction { realm ->
            val frcSteamRealm = realm.createObject(FrcSteamRealm::class.java)
            frcSteamRealm.gameID = gameID
            frcSteamRealm.teamName = teamID
            frcSteamRealm.gameMode = gameModeAuto
            frcSteamRealm.time = time
            frcSteamRealm.isLifted = lifted
            frcSteamRealm.positionX = x
            frcSteamRealm.positionY = y
            frcSteamRealm.gear = gear
            frcSteamRealm.lowGoal = lowGoal
            frcSteamRealm.highGoal = highGoal
        }
    }

    private fun runTimePermission() {
        AndPermission.with(this)
                .requestCode(100)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .send()
    }

    fun progressUtil(fab: FloatingActionButton) {
        val tU = TimerUtils()
        mProgressTypes = LinkedList<FloatActionUtil.ProgressType>()
        for (type in FloatActionUtil.ProgressType.values()) {
            mProgressTypes!!.offer(type)
        }
        fab.max = mMaxProgress
        fab.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP && isHighGoal) {
                nowTime = tU.timeNow()
                fab.hideProgress()
                highGoal += tU.difference(startTime, nowTime)
                Log.d(TAG, "onTouch: " + highGoal)
                isHighGoal = false
            }
            false
        }
        fab.setOnLongClickListener {
            startTime = tU.startTimer()
            if (inGame)
                isHighGoal = true
            else
                isHighGoal = false
            val type = mProgressTypes!!.poll()
            Log.d(TAG, "onLongClick: " + type)
            fab.setShowProgressBackground(false)
            fab.setIndeterminate(true)
            mProgressTypes!!.offer(FloatActionUtil.ProgressType.PROGRESS_NO_BACKGROUND)
            false
        }
    }

//    private fun summarize() {
//        fabQR.setOnClickListener { qRDialog() }
//    }

//    private fun qRDialog() {
//        val qr = QRUtils()
//        val dialog = MaterialDialog.Builder(this)
//                .title("QR Code")
//                .customView(R.layout.frcqr_dialog, true)
//                .positiveText("Save")
//                .negativeText("Cancel")
//                .onPositive { dialog, which -> famFunction.close(true) }
//                .onNegative { dialog, which -> famFunction.close(true) }
//                .build()
//        val sum = gameID + "\n" + teamID + "\n" + gameModeAuto + "\n" + time + "\n" + lifted
//        + "\n" + gear + "\n" + lowGoal + "\n" + highGoal
//        val qRCodeImage = dialog.customView!!.findViewById(R.id.QRView) as ImageView
//        val sumText = dialog.customView!!.findViewById(R.id.summarizeText) as TextView
//        sumText.text = sum
//        try {
//            // 调用方法createCode生成二维码, using createCode method to create QR code
//            val bm = qr.createCode(this@Frc2017Activity, sum)
//            // 将二维码在界面中显示, show it in the UI
//            qRCodeImage.setImageBitmap(bm)
//        } catch (e: WriterException) {
//            e.printStackTrace()
//        }
//
//        dialog.show()
//    }

    private fun resetValue() {
        gameID = ""
        teamID = ""
        gameModeAuto = true
        time = 0
        lifted = false
        x = 0f
        y = 0f
        gear = 0
        lowGoal = 0
        highGoal = 0
        inGame = false
        createGame = false
    }
}
