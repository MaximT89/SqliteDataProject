package com.example.sqlitedataproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataHelper extends SQLiteOpenHelper {

    private static DataHelper instance;

    private final static int DATABASE_VERSION = 5;
    private final static String DATABASE_NAME = "my_project.db";
    private final static String TABLE_NAME_USER = "user_data";

    private static final String COL1 = "id";
    private static final String COL2 = "name";
    private static final String COL3 = "age";
    private static final String COL4 = "city";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME_USER + "("
            + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL2 + " TEXT NOT NULL, "
            + COL3 + " INTEGER, "
            + COL4 + " TEXT" + ");";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME_USER;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DataHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DataHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
        Log.d("TAG", "onUpgrade: run is successful");
    }

    public boolean insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL2, user.getName());
        cv.put(COL3, user.getAge());
        cv.put(COL4, user.getCity());

        long result = db.insert(TABLE_NAME_USER, null, cv);

        return result != -1;
    }

    public void updateUser(String name, String city){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME_USER + " SET city = '" + city + "' WHERE name = '" + name + "';";
        db.execSQL(query);

    }

    public void updateUserSecond(String name, String city){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL4, city);

        String where = "name = '" + name + "';";

//        db.update(TABLE_NAME_USER, cv, where, null);
        db.update(TABLE_NAME_USER, cv, "name = ?", new String[]{name});
    }

    public List<User> getUsers(String userName) {

        List<User> users = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "
                + TABLE_NAME_USER
                + " WHERE name = '"
                + userName
                + "';";

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            users.add(
                    new User(
                            cursor.getString(cursor.getColumnIndex("name")),
                            cursor.getInt(cursor.getColumnIndex("age")),
                            cursor.getString(cursor.getColumnIndex("city"))
                    )
            );
        }

        cursor.close();

        return users;
    }

    public List<User> getUsersSecond(String userName){

        List<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_NAME_USER,
                new String[]{"name", "age", "city"},
                "name = ?",
                new String[]{userName},
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            users.add(
                    new User(
                            cursor.getString(cursor.getColumnIndex("name")),
                            cursor.getInt(cursor.getColumnIndex("age")),
                            cursor.getString(cursor.getColumnIndex("city"))
                    )
            );
        }

        cursor.close();

        return users;
    }

    public void deleteUser(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME_USER + " WHERE name = '" + name + "';";
        db.execSQL(query);
    }

    public void deleteUserSecond(String name){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME_USER, "name = ? AND age = ", new String[]{name});
    }

    public void dropTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DROP_TABLE);
    }
}
