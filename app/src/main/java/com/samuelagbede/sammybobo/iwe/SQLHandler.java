package com.samuelagbede.sammybobo.iwe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Agbede on 27/08/2016.
 */

public class SQLHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Notes.db";
    public static final String TABLE_NOTES = "notes";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE= "note_date";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_TAG = "tag";

    public SQLHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String CREATE_NOTES_TABLE = "CREATE TABLE " +
                TABLE_NOTES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_DATE
                + " TEXT," + COLUMN_NOTE + " TEXT, " + COLUMN_TAG + " TEXT" + ")";

        sqLiteDatabase.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(sqLiteDatabase);
    }

    public void addNotes(Notes notes)
    {
        Log.d("Notes", notes.getNote());
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NOTE, notes.getNote());
        contentValues.put(COLUMN_TAG, notes.getTag());
        contentValues.put(COLUMN_DATE, notes.getDate());

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(TABLE_NOTES, null, contentValues);
        sqLiteDatabase.close();
    }
    public void update(Notes notes)
    {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NOTE, notes.getNote());
        contentValues.put(COLUMN_TAG, notes.getTag());
        contentValues.put(COLUMN_DATE, notes.getDate());

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Log.d("ZNotes", notes.getId()+"");
        sqLiteDatabase.update(TABLE_NOTES, contentValues, "_id = ?", new String[]{ String.valueOf(notes.getId())});
        sqLiteDatabase.close();
    }

    public Notes getNote(int id){
          Notes notes = new Notes();
        String query = "Select * FROM " + TABLE_NOTES + " WHERE " + COLUMN_ID + " =  \"" + id + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
          return notes;
    }

    public ArrayList<Notes> getAllNotes(){
        String query = "Select * FROM " + TABLE_NOTES + " ORDER BY "+COLUMN_DATE + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Notes> notes = new ArrayList<Notes>();


        if (cursor.moveToFirst()){
            cursor.moveToPosition(0);
            while(cursor.moveToNext()){
                Notes _notes = new Notes();
                _notes = new Notes(cursor.getInt(0),cursor.getString(1),cursor.getString(2), cursor.getString(3));
                notes.add(_notes);
            }
        }
        else
        {
            notes = null;
        }
        cursor.close();
        db.close();
        return notes;
       // return notes;
    }

    public int deleteNotes(int note_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("String value", note_id +" ");
        return db.delete(TABLE_NOTES, "_id = ?", new String[]{ String.valueOf(note_id) });
    }
}
