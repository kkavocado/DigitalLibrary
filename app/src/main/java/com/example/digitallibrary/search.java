package com.example.digitallibrary;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import androidx.appcompat.widget.SearchView;

import com.example.digitallibrary.Adapter.GridViewAdapter;

import java.util.ArrayList;


public class search extends Fragment implements SearchView.OnQueryTextListener{

    private DatabaseAccess DB;
    private User currentUser;
    private Book book;
    SearchView searchView;
    GridView gridView;
    GridViewAdapter gridViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search, container, false);

        Bundle bundle = getArguments();
        currentUser = (User) bundle.getSerializable("user");

        searchView= view.findViewById(R.id.search);
        gridView=view.findViewById(R.id.SearchGridView);

        DB= DatabaseAccess.getInstance(getContext());
        DB.open();

        //get a list of books
        ArrayList<Book> bookList= DB.getAllBooks();

        //Initialize a custom GridView adapter
        gridViewAdapter= new GridViewAdapter(getContext(), bookList);
        gridView.setAdapter(gridViewAdapter);

        searchView.setOnQueryTextListener(this);

        //when gridview item click
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        gridViewAdapter.filter(text);
        return false;
    }
}