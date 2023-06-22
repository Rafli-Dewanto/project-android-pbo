package com.sugiartha.juniorandroid.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.sugiartha.juniorandroid.model.Auth;
import com.sugiartha.juniorandroid.utils.Token;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class AuthDao extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 5;

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
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues cv = new ContentValues();

            cv.put(COLUMN_FULLNAME, user.getFullname());
            cv.put(COLUMN_USERNAME, user.getUsername());
            cv.put(COLUMN_GENDER, user.getGender());
            cv.put(COLUMN_PASSWORD, user.getPassword());

            long insert = db.insertOrThrow(TABLE_USER, null, cv);
            return insert != -1;
        } catch (SQLiteException e) {
            if (e instanceof SQLiteConstraintException) {
                Log.e("AuthDao", "Constraint violation: " + e.getMessage());
            } else {
                Log.e("AuthDao", "SQLException: " + e.getMessage());
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     *
     * @param ctx Activity Context
     * @param payload user payload from login
     * @return Authenticated user (fullname, username, gender)
     */
    @SuppressLint("Range")
    public Auth authenticate(Context ctx, Auth payload) {
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

                String token = Token.generateToken(user);

                SharedPreferences sharedPreferences = ctx.getSharedPreferences("prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", token);
                editor.apply();


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
