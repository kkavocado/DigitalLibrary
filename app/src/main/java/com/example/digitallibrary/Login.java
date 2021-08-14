package com.example.digitallibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button login, register;
    DatabaseAccess DB;
    private User currentUser;
    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //reference to view by id
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login_btn);
        register=findViewById(R.id.register);

        //database
        DB=DatabaseAccess.getInstance(this);
        DB.open();

        //login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uemail= email.getText().toString();
                String upassword= password.getText().toString();

                //check wheter all the required field have inputs
                isAllFieldsChecked=CheckAllField();

                if(isAllFieldsChecked){
                    //create a new user object with database values
                    User user= DB.login(uemail, upassword);

                    if(user.getEmail() !=null){
                        Toast.makeText(Login.this, "Login sucessfully!", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

                        currentUser= user;
                        intent.putExtra("user", currentUser);
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(Login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //button regsiter
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent((Login.this), Register.class));
            }
        });
    }

    private boolean CheckAllField(){
        if(email.length()==0){
            email.setError("This field is required");
            return false;
        }
        if(password.length()==0){
            password.setError("This field is required");
            return false;
        }

        return true;
    }
}