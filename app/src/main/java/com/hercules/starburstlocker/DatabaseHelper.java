package com.hercules.starburstlocker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class DatabaseHelper extends SQLiteOpenHelper {




    public static final String DATABASE_NAME = "Passwords.db";
    public static final String TABLE_NAME = "Passwords";
    public static final String ID = "Id";
    public static final String PASSWORD = "Password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " +TABLE_NAME+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, PASSWORD PASSWORD NOT NULL)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor displayData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME, null);
        return res;
    }

    public boolean insertData(String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PASSWORD, /**hashMe*/(password));
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1){
            return false;
        }
        return true;
    }

//    public void insertData(String password){
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(PASSWORD, /**hashMe*/(password));
//        db.insert(TABLE_NAME, null, contentValues);
//    }

    /**This method was made to make sure the hash works
    public Cursor displayData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME, null);
        return res;
    }

    /**Hashes the password provided by the user**/
    public String hashMe(String password) {

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256"); //could also be MD5, SHA-256 etc.
            md.reset();
            md.update(password.getBytes("UTF-8"));
            byte[] resultByte = md.digest();
            password = String.format("%01x", new java.math.BigInteger(1, resultByte));
            
        } catch (NoSuchAlgorithmException e) {
            //do something.
        } catch (UnsupportedEncodingException ex) {
            //do something
        }
        return password;

    }

    /**compares password inputs**/
    public boolean checkUserLogin(String password){
        SQLiteDatabase db=this.getWritableDatabase();
        String Query = "select * from " +TABLE_NAME+ " where password='"+ password+"'";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(Query, null);//raw query always holds rawQuery(String Query,select args)
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(cursor!=null && cursor.getCount()>0){
            cursor.close();
            return true;
        }
        else{
            cursor.close();
            return false;
        }

    }

}
