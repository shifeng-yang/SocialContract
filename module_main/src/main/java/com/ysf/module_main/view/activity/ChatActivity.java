package com.ysf.module_main.view.activity;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.EaseChatInputMenu;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.ysf.module_main.R;
import com.ysf.module_main.R2;

import butterknife.BindView;

public class ChatActivity extends BaseActivity {
    @BindView(R2.id.message_list)
    EaseChatMessageList messageList;
    @BindView(R2.id.input_menu)
    EaseChatInputMenu inputMenu;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_chat;
    }

    @Override
    protected void iniEventData() {
        String username = getIntent().getStringExtra("username");
        initToolBar(username);
        messageList.init(username,EaseChatMessageList.AUTOFILL_TYPE_DATE,null);
        messageList.setItemClickListener(new EaseChatMessageList.MessageListItemClickListener() {
            @Override
            public boolean onBubbleClick(EMMessage message) {
                //气泡框点击事件，EaseUI有默认实现这个事件，如果需要覆盖，return值要返回true
                return false;
            }

            @Override
            public boolean onResendClick(EMMessage message) {
                //重发消息按钮点击事件
                return false;
            }

            @Override
            public void onBubbleLongClick(EMMessage message) {
                //气泡框长按事件
            }

            @Override
            public void onUserAvatarClick(String username) {
                //头像点击事件
            }

            @Override
            public void onUserAvatarLongClick(String username) {
                //头像长按
            }

            @Override
            public void onMessageInProgress(EMMessage message) {

            }
        });

        /*//获取下拉刷新控件
            swipeRefreshLayout = messageList.getSwipeRefreshLayout();
            //刷新消息列表
            messageList.refresh();
            messageList.refreshSeekTo(position);
            messageList.refreshSelectLast();*/
    }
}
