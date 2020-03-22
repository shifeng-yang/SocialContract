package com.ysf.module_main.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ysf.module_main.model.bean.UserBean;
import com.ysf.module_main.model.db.UserAccountDB;

public class UserAccountDao {
    private final UserAccountDB mHelp;
    private SQLiteDatabase mDb;

    public UserAccountDao(Context context) {
        mHelp = new UserAccountDB(context);
    }

    public void deleteTable() {
        mDb = mHelp.getReadableDatabase();
        mDb.execSQL("DROP TABLE " + UserAccountTable.TABLE_NAME);
    }

    public void addAccount(UserBean user) {
        mDb = mHelp.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserAccountTable.HXID,user.getHx_id());
        values.put(UserAccountTable.NAME,user.getName());
        values.put(UserAccountTable.NICK,user.getNick());
        values.put(UserAccountTable.IMGURL,user.getImgUrl());
        mDb.replace(UserAccountTable.TABLE_NAME,null,values);
        mDb.close();
    }

    public UserBean getUserAccountByHxid(String hxid) {
        mDb = mHelp.getReadableDatabase();
        String sql = "select * from " + UserAccountTable.TABLE_NAME + " where " + UserAccountTable.HXID + "=?";
        Cursor cursor = mDb.rawQuery(sql, new String[]{hxid});
        UserBean userBean = null;
        while (cursor.moveToNext()) {
            userBean = new UserBean();
            userBean.setHx_id(cursor.getString(cursor.getColumnIndex(UserAccountTable.HXID)));
            userBean.setName(cursor.getString(cursor.getColumnIndex(UserAccountTable.NAME)));
            userBean.setNick(cursor.getString(cursor.getColumnIndex(UserAccountTable.NICK)));
            userBean.setImgUrl(cursor.getString(cursor.getColumnIndex(UserAccountTable.IMGURL)));
        }
        cursor.close();
        mDb.close();
        return userBean;
    }

}
