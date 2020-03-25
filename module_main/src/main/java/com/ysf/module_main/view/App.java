package com.ysf.module_main.view;

import androidx.multidex.MultiDexApplication;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.idescout.sql.SqlScoutServer;
import com.ysf.module_main.model.MyModel;

public class App extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        SqlScoutServer.create(this, getPackageName());
        //初始化环信SDK
        EMOptions emOptions = new EMOptions();
        emOptions.setAutoAcceptGroupInvitation(false);
        emOptions.setAcceptInvitationAlways(false);
        emOptions.setAutoLogin(false);
        EaseUI.getInstance().init(this,emOptions);
        //初始化model
        MyModel.getInstance().init(this);
    }

}
