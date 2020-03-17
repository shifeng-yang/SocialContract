package com.ysf.module_main.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ysf.module_main.model.bean.MyUserManage;
import com.ysf.module_main.model.db.ContractAndInviteDB;

import java.util.ArrayList;
import java.util.List;

public class ContractAndInviteDao {
    private ContractAndInviteDB mHelper;
    private SQLiteDatabase mdb;

    public ContractAndInviteDao(ContractAndInviteDB helper) {
        mHelper = helper;
    }

    public List<MyUserManage> getContracts() {
        mdb = mHelper.getReadableDatabase();
        String sql = "select * from " + ContractTable.TABLE_NAME + " where " + ContractTable.IS_MY_CONTRACT + "=1";
        Cursor cursor = mdb.rawQuery(sql, null);
        List<MyUserManage> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            MyUserManage myUserManage = new MyUserManage();
            myUserManage.setHx_id(cursor.getString(cursor.getColumnIndex(ContractTable.HXID)));
            myUserManage.setName(cursor.getString(cursor.getColumnIndex(ContractTable.NAME)));
            myUserManage.setNick(cursor.getString(cursor.getColumnIndex(ContractTable.NICK)));
            myUserManage.setImgUrl(cursor.getString(cursor.getColumnIndex(ContractTable.IMGURL)));
            list.add(myUserManage);
        }
        cursor.close();
        mdb.close();
        return list;
    }

    public MyUserManage getContractByHxid(String hxid) {
        mdb = mHelper.getReadableDatabase();
        String sql = "select * from " + UserAccountTable.TABLE_NAME + " where " + UserAccountTable.HXID + "=?";
        Cursor cursor = mdb.rawQuery(sql, new String[]{hxid});
        MyUserManage manage = null;
        while (cursor.moveToNext()) {
            manage = new MyUserManage();
            manage.setHx_id(cursor.getString(cursor.getColumnIndex(UserAccountTable.HXID)));
            manage.setName(cursor.getString(cursor.getColumnIndex(UserAccountTable.NAME)));
            manage.setNick(cursor.getString(cursor.getColumnIndex(UserAccountTable.NICK)));
            manage.setImgUrl(cursor.getString(cursor.getColumnIndex(UserAccountTable.IMGURL)));
        }
        cursor.close();
        mdb.close();
        return manage;
    }

    public List<MyUserManage> getContractByHxid(List<String> hxid) {
        List<MyUserManage> list = new ArrayList<>();
        for (String id : hxid) {
            MyUserManage manage = getContractByHxid(id);
            list.add(manage);
        }
        return list;
    }
}
