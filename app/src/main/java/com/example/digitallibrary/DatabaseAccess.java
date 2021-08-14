package com.example.digitallibrary;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseAccess <instance> {


    private final SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    public DatabaseAccess(Context context) {
        this.openHelper = new DatabaseHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    //get a list of editor's pick books
    public ArrayList<Book> getEditorBooks(String editor){

        ArrayList<Book> arrayList= new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM Books WHERE editor = ? ", new String[]{editor});

        if (cursor.moveToFirst()) {
            do {

                arrayList.add(new Book(cursor.getString(1), cursor.getString(2), cursor.getString(3)
                ,cursor.getString(4),cursor.getString(5), cursor.getString(6),
                        cursor.getString(7), cursor.getString(8)));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;

    }

    //get book details by title
    public Book getBookDetails(String title) {

        Book book = null;
        Cursor cursor = database.rawQuery("SELECT * FROM Books WHERE title = ? ", new String[]{title});
        //if(cursor!=null){
        if (cursor.moveToFirst()) {
            book = new Book(cursor.getString(1), cursor.getString(2), cursor.getString(3)
                    , cursor.getString(4), cursor.getString(5), cursor.getString(6)
                    , cursor.getString(7), cursor.getString(8));
        }
        cursor.close();
        return book;
    }

    //get a list of books with genres
    public ArrayList<Book> getGenresBooks(String genres){

        ArrayList<Book> arrayList= new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM Books WHERE genres = ? ", new String[]{genres});

        if (cursor.moveToFirst()) {
            do {

                arrayList.add(new Book(cursor.getString(1), cursor.getString(2), cursor.getString(3)
                        ,cursor.getString(4),cursor.getString(5), cursor.getString(6),
                        cursor.getString(7), cursor.getString(8)));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;

    }

    //get all books
    public ArrayList<Book> getAllBooks(){

        ArrayList<Book> arrayList= new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM Books", null);

        if (cursor.moveToFirst()) {
            do {

                arrayList.add(new Book(cursor.getString(1), cursor.getString(2), cursor.getString(3)
                        ,cursor.getString(4),cursor.getString(5), cursor.getString(6),
                        cursor.getString(7), cursor.getString(8)));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;

    }

    //new user-insert into database
    public boolean register(String name, String email, String password) {

        String insertSql = "insert or ignore into User(name, email, password) select '" + name + "','" + email + "','" + password + "' where not exists (select * from User where email = '" + email + "');";

        try {
            database.execSQL(insertSql);
        } catch (RuntimeException e) {
            Log.d("insert data", e.getLocalizedMessage());
            return false;
        } finally {
            database.close();//add
        }
        return true;

    }



    //login
    public User login(String email, String password) {

        Cursor cursor = database.rawQuery("Select * from user where email=? and password=?", new String[]{email, password});
        User user = new User();
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            int uid= cursor.getInt(0);
            String name = cursor.getString(1);
            String uemail = cursor.getString(2);
            String upassword=cursor.getString(3);
            String about = cursor.getString(4);

            user.setUid(uid);
            user.setName(name);
            user.setEmail(uemail);
            user.setPassoword(upassword);
            user.setAbout(about);

        }
        cursor.close();
        return user;
    }

    //check exist user
    public Boolean checkExistUser(String email){

        Cursor cursor= database.rawQuery("Select * from User where email=?", new String[]{email});
        if(cursor.getCount()>0)
        {
            return true;
        }
        return false;
    }

    //check book exist in favorite list
    public Boolean checkFavoriteList(String title, int uid){

        Cursor cursor= database.rawQuery("Select * from Favorite where book=? and user=?", new String[]{title, String.valueOf(uid)});
        if(cursor.getCount()>0)
        {
            return true;
        }
        return false;
    }

    //add to favorite list
    public Boolean addFavorite(String title, int uid){

        String insertFavorite= "insert into Favorite (book, user) values (?,?)";

        try{
            database.execSQL(insertFavorite, new String[]{title, String.valueOf(uid)});

        }catch (RuntimeException e)
        {
            Log.d("Insert Favorite failed", e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    //delete favorite list
    public Boolean deleteFavorite(String title, int uid){

        String deleteFavorite= "delete from Favorite where book=? and user=?";

        try {
            database.execSQL(deleteFavorite, new String[]{title, String.valueOf(uid)});

        }catch (RuntimeException e)
        {
            Log.d("Delete Favorite failed", e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    //get a list of books in favorite list
    public ArrayList<Book> getFavoritelist(int uid){

        ArrayList<Book> arrayList= new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * from Books join \n" +
                "(SELECT * from Favorite where user=?) as F\n" +
                " on Books.title= F.book ", new String[]{String.valueOf(uid)});

        if (cursor.moveToFirst()) {
            do {

                arrayList.add(new Book(cursor.getString(1), cursor.getString(2), cursor.getString(3)
                        ,cursor.getString(4),cursor.getString(5), cursor.getString(6),
                        cursor.getString(7), cursor.getString(8)));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;

    }

    //update user profile
    public boolean updateProfile(User user){

        String update_user = "update User set name ='" + user.getName()+ "', email='" + user.getEmail() +
                "',password='" + user.getPassoword() + "',about='" + user.getAbout() +
                "' where uid = '" + user.getUid()+ "'";

        try{
            database.execSQL(update_user);
        }catch (RuntimeException e)
        {
            return false;
        }

        return true;
    }

}
