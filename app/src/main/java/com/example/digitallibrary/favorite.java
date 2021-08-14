package com.example.digitallibrary;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.digitallibrary.Adapter.GridViewAdapter;

import java.util.ArrayList;


public class favorite extends Fragment {


    GridView favoriteGridView;
    private User currentUser;
    DatabaseAccess DB;
    private Book book;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_favorite, container, false);

        Bundle bundle = getArguments();
        currentUser = (User) bundle.getSerializable("user");

        favoriteGridView=view.findViewById(R.id.FavoriteGridView);

        DB= DatabaseAccess.getInstance(getContext());
        DB.open();

        //get a list of books
        ArrayList<Book> bookList= DB.getFavoritelist(currentUser.getUid());

        //Initialize a custom GridView adapter
        GridViewAdapter gridViewAdapter= new GridViewAdapter(getContext(), bookList);
        favoriteGridView.setAdapter(gridViewAdapter);

        //when gridview item click
        favoriteGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i;
                book=gridViewAdapter.getItem(position);
                String bookTitle= book.getTitle();
                i= new Intent(getActivity(), BookDetails.class);
                i.putExtra("book_title", bookTitle);
                i.putExtra("user", currentUser);
                startActivity(i);
            }
        });

        DB.close();


        return view;
    }
}