package com.ysf.module_main.view.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.ysf.module_main.model.adapter.ContractAdapter;
import com.ysf.module_main.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ContractAddedReceiver extends BroadcastReceiver {
    private ContractAdapter mAdapter;
    private CompositeDisposable mDisposable;

    public ContractAddedReceiver(ContractAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ContractAddedReceiver", "联系人增加了Receiver");
        //刷新适配器显示刚添加的联系人
        List<String> contacts = new ArrayList<>();
        mDisposable = new CompositeDisposable(
                Observable.create((ObservableOnSubscribe<String>) emitter -> {
                    try {
                        contacts.addAll(EMClient.getInstance().contactManager().getAllContactsFromServer());
                        Log.d("ContractAddedReceiver", "contacts.size():" + contacts.size());
                        emitter.onNext("成功");
                    } catch (HyphenateException e) {
                        emitter.onNext(e.getDescription());
                        e.printStackTrace();
                    }
                })
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(s -> {
                            //数据异常,取消订阅
                            if (s.equals("-1") && mDisposable != null) {
                                mDisposable.dispose();
                                return;
                            }
                            if (s.equals("成功")) {
                                mAdapter.replaceData(contacts);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                ToastUtils.show(context,s);
                            }
                        })
        );

    }
}
