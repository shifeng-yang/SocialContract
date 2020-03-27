package com.ysf.module_main.view.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
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
                Observable.create((ObservableOnSubscribe<String>) emitter -> EMClient.getInstance().contactManager().aysncGetAllContactsFromServer(new EMValueCallBack<List<String>>() {
                    @Override
                    public void onSuccess(List<String> strings) {
                        contacts.addAll(strings);
                        Log.d("ContractAddedReceiver", "contacts.size():" + contacts.size());
                        emitter.onNext("成功");
                    }

                    @Override
                    public void onError(int i, String s) {
                        emitter.onNext(i + ": " + s);
                    }
                }))
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
