package com.example.digitallibrary.Adapter;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitallibrary.Book;
import com.example.digitallibrary.BookDetails;
import com.example.digitallibrary.R;
import com.example.digitallibrary.User;


import java.util.ArrayList;
import java.util.List;

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.InnerHolder> {

    List<Book> book;
    Context context;
    LayoutInflater mInflater;
    User currentUser;

    public BookRecyclerViewAdapter(List<Book> book, User user, Context context) {
        this.book = book;
        this.context = context;
        this.currentUser=user;
        mInflater= LayoutInflater.from(context);
    }

    public List<Book> getBooks(){
        return book;
    }

    public void setBooks(List<Book> book){
        this.book= book;
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.book, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookRecyclerViewAdapter.InnerHolder holder, int position) {
        holder.initData(position);


        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context=v.getContext();
                Intent intent= new Intent(context, BookDetails.class);
                intent.putExtra("book_title", book.get(position).getTitle());
                intent.putExtra("user", currentUser);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return book.size();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        private ImageView bookimg;
        private TextView title;
        private CardView mainlayout;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            bookimg = itemView.findViewById(R.id.book_img);
            title = itemView.findViewById(R.id.book_title);
            mainlayout=itemView.findViewById(R.id.relativelayoutbook);


        }

        //init data
        public void initData(int position) {

            title.setText(book.get(position).getTitle());
            String imgname = book.get(position).getImageuri();
            int res = context.getResources().getIdentifier(imgname, "drawable", context.getPackageName());
            bookimg.setImageResource(res);
        }

    }
}
