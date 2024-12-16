package com.my.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.my.myapplication.fragment.Home_item1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//主界面
public class MainActivity extends AppCompatActivity {
    //定义变量
    private final ArrayList<Fragment> tabFragmentList = new ArrayList<>();

    private String[] mTitles_3;
    private TabLayout mTablayout;
    private ViewPager mViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        设置标题
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
//        初始化控件
        initView();


    }

    //查找控件 绑定点击事件
    private void initView() {
//        找控件
        mViewpager = findViewById(R.id.viewpager);
        mTablayout = findViewById(R.id.tablayout);
        mTitles_3 = getResources().getStringArray(R.array.data);
//        追加全部类型 页面
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(mTitles_3));
        list.add(0, "all");
        mTitles_3 = list.toArray(new String[list.size()]);

//        获取分类数据
        if (mTitles_3.length == 0) {
            Toast.makeText(this, "Resource file classification data cannot be empty", Toast.LENGTH_SHORT).show();
        }
//        根据分类的数据 初始化页面
        for (int i = 0; i < mTitles_3.length; i++) {
            Home_item1 home_item1 = Home_item1.newInstance(mTitles_3[i], "1");
            tabFragmentList.add(home_item1);
        }
//        绑定 viewpager fragment Tablelayout
        mViewpager.setOffscreenPageLimit(tabFragmentList.size());
        mViewpager.setAdapter(new MPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabFragmentList));
        mTablayout.setupWithViewPager(mViewpager);

    }

    //创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //将菜单资源加载到当前的菜单资源

        getMenuInflater().inflate(R.menu.main1, menu);

        return true;
    }

    //菜单栏 点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        搜索按钮
        if (item.getItemId() == R.id.id1) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);

        }


        return true;
    }

    //    viewpager适配器
    class MPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList;

        public MPagerAdapter(FragmentManager fm, int behavior, List<Fragment> fragmentList) {
            super(fm, behavior);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        //返回tablayout的标题文字;
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles_3[position];
        }
    }
}