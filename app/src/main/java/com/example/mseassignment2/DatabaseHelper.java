package com.example.mseassignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "friends_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create friends table
        db.execSQL(Friend.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Friend.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertFriend(String friend) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Friend.COLUMN_FRIEND, friend);

        // insert row
        long id = db.insert(Friend.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Friend getFriend(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Friend.TABLE_NAME,
                new String[]{Friend.COLUMN_ID, Friend.COLUMN_FRIEND, Friend.COLUMN_TIMESTAMP},
                Friend.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare friend object
        Friend friend = new Friend(
                cursor.getInt(cursor.getColumnIndex(Friend.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Friend.COLUMN_FRIEND)),
                cursor.getString(cursor.getColumnIndex(Friend.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return friend;
    }

    public List<Friend> getAllFriends() {
        List<Friend> friends = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Friend.TABLE_NAME + " ORDER BY " +
                Friend.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Friend friend = new Friend();
                friend.setId(cursor.getInt(cursor.getColumnIndex(Friend.COLUMN_ID)));
                friend.setFriend(cursor.getString(cursor.getColumnIndex(Friend.COLUMN_FRIEND)));
                friend.setTimestamp(cursor.getString(cursor.getColumnIndex(Friend.COLUMN_TIMESTAMP)));

                friends.add(friend);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return friends list
        return friends;
    }

    public int getFriendsCount() {
        String countQuery = "SELECT  * FROM " + Friend.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public void deleteFriend(Friend friend) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Friend.TABLE_NAME, Friend.COLUMN_ID + " = ?",
                new String[]{String.valueOf(friend.getId())});
        db.close();
    }
}
