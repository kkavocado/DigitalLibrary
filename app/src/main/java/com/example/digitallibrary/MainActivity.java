package com.example.digitallibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

public class MainActivity extends AppCompatActivity {

    private User currentUser;
    private int lastfragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get intent for current user
        currentUser=(User) getIntent().getSerializableExtra("user");
        lastfragment=0;

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED);
        bottomNav.postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomNav.setSelectedItemId(bottomNav.getMenu().getItem(lastfragment).getItemId());
            }
        },100);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener= new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment= null;

            switch (item.getItemId()){
                case R.id.nav_home:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//pass the value
                    selectedFragment= new home();
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
                    break;
                case R.id.nav_favorite:
                    selectedFragment= new favorite();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//pass the value
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_favorite;
                    break;
                case R.id.nav_search:
                    selectedFragment= new search();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//pass the value
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_search;
                    break;
                case R.id.nav_profile:
                    selectedFragment= new profile();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//pass the value
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_profile;
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

            return false;
        }
    };
}