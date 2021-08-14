package com.example.digitallibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;

public class BookDetails extends AppCompatActivity {

    private String book_title;
    TextView title, author, pages, year, description, genres;
    ImageView bookcover;
    ImageButton read, share;
    ToggleButton favourite;
    DatabaseAccess DB;
    private User currentUser;
    private int lastfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        //get current user, last fragment, book_title from last intent
        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;
        book_title= (String) getIntent().getSerializableExtra("book_title");
        Log.d("Tag", String.valueOf(book_title));

        Log.d("User", currentUser.getName());
        Log.d("Userid", String.valueOf(currentUser.getUid()));

        //initialize views
        title=findViewById(R.id.book_name);
        author=findViewById(R.id.author);
        pages=findViewById(R.id.page);
        year=findViewById(R.id.year);
        description=findViewById(R.id.description);
        genres=findViewById(R.id.genres);
        bookcover=findViewById(R.id.book_image);
        read=findViewById(R.id.read_btn);
        favourite=findViewById(R.id.favorite_btn);
        share=findViewById(R.id.share);

        //connect to database
        DB= DatabaseAccess.getInstance(this);
        DB.open();

        Book book= DB.getBookDetails(book_title);

        //Set the TextView with the value from database
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        pages.setText(book.getPages());
        year.setText(book.getYear());
        description.setText(book.getDescription());
        genres.setText(book.getGenres());

        String imgname = book.getImageuri();
        int res = getResources().getIdentifier(imgname, "drawable", getPackageName());
        bookcover.setImageResource(res);

        String bookurl= book.getLink();

        //read button
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(BookDetails.this, ViewBook.class);
                intent.putExtra("bookurl", bookurl);
                startActivity(intent);
            }
        });

       //check the book exist in favorite table or not
       Boolean isExist= DB.checkFavoriteList(book_title, currentUser.getUid());

        if(isExist){
            //check the toggle button first if exist
            favourite.setChecked(true);
            //when favorite button is click
            favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean toggleState= favourite.isChecked();
                    Log.d("toggleState", String.valueOf(toggleState));
                    if(toggleState.equals(true)){
                        saveFavorite(book_title, currentUser.getUid());
                        Snackbar.make(v, "Added to Favorite", Snackbar.LENGTH_SHORT).show();
                    }else
                    {
                        deleteFavorite(book_title, currentUser.getUid());
                        Snackbar.make(v, "Deleted from Favorite", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });

        }else{

            favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean toggleState= favourite.isChecked();
                    Log.d("toggleState", String.valueOf(toggleState));
                    if(toggleState.equals(true)){
                        saveFavorite(book_title, currentUser.getUid());
                        Snackbar.make(v, "Added to Favorite", Snackbar.LENGTH_SHORT).show();
                    }else
                    {
                        deleteFavorite(book_title, currentUser.getUid());
                        Snackbar.make(v, "Deleted from Favorite", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });

        }

        //share button
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent= new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Hi, Here is the "+ book_title+ " "+bookurl);
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
            }
        });


        //bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);



    }

    private void deleteFavorite(String book_title, int uid) {
        DatabaseAccess DB= DatabaseAccess.getInstance(this);
        DB.open();
        DB.deleteFavorite(book_title,uid);

    }

    private void saveFavorite(String book_title, int uid) {

        DatabaseAccess DB= DatabaseAccess.getInstance(this);
        DB.open();
        DB.addFavorite(book_title,uid);

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