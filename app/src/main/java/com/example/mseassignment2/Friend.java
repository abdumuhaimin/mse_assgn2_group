package com.example.mseassignment2;

public class Friend {
    public static final String TABLE_NAME = "friends";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FRIEND = "friend";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String friend;
    private String timestamp;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_FRIEND + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Friend() {
    }

    public Friend(int id, String friend, String timestamp) {
        this.id = id;
        this.friend = friend;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}