package com.example.digitallibrary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.digitallibrary.Book;
import com.example.digitallibrary.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GridViewAdapter extends ArrayAdapter<Book> {

    private List<Book> searchList = null;
    private ArrayList<Book> bookArrayList;

    public GridViewAdapter(@NonNull Context context, @NonNull List<Book> booklist) {
        super(context, 0, booklist);
        this.searchList=booklist;
        this.bookArrayList= new ArrayList<Book>();
        this.bookArrayList.addAll(searchList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.book, parent, false);


        }

        ImageView bookimg = v.findViewById(R.id.book_img);
        TextView title = v.findViewById(R.id.book_title);

        Book book = getItem(position);
        title.setText(book.getTitle());
        String imgname = book.getImageuri();
        int res = getContext().getResources().getIdentifier(imgname, "drawable", getContext().getPackageName());
        bookimg.setImageResource(res);
        return v;

    }

    // Filter Class
    public void filter(String charText) {
        //change input from searh view to lowercase
        charText = charText.toLowerCase(Locale.getDefault());
        searchList.clear();
        if (charText.length() == 0) {
            //if there are no input at search bar, show all the books
            searchList.addAll(bookArrayList);
        } else {
            for (Book book : bookArrayList) {
                //if the iput from the search bar contents character of book title, add it to the result list
                if (book.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    searchList.add(book);
                }
            }
        }
        notifyDataSetChanged();
    }
}
