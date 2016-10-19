package com.caoye.fragmentdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener{

    //UI Object
    private TextView txt_topbar;

    private TextView txt_contact;
    private TextView txt_message;
    private TextView txt_discover;
    private TextView txt_setting;

    private ViewPager mPager;

    //Fragment
    private MyFragmentPagerAdapter mAdapter;
    private List<Fragment> fgList = new ArrayList<>();
    private Fragment fg1;
    private Fragment fg2;
    private Fragment fg3;
    private Fragment fg4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        initState();
        initPager();
        txt_message.performClick();
    }

    //UI组件初始化与事件绑定
    private void bindViews() {
        txt_topbar = (TextView) findViewById(R.id.txt_topbar);

        txt_contact = (TextView) findViewById(R.id.txt_contact);
        txt_message = (TextView) findViewById(R.id.txt_message);
        txt_discover = (TextView) findViewById(R.id.txt_discover);
        txt_setting = (TextView) findViewById(R.id.txt_setting);

        txt_contact.setOnClickListener(this);
        txt_message.setOnClickListener(this);
        txt_discover.setOnClickListener(this);
        txt_setting.setOnClickListener(this);
    }

    private void initPager() {
        fg1 = FragmentOne.newInstance("args for FragmentOne");
        fg2 = FragmentTwo.newInstance("args for FragmentTwo");
        fg3 = FragmentThree.newInstance("args for FragmentThree");
        fg4 = FragmentFour.newInstance("args for FragmentFour");
        fgList.add(fg1);
        fgList.add(fg2);
        fgList.add(fg3);
        fgList.add(fg4);

        mPager = (ViewPager) findViewById(R.id.vPager);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fgList);
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(this);
    }

    //重置所有文本的选中状态
    private void initState(){
        txt_contact.setSelected(false);
        txt_message.setSelected(false);
        txt_discover.setSelected(false);
        txt_setting.setSelected(false);
    }

    private void showFragment(int num) {
        initState();

        switch (num) {
            case R.id.txt_message:
            case 0:
                txt_message.setSelected(true);
                mPager.setCurrentItem(0);
                txt_topbar.setText(txt_message.getText());
                break;
            case R.id.txt_contact:
            case 1:
                txt_contact.setSelected(true);
                mPager.setCurrentItem(1);
                txt_topbar.setText(txt_contact.getText());
                break;
            case R.id.txt_discover:
            case 2:
                txt_discover.setSelected(true);
                mPager.setCurrentItem(2);
                txt_topbar.setText(txt_discover.getText());
                break;
            case R.id.txt_setting:
            case 3:
                txt_setting.setSelected(true);
                mPager.setCurrentItem(3);
                txt_topbar.setText(txt_setting.getText());
                break;
        }
    }

    @Override
    public void onClick(View view) {
        showFragment(view.getId());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {}

    @Override
    public void onPageScrollStateChanged(int state) {
        if(state == 2) {
            int i = mPager.getCurrentItem();
            showFragment(i);
        }
    }
}
