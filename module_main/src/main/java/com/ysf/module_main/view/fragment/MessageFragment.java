package com.ysf.module_main.view.fragment;

import android.content.Intent;
import android.widget.FrameLayout;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.ysf.module_main.R;
import com.ysf.module_main.R2;
import com.ysf.module_main.view.activity.ChatActivity;

import java.util.List;

import butterknife.BindView;

public class MessageFragment extends BaseFragment {
    @BindView(R2.id.fl_container)
    FrameLayout flContainer;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initEventData() {
        initToolBar("消息");
        EaseConversationListFragment conversationListFragment = new EaseConversationListFragment();
        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction().add(R.id.fl_container,conversationListFragment).commit();
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                EaseUI.getInstance().getNotifier().notify(list);
                conversationListFragment.refresh();
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageRead(List<EMMessage> list) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> list) {

            }

            @Override
            public void onMessageRecalled(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {

            }
        });

        conversationListFragment.setConversationListItemClickListener(conversation -> {
            Intent intent = new Intent(mContext, ChatActivity.class);
            intent.putExtra(EaseConstant.EXTRA_USER_ID,conversation.conversationId());
            startActivity(intent);
        });
    }
}
