package com.example.musicapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.musicapplication.Adapter.ViewPagerAdapter;
import com.example.musicapplication.Fragment.LocalMusicFragment;
import com.example.musicapplication.Fragment.OnlineMusicFragment;
import com.example.musicapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragmentList;
    private LocalMusicFragment localMusicFragment;
    private OnlineMusicFragment onlineMusicFragment;
    private RadioGroup radioGroup;
    private RadioButton localButton,onlineButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        ImageView pl = findViewById(R.id.music_bottom_playlist);
        ImageView pic= findViewById(R.id.music_bottom_pic);
        pl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PlaylistActivity.class);
                MainActivity.this.startActivity(intent);

            }

        });
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent();
                intent1.setClass(MainActivity.this, PlayActivity.class);
                MainActivity.this.startActivity(intent1);
            }
        });
    }

    private void initView() {
        ContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.main_viewpager);
        radioGroup = findViewById(R.id.main_radioGroup);
        localButton = findViewById(R.id.local_button);
        onlineButton = findViewById(R.id.online_button);

        localMusicFragment = new LocalMusicFragment();
        onlineMusicFragment = new OnlineMusicFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(localMusicFragment);
        fragmentList.add(onlineMusicFragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPagerAdapter = new ViewPagerAdapter(fragmentManager,fragmentList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);
        radioGroup.check(R.id.local_button);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        radioGroup.check(R.id.local_button);
                        break;
                    case 1:
                        radioGroup.check(R.id.online_button);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.local_button:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.online_button:
                        viewPager.setCurrentItem(1);
                        break;
                }
            }
        });


    }
    private void ContentView(int activity_main) {
    }
}
