package com.example.digitallibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

public class Introductory extends AppCompatActivity {

    LottieAnimationView book;
    TextView company_name;

    private static final int NUM_PAGES= 2;
    private ViewPager2 viewPager;
    private ScreenSlidePageAdapter pagerAdapter;
    private List<Fragment> fragments;//fragment list
    Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        company_name=findViewById(R.id.logoname);
        book= findViewById(R.id.book_anime);
        book.enableMergePathsForKitKatAndAbove(true);

        btn_start=findViewById(R.id.startbtn);


        //on click listener for get started button
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Introductory.this,Login.class);
                startActivity(intent);
                finish();
            }
        });

        //init array lists and add fragment
        fragments=new ArrayList<>();
        fragments.add(new OnBoarding1());
        fragments.add(new OnBoarding2());

        viewPager= findViewById(R.id.pager);
        pagerAdapter= new ScreenSlidePageAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.i("test.......", "onPageSelected: "+position);

                //get started button show when comes to last on boarding fragment
                btn_start.setVisibility(position==fragments.size()-1? View.VISIBLE:View.GONE);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        //set animation
        company_name.animate().translationY(3000).setDuration(1000).setStartDelay(4000);
        book.animate().translationY(2000).setDuration(1000).setStartDelay(4000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }
            @Override
            public void onAnimationEnd(Animator animation) {
                //view on boarding page when animation end
                viewPager.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationCancel(Animator animation) {

            }
            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    // screen slide page adapter
    private class ScreenSlidePageAdapter extends FragmentStateAdapter {

        public ScreenSlidePageAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return fragments.size();
        }
    }
}