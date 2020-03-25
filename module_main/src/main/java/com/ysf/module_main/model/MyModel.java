package com.ysf.module_main.model;

import android.content.Context;
import com.ysf.module_main.model.bean.UserBean;
import com.ysf.module_main.model.dao.ContractAndinviteManage;
import com.ysf.module_main.model.dao.UserAccountDao;

public class MyModel {
    private static MyModel model;
    private Context mContext;
    private UserAccountDao mUserAccountDao;
    private ContractAndinviteManage mContractAndinviteManage;

    private MyModel() {}

    public static MyModel getInstance() {
        if (model == null) {
            synchronized (MyModel.class) {
                if (model == null) {
                    model = new MyModel();
                }
            }
        }
        return model;
    }

    public void init(Context context) {
        mContext = context;
        //提供数据库对象
        mUserAccountDao = new UserAccountDao(context);
        //注册监听
        new HxEventListener(context);
    }

    public UserAccountDao getUserAccountDao() {
        return mUserAccountDao;
    }

    public void onSuccessLogin(UserBean user) {
        if (mContractAndinviteManage != null) mContractAndinviteManage.close();
        mContractAndinviteManage = new ContractAndinviteManage(mContext, user.getName() + ".db");
    }

    public ContractAndinviteManage getContractAndinviteManage() {
        return mContractAndinviteManage;
    }
}
