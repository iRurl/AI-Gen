package com.ai.ai_gen.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.ai.ai_gen.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ai.ai_gen.utils.NoSwipeViewPager;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ai.ai_gen.fragment.HomeFragment;
import com.ai.ai_gen.fragment.CommunityFragment;
import com.ai.ai_gen.fragment.UtilsFragment;
import com.ai.ai_gen.fragment.UserFragment;

public class MainActivity extends AppCompatActivity {
    private NoSwipeViewPager viewPager;  // 使用自定义的 NoSwipeViewPager
    private TabLayout tablayout;
    private List<Fragment> fragmentList;
    private String[] titles={"首页","工具","社区","我的"};
    private int[] unSele = {R.mipmap.main_home, R.mipmap.main_cart, R.mipmap.main_community, R.mipmap.main_user};
    private int[] onSele = {R.mipmap.main_home_press, R.mipmap.main_cart_press, R.mipmap.main_community_press, R.mipmap.main_user_press};

    @Override
    protected void onCreate(Bundle InstanceState) {
        super.onCreate(InstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        viewPager = (NoSwipeViewPager) findViewById(R.id.viewPager);  // 注意这里的类型
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        getPermission();
        initData();
    }

    public void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new UtilsFragment());
        fragmentList.add(new CommunityFragment());
        fragmentList.add(new UserFragment());

        MainTabAdapter mainTabAdapter = new MainTabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mainTabAdapter);
        tablayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tablayout.getTabCount(); i++) {
            TabLayout.Tab tab = tablayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(mainTabAdapter.getView(i));
            }
        }

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view != null) {
                    ImageView img = view.findViewById(R.id.img);
                    TextView tv = view.findViewById(R.id.tv);
                    String title = tv.getText().toString();
                    if (title.equals("首页")) {
                        img.setImageResource(onSele[0]);
                    } else if (title.equals("工具")) {
                        img.setImageResource(onSele[1]);
                    } else if (title.equals("社区")) {
                        img.setImageResource(onSele[2]);
                    } else if (title.equals("我的")) {
                        img.setImageResource(onSele[3]);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view != null) {
                    ImageView img = view.findViewById(R.id.img);
                    TextView tv = view.findViewById(R.id.tv);
                    String title = tv.getText().toString();
                    if (title.equals("首页")) {
                        img.setImageResource(unSele[0]);
                    } else if (title.equals("工具")) {
                        img.setImageResource(unSele[1]);
                    } else if (title.equals("社区")) {
                        img.setImageResource(unSele[2]);
                    } else if (title.equals("我的")) {
                        img.setImageResource(unSele[3]);
                    }
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 可以留空
            }
        });
    }

    public class MainTabAdapter extends FragmentPagerAdapter {
        public MainTabAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        public View getView(int position) {
            View view = View.inflate(MainActivity.this, R.layout.main_tab_item, null);
            ImageView img = view.findViewById(R.id.img);
            TextView tv = view.findViewById(R.id.tv);
            if (tablayout.getTabAt(position).isSelected()) {
                img.setImageResource(onSele[position]);
            } else {
                img.setImageResource(unSele[position]);
            }
            tv.setText(titles[position]);
            tv.setTextColor(tablayout.getTabTextColors());
            return view;
        }
    }

    private void getPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.POST_NOTIFICATIONS,
                    Manifest.permission.INTERNET,
            }, 1);
        }
    }
}
