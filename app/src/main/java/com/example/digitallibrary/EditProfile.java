package com.example.digitallibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EditProfile extends AppCompatActivity {

    private User currentUser;
    private int lastfragment;
    DatabaseAccess DB;
    Button save;
    EditText name, email, passowrd, repassword, about;
    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //get current user, last fragment, book_title from lat intent
        currentUser = (User) getIntent().getSerializableExtra("user");
        lastfragment = 0;

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        passowrd=findViewById(R.id.password);
        repassword=findViewById(R.id.repassword);
        about=findViewById(R.id.about_me);
        save=findViewById(R.id.save);

        name.setText(currentUser.getName());
        email.setText(currentUser.getEmail());
        about.setText(currentUser.getAbout());

        //connect database
        DB= new DatabaseAccess(this);
        DB.open();

        //save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isAllFieldsChecked = CheckAllField();
                if (isAllFieldsChecked) {

                    String newName = name.getText().toString();
                    String newEmail = email.getText().toString();
                    String newAbout = about.getText().toString();
                    String newPassword = passowrd.getText().toString();
                    String newrepassword=passowrd.getText().toString();

                    if(newPassword.equals(newrepassword)) {
                        currentUser.setName(newName);
                        currentUser.setEmail(newEmail);
                        currentUser.setPassoword(newPassword);
                        currentUser.setAbout(newAbout == null ? "" : newAbout);

                        Boolean update = DB.updateProfile(currentUser);

                        if (update) {
                            Toast.makeText(EditProfile.this, "Update successfully", Toast.LENGTH_SHORT).show();

                            Fragment selectedFragment = null;
                            selectedFragment = new profile();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user", currentUser);//pass the value user
                            selectedFragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                        } else {
                            Toast.makeText(EditProfile.this, "Update failed", Toast.LENGTH_SHORT).show();

                        }

                    }else {
                        Toast.makeText(EditProfile.this, "Confirm password not match!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        //bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private boolean CheckAllField() {

        if(name.length()==0){
            name.setError("This field is required");
            return false;
        }
        if(email.length()==0){
            email.setError("This field is required");
            return false;
        }
        if(passowrd.length()==0){
            passowrd.setError("This field is required");
            return false;
        }
        if(repassword.length()==0){
            repassword.setError("This field is required");
            return false;
        }
        return true;
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
