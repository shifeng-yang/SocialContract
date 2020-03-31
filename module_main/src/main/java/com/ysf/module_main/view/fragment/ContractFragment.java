package com.ysf.module_main.view.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.michael.easydialog.EasyDialog;
import com.ysf.module_main.R;
import com.ysf.module_main.R2;
import com.ysf.module_main.model.adapter.ContractAdapter;
import com.ysf.module_main.model.adapter.InviteAdapter;
import com.ysf.module_main.model.bean.RvInviteBean;
import com.ysf.module_main.utils.MyConstans;
import com.ysf.module_main.utils.MyPopupDialogUtils;
import com.ysf.module_main.utils.SPUtil;
import com.ysf.module_main.utils.ToastUtils;
import com.ysf.module_main.view.activity.ChatActivity;
import com.ysf.module_main.view.activity.InviteDetilActivity;
import com.ysf.module_main.view.broadcast.ContractAddedReceiver;
import com.ysf.module_main.view.broadcast.ContractInviteChangedReceiver;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ContractFragment extends BaseFragment {
    @BindView(R2.id.ib_add)
    ImageButton ibAdd;
    @BindView(R2.id.rv_invite)
    RecyclerView rvInvite;
    @BindView(R2.id.rv_contract)
    RecyclerView rvContract;
    private List<RvInviteBean> mRvInviteBeanList = new ArrayList<>();
    private CompositeDisposable mDisposable;
    private LocalBroadcastManager mBroadcastManager;
    private ContractInviteChangedReceiver mInviteChangedReceiver;
    private ContractAddedReceiver mContractAddedReceiver;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contract;
    }

    @Override
    protected void initEventData() {
        initToolBar("联系人");
        //用来注册广播接收者
        mBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        //显示右侧加号
        ibAdd.setVisibility(View.VISIBLE);
        rvInvite.setLayoutManager(new LinearLayoutManager(mContext));
        rvInvite.addItemDecoration(new DividerItemDecoration(mContext, RecyclerView.VERTICAL));
        mRvInviteBeanList.add(new RvInviteBean(R.drawable.icon_friend_deep, "好友/群邀请"));
        mRvInviteBeanList.add(new RvInviteBean(R.drawable.icon_group_deep, "群组"));
        InviteAdapter inviteAdapter = new InviteAdapter(R.layout.item_invite, mRvInviteBeanList);
        inviteAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (position) {
                case 0:
                    //查看了邀请信息则隐藏红点
                    SPUtil.setParam(mContext, SPUtil.IS_NEW_INVITE, false);
                    inviteAdapter.notifyDataSetChanged();
                    startActivity(new Intent(mContext, InviteDetilActivity.class));
                    break;
                case 1:
                    break;
                default:
            }

        });
        rvInvite.setAdapter(inviteAdapter);
        mInviteChangedReceiver = new ContractInviteChangedReceiver(inviteAdapter);
        mBroadcastManager.registerReceiver(mInviteChangedReceiver, new IntentFilter(MyConstans.INVITE_CHANGED));

        //联系人列表
        rvContract.setLayoutManager(new LinearLayoutManager(mContext));
        rvContract.addItemDecoration(new DividerItemDecoration(mContext,RecyclerView.VERTICAL));
        List<String> contacts = new ArrayList<>();
        ContractAdapter contractAdapter  = new ContractAdapter(R.layout.item_contract, contacts);
        rvContract.setAdapter(contractAdapter);
        mDisposable = new CompositeDisposable(
                Observable.create((ObservableOnSubscribe<String>) emitter -> EMClient.getInstance().contactManager().aysncGetAllContactsFromServer(new EMValueCallBack<List<String>>() {
                    @Override
                    public void onSuccess(List<String> strings) {
                        Log.d("ContractFragment", "第一次发送==>成功回调"+ Thread.currentThread().getName());
                        contacts.addAll(strings);
                        emitter.onNext("成功");
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.d("ContractFragment", "第一次发送==>失败回调" + Thread.currentThread().getName());
                        emitter.onNext(i + ": " + s);
                    }
                }))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(s -> {
                            Log.d("ContractFragment", "第一次接收" + Thread.currentThread().getName());
                            //数据异常,取消订阅
                            if (s.equals("-1") && mDisposable != null) {
                                mDisposable.dispose();
                                return;
                            }
                            if (s.equals("成功")) contractAdapter.notifyDataSetChanged();
                        })
                        .observeOn(Schedulers.newThread())
                        .flatMap((Function<String, ObservableSource<String>>) s -> (ObservableSource<String>) observer -> {
                            Log.d("ContractFragment", "第一次转换" + Thread.currentThread().getName());
                            if (s.equals("成功")) return;
                            String user = EMClient.getInstance().getCurrentUser();
                            EMClient.getInstance().login(user, user, new EMCallBack() {
                                @Override
                                public void onSuccess() {
                                    Log.d("ContractFragment", "第一次转换==>成功回调"+ Thread.currentThread().getName());
                                    observer.onNext("成功了");
                                }

                                @Override
                                public void onError(int i, String s) {
                                    Log.d("ContractFragment", "第一次转换==>失败回调"+ Thread.currentThread().getName());
                                    observer.onNext(i + " :" + s);
                                }

                                @Override
                                public void onProgress(int i, String s) {

                                }
                            });
                        })
                        .flatMap((Function<String, ObservableSource<String>>) s -> (ObservableSource<String>) observer -> {
                            Log.d("ContractFragment", "第二次转换" + Thread.currentThread().getName());
                            if (!s.equals("成功了")) return;
                            EMClient.getInstance().contactManager().aysncGetAllContactsFromServer(new EMValueCallBack<List<String>>() {
                                @Override
                                public void onSuccess(List<String> strings) {
                                    Log.d("ContractFragment", "第二次转换==>成功回调"+ Thread.currentThread().getName());
                                    contacts.addAll(strings);
                                    observer.onNext("成功了啊");
                                }

                                @Override
                                public void onError(int i, String s12) {
                                    Log.d("ContractFragment", "第二次转换==>失败回调" + Thread.currentThread().getName());
                                    observer.onNext(i + ": " + s12);
                                }
                            });
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(s -> {
                            Log.d("ContractFragment", "最终接收" + Thread.currentThread().getName());
                            if (s.equals("成功了啊")) contractAdapter.notifyDataSetChanged();
                            else ToastUtils.show(mContext,s);
                        })
        );
        contractAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(mContext, ChatActivity.class);
            intent.putExtra(EaseConstant.EXTRA_USER_ID,contacts.get(position));
            intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
            startActivity(intent);
        });
        contractAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            MyPopupDialogUtils.popupWindowDelete(mActivity,view,EasyDialog.GRAVITY_TOP);
            MyPopupDialogUtils.setOnDeleteClickListener(() -> {
                Log.d("ContractFragment", "position:" + position);
                EMClient.getInstance().contactManager().aysncDeleteContact(contacts.get(position), new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        mBroadcastManager.sendBroadcast(new Intent(MyConstans.CONTRACT_CHANGED));
                    }

                    @Override
                    public void onError(int i, String s) {
                        mActivity.runOnUiThread(() -> ToastUtils.show(mContext,i + ": " + s));
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            });
            return false;
        });
        mContractAddedReceiver = new ContractAddedReceiver(contractAdapter);
        mBroadcastManager.registerReceiver(mContractAddedReceiver,new IntentFilter(MyConstans.CONTRACT_CHANGED));
    }

    @OnClick(R2.id.ib_add)
    public void onClick() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        ibAdd.startAnimation(rotateAnimation);
        MyPopupDialogUtils.popupWindowContract(mActivity, ibAdd, EasyDialog.GRAVITY_BOTTOM);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBroadcastManager.unregisterReceiver(mContractAddedReceiver);
        mBroadcastManager.unregisterReceiver(mInviteChangedReceiver);
        mDisposable.dispose();
    }
}
