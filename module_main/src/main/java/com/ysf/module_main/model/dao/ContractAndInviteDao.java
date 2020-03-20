package com.ysf.module_main.model.dao;

import android.content.ContentValues;
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

    /**
     * 获取所有联系人
     * @return 联系人集合
     */
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

    /**
     * 通过环信id获取单个或多个联系人信息
     * @param hxid 联系人的环信id
     * @return 联系人集合
     */
    public List<MyUserManage> getContractByHxid(String... hxid) {
        mdb = mHelper.getReadableDatabase();
        String sql = "select * from " + UserAccountTable.TABLE_NAME + " where " + UserAccountTable.HXID + "=?";
        List<MyUserManage> list = new ArrayList<>();
        MyUserManage manage;
        for (String id : hxid) {
            Cursor cursor = mdb.rawQuery(sql, new String[] {id});
            while (cursor.moveToNext()) {
                manage = new MyUserManage();
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

    public void addContract(MyUserManage user,boolean isMyContract) {
        mdb =  mHelper.getReadableDatabase();
        ContentValues value = new ContentValues();
        value.put(ContractTable.HXID,user.getHx_id());
        value.put(ContractTable.NAME,user.getName());
        value.put(ContractTable.NICK,user.getNick());
        value.put(ContractTable.IMGURL,user.getImgUrl());
        value.put(ContractTable.IS_MY_CONTRACT,isMyContract ? 1 : 0);
        mdb.replace(ContractTable.TABLE_NAME,null,value);
    }

    public void addContract(List<MyUserManage> users,boolean isMyContract) {
        mdb =  mHelper.getReadableDatabase();
        ContentValues value;
        for (MyUserManage user : users) {
            value = new ContentValues();
            value.put(ContractTable.HXID,user.getHx_id());
            value.put(ContractTable.NAME,user.getName());
            value.put(ContractTable.NICK,user.getNick());
            value.put(ContractTable.IMGURL,user.getImgUrl());
            value.put(ContractTable.IS_MY_CONTRACT,isMyContract ? 1 : 0);
            mdb.replace(ContractTable.TABLE_NAME,null,value);
        }
    }

}
