package com.ysf.module_main.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ysf.module_main.model.bean.GroupBean;
import com.ysf.module_main.model.bean.InviteBean;
import com.ysf.module_main.model.bean.UserBean;
import com.ysf.module_main.model.db.ContractAndInviteDB;
import java.util.ArrayList;
import java.util.List;

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

    public List<InviteBean> getInvite() {
        mdb = mHelper.getReadableDatabase();
        String sql = "select * from " + InviteTable.TABLE_NAME;
        Cursor cursor = mdb.rawQuery(sql, null);
        InviteBean inviteBean;
        UserBean userBean;
        GroupBean groupBean;
        List<InviteBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            inviteBean = new InviteBean();
            inviteBean.setReson(cursor.getString(cursor.getColumnIndex(InviteTable.REASON)));
            inviteBean.setStatus(int2Status(cursor.getInt(cursor.getColumnIndex(InviteTable.STATUS))));
            String group_id = cursor.getString(cursor.getColumnIndex(InviteTable.GROUP_HXID));
            if (group_id != null) {
                groupBean = new GroupBean();
                groupBean.setGroup_id(cursor.getString(cursor.getColumnIndex(InviteTable.GROUP_HXID)));
                groupBean.setGroup_name(cursor.getString(cursor.getColumnIndex(InviteTable.GROUP_NAME)));
                groupBean.setInvit_user_id(cursor.getString(cursor.getColumnIndex(InviteTable.USER_HXID)));
                inviteBean.setGroupInfo(groupBean);
            } else {
                userBean = new UserBean();
                userBean.setHx_id(cursor.getString(cursor.getColumnIndex(InviteTable.USER_HXID)));
                userBean.setName(cursor.getString(cursor.getColumnIndex(InviteTable.USER_NAME)));
                inviteBean.setUserInfo(userBean);
            }
            list.add(inviteBean);
        }
        cursor.close();
        mdb.close();
        return list;
    }

    private InviteBean.InvitationStatus int2Status(int ordinal) {
        if (ordinal == InviteBean.InvitationStatus.INVITE_BY_ACCEPT.ordinal()) {
            return InviteBean.InvitationStatus.INVITE_BY_ACCEPT;
        } else if (ordinal == InviteBean.InvitationStatus.INVITE_BY_DECLINED.ordinal()) {
            return InviteBean.InvitationStatus.INVITE_BY_DECLINED;
        } else if (ordinal == InviteBean.InvitationStatus.NEW_INVITE.ordinal()) {
            return InviteBean.InvitationStatus.NEW_INVITE;
        } else if (ordinal == InviteBean.InvitationStatus.INVITE_ACCEPT.ordinal()) {
            return InviteBean.InvitationStatus.INVITE_ACCEPT;
        } else if (ordinal == InviteBean.InvitationStatus.INVITE_DECLINED.ordinal()) {
            return InviteBean.InvitationStatus.INVITE_DECLINED;
        }
        return InviteBean.InvitationStatus.DEFAULT;
    }

    public void deleteInvite(String hxid) {
        mdb = mHelper.getReadableDatabase();
        mdb.delete(InviteTable.TABLE_NAME,InviteTable.USER_HXID + "=?",new String[] {hxid});
        mdb.close();
    }

    public void updateInviteStatus(String hxid,InviteBean.InvitationStatus status) {
        mdb = mHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(InviteTable.STATUS,status.ordinal());
        mdb.update(InviteTable.TABLE_NAME,values,InviteTable.USER_HXID + "=?",new String[] {hxid});
        mdb.close();
    }

}
