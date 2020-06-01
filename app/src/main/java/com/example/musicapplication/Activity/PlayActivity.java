package com.example.musicapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.musicapplication.Adapter.ViewPagerAdapter;
import com.example.musicapplication.Fragment.AlbumFragment;
import com.example.musicapplication.Fragment.LyricFragment;
import com.example.musicapplication.R;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragmentList;
    private AlbumFragment pic_fragment;
    private LyricFragment lyricFragment;
    private RadioGroup radio_Group;
    private RadioButton radio_dot1,radio_dot2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        ImageView dn = findViewById(R.id.play_top_down);
        dn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(PlayActivity.this, MainActivity.class);
//                PlayActivity.this.startActivity(intent);
                finish();
            }
        });
        initView();
    }
    public void initView(){
         ContentView(R.layout.activity_play);
        viewPager = findViewById(R.id.play_viewpager);
        radio_Group = findViewById(R.id.radio_group);
        radio_dot1 = findViewById(R.id.radio_dot1);
        radio_dot2 = findViewById(R.id.radio_dot2);

        lyricFragment = new LyricFragment();
        pic_fragment = new AlbumFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(pic_fragment);
        fragmentList.add(lyricFragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPagerAdapter = new ViewPagerAdapter(fragmentManager,fragmentList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);
        radio_Group.check(R.id.radio_dot1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        radio_Group.check(R.id.radio_dot1);
                        break;
                    case 1:
                        radio_Group.check(R.id.radio_dot2);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        radio_Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_dot1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.radio_dot2:
                        viewPager.setCurrentItem(1);
                        break;
                }
            }
        });

    }
    private void ContentView(int activity_play){

    }


}
