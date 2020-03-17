package com.ysf.module_main.view.activity;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hyphenate.chat.EMClient;
import com.ysf.module_main.R;
import com.ysf.module_main.R2;
import com.ysf.module_main.view.fragment.ContractFragment;
import com.ysf.module_main.view.fragment.MessageFragment;
import com.ysf.module_main.view.fragment.SettingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;

public class MainActivity extends BaseActivity {
    @BindView(R2.id.vp_main)
    ViewPager vpMain;
    @BindView(R2.id.pn_tab)
    PageNavigationView pnTab;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void iniEventData() {
        getSwipeBackLayout().setEnableGesture(false);
        initHxMsg();
        iniViewPage();
        initNP();
    }

    /**
     * 保证进入主页面后本地会话和群组都 load 完毕
     */
    private void initHxMsg() {
        EMClient.getInstance().chatManager().loadAllConversations();
        EMClient.getInstance().groupManager().loadAllGroups();
    }

    private void iniViewPage() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MessageFragment());
        fragments.add(new ContractFragment());
        fragments.add(new SettingFragment());
        vpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    private void initNP() {
        NavigationController navigationController = pnTab.custom()
                .addItem(newItem(R.drawable.message_normal,R.drawable.message_active,"消息"))
                .addItem(newItem(R.drawable.contract_normal,R.drawable.contract_active,"联系人"))
                .addItem(newItem(R.drawable.setting_normal,R.drawable.setting_active,"设置"))
                .build();
        navigationController.setupWithViewPager(vpMain);
    }

    private BaseTabItem newItem(int drawable, int checkedDrawable, String text){
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable,checkedDrawable,text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(getResources().getColor(R.color.colorPrimary));
        return normalItemView;
    }

}
