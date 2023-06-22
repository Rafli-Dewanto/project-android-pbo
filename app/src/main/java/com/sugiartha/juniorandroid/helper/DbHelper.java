package com.sugiartha.juniorandroid.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sugiartha.juniorandroid.SQLiteActivity;
import com.sugiartha.juniorandroid.model.Peserta;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 5;

    static final String DATABASE_NAME = "digitalent.db";

    public static final String TABLE_PESERTA = "peserta";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_PESERTA_TABLE = "CREATE TABLE " + TABLE_PESERTA + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_ADDRESS + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_PESERTA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PESERTA);
        onCreate(db);
    }

    public List<Peserta> getAllData() {
        List<Peserta> listPeserta = new ArrayList<>();

        final String QUERY = "SELECT * FROM " + TABLE_PESERTA;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String address = cursor.getString(2);

                Peserta newPeserta = new Peserta(id, name, address);
                listPeserta.add(newPeserta);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listPeserta;
    }

    public boolean insert(Peserta peserta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, peserta.getName());
        cv.put(COLUMN_ADDRESS, peserta.getAddress());

        long insert = db.insert(TABLE_PESERTA, null, cv);
        db.close();
        return insert != -1;
    }

    public void update(Peserta peserta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, peserta.getName());
        cv.put(COLUMN_ADDRESS, peserta.getAddress());

        String whereClause = COLUMN_ID + "=?";
        String[] whereArgs = {String.valueOf(peserta.getId())};

        db.update(TABLE_PESERTA, cv, whereClause, whereArgs);
        db.close();
    }

    public void delete(int id) {
        SQLiteDatabase database = this.getWritableDatabase();

        String updateQuery = "DELETE FROM " + TABLE_PESERTA + " WHERE " + COLUMN_ID + "=" + "'" + id + "'";
        Log.e("update sqlite ", updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }
}
