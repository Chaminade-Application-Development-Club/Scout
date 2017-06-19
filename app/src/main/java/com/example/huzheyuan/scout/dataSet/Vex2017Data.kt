package com.example.huzheyuan.scout.dataSet

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Zheyuan on 6/13/2017.
 */

open class Vex2017Data
(@PrimaryKey var uuid: Int = 0,
                  var gameNumber:String ?= null,
                  var teamID:String ?= null,
                  var x:Int ?= null,
                  var y:Int ?= null)
    :RealmObject() {
}