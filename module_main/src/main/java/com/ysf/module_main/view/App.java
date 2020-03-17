package com.ysf.module_main.view;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.hyphenate.chat.EMOptions;

public class App extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        EMOptions emOptions = new EMOptions();
        emOptions.setAcceptInvitationAlways(false);
        emOptions.setAutoAcceptGroupInvitation(false);
//        EaseUI.getInstance().init(this,emOptions);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
