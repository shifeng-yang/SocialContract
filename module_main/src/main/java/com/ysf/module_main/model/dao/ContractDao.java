package com.ysf.module_main.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ysf.module_main.model.bean.UserBean;
import com.ysf.module_main.model.db.ContractAndInviteDB;
import java.util.ArrayList;
import java.util.List;

public class ContractDao {
    private ContractAndInviteDB mHelper;
    private SQLiteDatabase mdb;

    public ContractDao(ContractAndInviteDB helper) {
        mHelper = helper;
    }

    public void deleteTable() {
        mdb = mHelper.getReadableDatabase();
        mdb.execSQL("DROP TABLE " + ContractTable.TABLE_NAME);
    }

    /**
     * 获取所有联系人
     * @return 联系人集合
     */
    public List<UserBean> getContracts() {
        mdb = mHelper.getReadableDatabase();
        String sql = "select * from " + ContractTable.TABLE_NAME + " where " + ContractTable.IS_MY_CONTRACT + "=1";
        Cursor cursor = mdb.rawQuery(sql, null);
        List<UserBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            UserBean userInfo = new UserBean();
            userInfo.setHx_id(cursor.getString(cursor.getColumnIndex(ContractTable.HXID)));
            userInfo.setName(cursor.getString(cursor.getColumnIndex(ContractTable.NAME)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(ContractTable.NICK)));
            userInfo.setImgUrl(cursor.getString(cursor.getColumnIndex(ContractTable.IMGURL)));
            list.add(userInfo);
        }
        cursor.close();
        mdb.close();
        return list;
    }

    /**
     * 通过环信id获取单个或多个联系人信息
     * @param hxid 联系人的环信id
     * @return 联系人集合
     */
    public List<UserBean> getContractByHxid(String... hxid) {
        mdb = mHelper.getReadableDatabase();
        String sql = "select * from " + UserAccountTable.TABLE_NAME + " where " + UserAccountTable.HXID + "=?";
        List<UserBean> list = new ArrayList<>();
        UserBean manage;
        for (String id : hxid) {
            Cursor cursor = mdb.rawQuery(sql, new String[] {id});
            while (cursor.moveToNext()) {
                manage = new UserBean();
                manage.setHx_id(cursor.getString(cursor.getColumnIndex(UserAccountTable.HXID)));
                manage.setName(cursor.getString(cursor.getColumnIndex(UserAccountTable.NAME)));
                manage.setNick(cursor.getString(cursor.getColumnIndex(UserAccountTable.NICK)));
                manage.setImgUrl(cursor.getString(cursor.getColumnIndex(UserAccountTable.IMGURL)));
                list.add(manage);
            }
            cursor.close();
        }
        mdb.close();
        return list;
    }

    public void addContract(UserBean user, boolean isMyContract) {
        mdb =  mHelper.getReadableDatabase();
        ContentValues value = new ContentValues();
        value.put(ContractTable.HXID,user.getHx_id());
        value.put(ContractTable.NAME,user.getName());
        value.put(ContractTable.NICK,user.getNick());
        value.put(ContractTable.IMGURL,user.getImgUrl());
        value.put(ContractTable.IS_MY_CONTRACT,isMyContract ? 1 : 0);
        mdb.replace(ContractTable.TABLE_NAME,null,value);
    }

    public void addContract(List<UserBean> users, boolean isMyContract) {
        for (UserBean user : users) {
            addContract(user,isMyContract);
        }
    }

    public void deletContract(String... hxids) {
        mdb = mHelper.getReadableDatabase();
        mdb.delete(ContractTable.TABLE_NAME,ContractTable.HXID + "=?",hxids);
    }

}
