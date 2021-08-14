package com.example.digitallibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.digitallibrary.Adapter.GridViewAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class GenresCollections extends AppCompatActivity {

    GridView gridView;
    ImageView icon;
    TextView genres_title;
    DatabaseAccess DB;
    private Book book;
    String genres;
    private User currentUser;
    private int lastfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres_collections);

        //get current user, last fragment, genres from lat intent
        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;
        genres= (String) getIntent().getSerializableExtra("genres");

        gridView=findViewById(R.id.DetectiveGridView);
        genres_title=findViewById(R.id.genres_title);
        icon=findViewById(R.id.genres_icon);

        changeTitleSec();

        DB= DatabaseAccess.getInstance(this);
        DB.open();

        //get a list of books
        ArrayList<Book> bookList= DB.getGenresBooks(genres);

        //Initialize a custom GridView adapter
        GridViewAdapter gridViewAdapter= new GridViewAdapter(this, bookList);
        gridView.setAdapter(gridViewAdapter);

        //when gridview item click
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i;
                book=gridViewAdapter.getItem(position);
                String bookTitle= book.getTitle();
                i= new Intent(GenresCollections.this, BookDetails.class);
                i.putExtra("book_title", bookTitle);
                i.putExtra("user", currentUser);
                startActivity(i);
            }
        });

        DB.close();


        //bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


    }

    private void changeTitleSec() {
        switch (genres){
            case ("Detective"):
                genres_title.setText("Detective");
                icon.setImageResource(R.drawable.spy);
                break;
            case ("Romance"):
                genres_title.setText("Romance");
                icon.setImageResource(R.drawable.lovebird);
                break;
            case ("SciFic"):
                genres_title.setText("Science Fiction");
                icon.setImageResource(R.drawable.ufo);
                break;
            case ("Horror"):
                genres_title.setText("Horror");
                icon.setImageResource(R.drawable.ghost);
                break;
            case ("Fantasy"):
                genres_title.setText("Fantasy");
                icon.setImageResource(R.drawable.magic);
                break;
            case ("NonFic"):
                genres_title.setText("Fantasy");
                icon.setImageResource(R.drawable.openbook);
                break;
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener= new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment= null;

            switch (item.getItemId()){
                case R.id.nav_home:
                    selectedFragment= new home();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//pass the valu
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
                    break;
                case R.id.nav_favorite:
                    selectedFragment= new favorite();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//pass the valu
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
                    bundle.putSerializable("user",currentUser);//pass the valu
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_profile;
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

            return false;
        }
    };

}