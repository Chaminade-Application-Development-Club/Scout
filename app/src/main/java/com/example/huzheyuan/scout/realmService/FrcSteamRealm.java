package com.example.huzheyuan.scout.realmService;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by huzhe on 1/22/2017.
 */

public class FrcSteamRealm extends RealmObject{
    private String teamName;

    @Ignore
    private int sessionId;

    public int    getSessionId() { return sessionId; }
    public void   setSessionId(int sessionId){
        this.sessionId = sessionId;
    }
}
