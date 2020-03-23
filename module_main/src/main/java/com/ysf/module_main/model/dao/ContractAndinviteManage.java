package com.ysf.module_main.model.dao;

import android.content.Context;

import com.ysf.module_main.model.db.ContractAndInviteDB;

public class ContractAndinviteManage {
    private final ContractDao mContractDao;
    private final InviteDao mInviteDao;
    private final ContractAndInviteDB mHelp;

    public ContractAndinviteManage(Context context, String dBname) {
        mHelp = new ContractAndInviteDB(context, dBname);
        mContractDao = new ContractDao(mHelp);
        mInviteDao = new InviteDao(mHelp);
    }

    public ContractDao getContractDao() {
        return mContractDao;
    }

    public InviteDao getInviteDao() {
        return mInviteDao;
    }

    public void close() {
        mHelp.close();
    }

}
