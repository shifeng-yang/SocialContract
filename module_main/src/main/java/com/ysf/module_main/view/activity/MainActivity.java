package com.ysf.module_main.view.activity;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ysf.module_main.R;
import com.ysf.module_main.R2;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R2.id.vp_main)
    ViewPager vpMain;
    @BindView(R2.id.tv_msg)
    TextView tvMsg;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void iniEventData() {
        Log.d("MainActivity", "tvMsg:" + tvMsg);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (vpMain == null) {
            Log.d("MainActivity", "空的");
        } else {
            Log.d("MainActivity", "vpMain:" + vpMain);
        }
        vpMain.setAdapter(new MyAdapter(fragmentManager));
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }
}
