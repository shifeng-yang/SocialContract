package com.ysf.module_main.model.dao;

public interface UserAccountTable {
    String TABLE_NAME = "user_account";
    String NAME = "name";
    String HXID = "hxid";
    String NICK = "nick";
    String IMGURL = "imgurl";

    String CREATE_TABLE = "create table " +
            TABLE_NAME + " (" +
            HXID + " text primary key," +
            NAME + " text," +
            NICK + " text," +
            IMGURL + "text);";
}
