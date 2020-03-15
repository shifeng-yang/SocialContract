package com.ysf.module_main.view;

import androidx.multidex.MultiDexApplication;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;

public class App extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        EMOptions emOptions = new EMOptions();
        emOptions.setAutoAcceptGroupInvitation(false);
        emOptions.setAcceptInvitationAlways(false);
        EaseUI.getInstance().init(this,emOptions);
    }
}
