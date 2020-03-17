package com.ysf.module_main.view;

import androidx.multidex.MultiDexApplication;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.ysf.module_main.model.MyModel;

public class App extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化model
        MyModel.getInstance().init(this);
        //初始化环信SDK
        EMOptions emOptions = new EMOptions();
        emOptions.setAutoAcceptGroupInvitation(false);
        emOptions.setAcceptInvitationAlways(false);
        emOptions.setAutoLogin(false);
        EaseUI.getInstance().init(this,emOptions);
    }


}
