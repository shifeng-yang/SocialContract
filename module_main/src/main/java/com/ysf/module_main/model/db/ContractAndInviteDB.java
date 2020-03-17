package com.ysf.module_main.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ysf.module_main.model.dao.ContractTable;

public class ContractAndInviteDB extends SQLiteOpenHelper {
    public ContractAndInviteDB(Context context,String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //联系人
        sqLiteDatabase.execSQL(ContractTable.CREATE_TABLE);
        //邀请
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
