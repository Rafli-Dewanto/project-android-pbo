package com.sugiartha.juniorandroid.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sugiartha.juniorandroid.model.Auth;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class AuthDao extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "digitalent.db";

    public static final String TABLE_USER = "user";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FULLNAME = "fullname";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_GENDER = "gender";

    public AuthDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FULLNAME + " TEXT NOT NULL, " +
                COLUMN_USERNAME + " TEXT NOT NULL UNIQUE, " +
                COLUMN_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_GENDER + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(sqLiteDatabase);
    }

    public boolean insert(Auth user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FULLNAME, user.getFullname());
        cv.put(COLUMN_USERNAME, user.getUsername());
        cv.put(COLUMN_GENDER, user.getGender());
        cv.put(COLUMN_PASSWORD, user.getPassword());

        long insert = db.insert(TABLE_USER, null, cv);
        db.close();
        return insert != -1;
    }

    @SuppressLint("Range")
    public Auth authenticate(Auth payload) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{payload.getUsername()});

        if (cursor.moveToFirst()) {
            String storedPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            boolean passwordMatch = BCrypt.verifyer().verify(payload.getPassword().toCharArray(), storedPassword).verified;

            if (passwordMatch) {
                Auth user = new Auth();
                user.setFullname(cursor.getString(cursor.getColumnIndex(COLUMN_FULLNAME)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
                user.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));

                cursor.close();
                db.close();
                return user;
            }
        }
        cursor.close();
        db.close();
        return null; // Authentication failed
    }


}
