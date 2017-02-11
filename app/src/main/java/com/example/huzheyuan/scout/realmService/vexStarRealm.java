package com.example.huzheyuan.scout.realmService;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by leo428 on 2/3/17.
 */

public class VexStarRealm extends RealmObject{
    private String teamName;
    private String gameMode;
    private long time;
    private String positionX;
    private String positionY;
    private String SAN;
    private String SDN;
    private String SAF;
    private String SDF;
    private String CAN;
    private String CAF;
    private String CDN;
    private String CDF;
    private String lifted;

    //generated getters and setters
    public String getSAN() {
        return SAN;
    }

    public void setSAN(String SAN) {
        this.SAN = SAN;
    }

    public String getSAF() {
        return SAF;
    }

    public void setSAF(String SAF) {
        this.SAF = SAF;
    }

    public String getSDN() {
        return SDN;
    }

    public void setSDN(String SDN) {
        this.SDN = SDN;
    }

    public String getSDF() {
        return SDF;
    }

    public void setSDF(String SDF) {
        this.SDF = SDF;
    }

    public String getCAN() {
        return CAN;
    }

    public void setCAN(String CAN) {
        this.CAN = CAN;
    }

    public String getCAF() {
        return CAF;
    }

    public void setCAF(String CAF) {
        this.CAF = CAF;
    }

    public String getCDN() {
        return CDN;
    }

    public void setCDN(String CDN) {
        this.CDN = CDN;
    }

    public String getCDF() {
        return CDF;
    }

    public void setCDF(String CDF) {
        this.CDF = CDF;
    }

    public String getLifted() {
        return lifted;
    }

    public void setLifted(String lifted) {
        this.lifted = lifted;
    }

    public String getPositionY() {
        return positionY;
    }

    public void setPositionY(String positionY) {
        this.positionY = positionY;
    }

    public String getPositionX() {
        return positionX;
    }

    public void setPositionX(String positionX) {
        this.positionX = positionX;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

}
