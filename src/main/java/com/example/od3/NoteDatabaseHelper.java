package com.example.od3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    public NoteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE notes (id INTEGER PRIMARY KEY AUTOINCREMENT, note TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS notes";
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public long insert(String note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("note", note);
        long id = db.insert("notes", null, values);
        db.close();
        return id;
    }

    public void update(long id, String note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("note", note);
        db.update("notes", values, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void delete(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("notes", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Cursor getAllNotes() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query("notes", null, null, null, null, null, null);
    }



}

