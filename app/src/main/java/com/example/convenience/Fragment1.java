package com.example.convenience;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class Fragment1 extends Fragment {
    ViewPager pager;
    PagerAdapter adapter;

//    private ViewPagerFragment1 fragment1;
//    private ViewPagerFragment2 fragment2;
//    private ViewPagerFragment3 fragment3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_fragment1, container, false);

        pager = rootView.findViewById(R.id.pager);

        adapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    //정지 후 1~2후 호출

                } else if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    //이동
                }

            }
        });
        return rootView;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        fragment1=new ViewPagerFragment1();
//        fragment2=new ViewPagerFragment2();
//        fragment3=new ViewPagerFragment3();
//    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Log.d("abb", "pos: " + position);
            if (position == 0) {
                return new ViewPagerFragment1();
            } else if (position == 1) {
                return new ViewPagerFragment2();
            } else if (position == 2) {
                return new ViewPagerFragment3();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}