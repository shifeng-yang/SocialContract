package com.ysf.module_main.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ysf.module_main.model.bean.InviteBean;
import com.ysf.module_main.model.db.ContractAndInviteDB;

public class InviteDao {
    private ContractAndInviteDB mHelper;
    private SQLiteDatabase mdb;

    public InviteDao(ContractAndInviteDB helper) {
        mHelper = helper;
    }

    public void deleteTable() {
        mdb = mHelper.getReadableDatabase();
        mdb.execSQL("DROP TABLE " + InviteTable.TABLE_NAME);
    }

    public void addInvite(InviteBean inviteBean) {
        mdb = mHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(InviteTable.REASON,inviteBean.getReson());
        values.put(InviteTable.STATUS,inviteBean.getStatus().ordinal());
        if (inviteBean.getUserInfo() != null) {
            values.put(InviteTable.USER_HXID,inviteBean.getUserInfo().getHx_id());
            values.put(InviteTable.USER_NAME,inviteBean.getUserInfo().getName());
        } else {
            values.put(InviteTable.GROUP_HXID,inviteBean.getGroupInfo().getGroup_id());
            values.put(InviteTable.GROUP_NAME,inviteBean.getGroupInfo().getGroup_name());
            values.put(InviteTable.USER_HXID,inviteBean.getGroupInfo().getInvit_user_id());
        }
        mdb.replace(InviteTable.TABLE_NAME,null,values);
        mdb.close();
    }

    public InviteBean getInvite() {
        mdb = mHelper.getReadableDatabase();
        String aql = "select * from " + InviteTable.TABLE_NAME;
        Cursor cursor = mdb.rawQuery(aql, null);
        InviteBean inviteBean;
        while (cursor.moveToNext()) {
            inviteBean = new InviteBean();
            inviteBean.setReson(cursor.getString(cursor.getColumnIndex(InviteTable.REASON)));
            inviteBean.setStatus(int2Status(cursor.getInt(cursor.getColumnIndex(InviteTable.STATUS))));
        }
        cursor.close();
        mdb.close();
        return null;
    }

    private InviteBean.InvitationStatus int2Status(int ordinal) {
        if (ordinal == InviteBean.InvitationStatus.INVITE_ACCEPT.ordinal()) {
            return InviteBean.InvitationStatus.INVITE_ACCEPT;
        } else if (ordinal == InviteBean.InvitationStatus.INVITE_ACCEPT_BY_PEER.ordinal()) {
            return InviteBean.InvitationStatus.INVITE_ACCEPT_BY_PEER;
        } else if (ordinal == InviteBean.InvitationStatus.NEW_INVITE.ordinal()) {
            return InviteBean.InvitationStatus.NEW_INVITE;
        }
        return InviteBean.InvitationStatus.DEFAULT;
    }

}
