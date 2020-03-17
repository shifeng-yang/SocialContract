package com.ysf.module_main.model.dao;

public interface ContractTable {
    String TABLE_NAME = "contract";
    String NAME = "name";
    String HXID = "hxid";
    String NICK = "nick";
    String IMGURL = "imgurl";
    String IS_MY_CONTRACT = "is_my_contract";

    String CREATE_TABLE = "create table " +
            TABLE_NAME + " (" +
            HXID + " text primary key," +
            NAME + " text," +
            NICK + " text," +
            IMGURL + " text," +
            IS_MY_CONTRACT + " integer);";
}
