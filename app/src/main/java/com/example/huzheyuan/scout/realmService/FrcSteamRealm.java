package com.example.huzheyuan.scout.realmService;

import io.realm.RealmObject;

/**
 * Created by huzhe on 1/22/2017.
 */

public class FrcSteamRealm extends RealmObject{

    private String gameID;
    private String teamName;
    private boolean gameMode;
    private long time;
    private float positionX;
    private float positionY;
    private int gear;
    private int lowGoal;
    private int highGoal;
    private boolean lifted;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public boolean getGameMode() {
        return gameMode;
    }

    public void setGameMode(boolean gameMode) {
        this.gameMode = gameMode;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public boolean isLifted() {
        return lifted;
    }

    public void setLifted(boolean lifted) {
        this.lifted = lifted;
    }

    public int getGear() {
        return gear;
    }

    public void setGear(int gear) {
        this.gear = gear;
    }

    public int getHighGoal() {
        return highGoal;
    }

    public void setHighGoal(int highGoal) {
        this.highGoal = highGoal;
    }

    public int getLowGoal() {
        return lowGoal;
    }

    public void setLowGoal(int lowGoal) {
        this.lowGoal = lowGoal;
    }
}
