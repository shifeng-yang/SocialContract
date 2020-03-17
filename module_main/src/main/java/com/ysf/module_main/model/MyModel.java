package com.ysf.module_main.model;

import android.content.Context;
import com.ysf.module_main.model.dao.UserAccountDao;

public class MyModel {
    private static MyModel model;
    private Context mContext;
    public UserAccountDao mDao;

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
        mDao = new UserAccountDao(context);
    }
}
