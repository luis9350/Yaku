package com.project.yaku.presentation.view.Main;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.project.yaku.R;
import com.project.yaku.presentation.model.entities.ViewPagerAdapter;
import com.project.yaku.presentation.model.ui.MasterView;
import com.project.yaku.presentation.view.Consume.ConsumeFragment;
import com.project.yaku.presentation.view.Dashboard.DashboardFragment;

public class MainActivity extends AppCompatActivity implements MasterView{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
    }


    @Override
    public void initializeComponents() {
        mContext=MainActivity.this;
        tabLayout = (TabLayout) findViewById(R.id.principal_tabLayout);
        viewPager = (ViewPager) findViewById(R.id.principal_viewPager);
        renderTabView();
    }

    @Override
    public void showMessage(String mensaje) {
        Toast.makeText(mContext, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnActivity() {

    }

    @Override
    public void navigateToActivity(Intent i) {

    }

    public void renderTabView() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new DashboardFragment(MainActivity.this), "Hazlo");
        viewPagerAdapter.addFragments(new ConsumeFragment(MainActivity.this), "Mi Consumo");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.imgcoche);
        tabLayout.getTabAt(1).setIcon(R.drawable.imgducha);
    }
}
