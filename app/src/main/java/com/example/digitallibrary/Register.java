package com.example.digitallibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText name, email, passowrd, repassword;
    Button register, login;
    DatabaseAccess DB;
    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //reference to view by id
        name= findViewById(R.id.name);
        email= findViewById(R.id.email);
        passowrd= findViewById(R.id.password);
        repassword= findViewById(R.id.repassword);
        register= findViewById(R.id.register);
        login= findViewById(R.id.login_register);

        //database
        DB= DatabaseAccess.getInstance(this);
        DB.open();

        //register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname= name.getText().toString();
                String uemail= email.getText().toString();
                String upassword= passowrd.getText().toString();
                String urepass= repassword.getText().toString();

                //check whether all the required fields have inputs
                isAllFieldsChecked=CheckAllField();
                if(isAllFieldsChecked){
                    //check whether the user exist in the database before
                    Boolean checkexist= DB.checkExistUser(uemail);
                    if (checkexist) {
                        Toast.makeText(Register.this, "User already exist", Toast.LENGTH_SHORT).show();

                    }else {
                        //check whther password and confirm password are match
                        if(upassword.equals(urepass)){
                            //add new user to database
                            Boolean registeruser= DB.register(uname, uemail, upassword);
                            if(registeruser){
                                if(ContextCompat.checkSelfPermission(Register.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                                    ActivityCompat.requestPermissions(Register.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},123);
                                }
                                Toast.makeText(Register.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(Register.this, "User already exist", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(Register.this, "Confirm password not matching", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        });



    }

    private boolean CheckAllField(){
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
}