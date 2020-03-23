package com.ysf.module_main.model.dao;

public interface InviteTable {
    String TABLE_NAME = "invite";
    String USER_HXID = "user_hxid";
    String USER_NAME = "user_name";
    String GROUP_HXID = "group_hxid";
    String GROUP_NAME = "group_name";
    String REASON = "reason";
    String STATUS = "status";

    String CREATE_TABLE = "create table " +
            TABLE_NAME + " (" +
            USER_HXID + " text primary key," +
            USER_NAME + " text," +
            GROUP_HXID + " text," +
            GROUP_NAME + " text," +
            REASON + " text," +
            STATUS + " integer);";

}
