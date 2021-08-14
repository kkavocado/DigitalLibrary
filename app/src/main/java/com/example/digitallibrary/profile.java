package com.example.digitallibrary;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;


public class profile extends Fragment {

    TextView name, email, about;
    ImageButton logout;
    Button edit;
    private User currentUser;
    DatabaseAccess DB;
    private Book book;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        Bundle bundle = getArguments();
        currentUser = (User) bundle.getSerializable("user");

        name= view.findViewById(R.id.username);
        email= view.findViewById(R.id.email_profile);
        about= view.findViewById(R.id.about);
        logout= view.findViewById(R.id.logout);
        edit= view.findViewById(R.id.edit_btn);

        //set text of textview with current user information
        name.setText(currentUser.getName());
        email.setText(currentUser.getEmail());

        if(currentUser.getAbout()!=null){
            about.setText(currentUser.getAbout());
            about.setVisibility(View.VISIBLE);
        }

        //edit profile button
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(), EditProfile.class);
                i.putExtra("user", currentUser);
                startActivity(i);
            }
        });

        //logout button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}