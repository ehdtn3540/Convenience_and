package com.example.convenience;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

public class MainFragment extends FragmentActivity {
    TabLayout tabs;

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);

        fragment1 = new Fragment1();
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment1).commit();

        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("홈"));
        tabs.addTab(tabs.newTab().setText("조합"));
        tabs.addTab(tabs.newTab().setText("신상"));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
//                Fragment selected = null;
                if(position == 0){
                    if(fragment1 == null) {
                        fragment1 = new Fragment1();
                        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment1).commit();
                    }
                    if(fragment1 != null) getSupportFragmentManager().beginTransaction().show(fragment1).commit();
                    if(fragment2 != null) getSupportFragmentManager().beginTransaction().hide(fragment2).commit();
                    if(fragment3 != null) getSupportFragmentManager().beginTransaction().hide(fragment3).commit();
//                    selected = fragment1;
                }else if(position == 1){
                    if(fragment2 == null) {
                        fragment2 = new Fragment2();
                        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment2).commit();
                    }
                    if(fragment1 != null) getSupportFragmentManager().beginTransaction().hide(fragment1).commit();
                    if(fragment2 != null) getSupportFragmentManager().beginTransaction().show(fragment2).commit();
                    if(fragment3 != null) getSupportFragmentManager().beginTransaction().hide(fragment3).commit();
//                    selected = fragment2;
                }else if(position == 2){
                    if(fragment3 == null) {
                        fragment3 = new Fragment3();
                        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment3).commit();
                    }
                    if(fragment1 != null) getSupportFragmentManager().beginTransaction().hide(fragment1).commit();
                    if(fragment2 != null) getSupportFragmentManager().beginTransaction().hide(fragment2).commit();
                    if(fragment3 != null) getSupportFragmentManager().beginTransaction().show(fragment3).commit();
//                    selected = fragment3;
                }

//                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}