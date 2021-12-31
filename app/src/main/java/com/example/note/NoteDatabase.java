package com.example.note;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.lang.annotation.Target;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "noteeey.db";
    private static final String DATABASE_TABLE = "notestable";

    //ten cot cho database table
    private static final String KEY_ID ="id";
    private static final String KEY_TIlTLE ="tiltle";
    private static final String KEY_CONTENT ="content";
    private static final String KEY_DATE ="date";
    private static final String KEY_TIME ="time";
//    private static final String KEY_REMIND ="remind";

     public NoteDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //tao bang
        String query = "CREATE TABLE  "+ DATABASE_TABLE + "("+ KEY_ID+" INTEGER PRIMARY KEY,"+
                KEY_TIlTLE+ " TEXT,"+
                KEY_CONTENT+ " TEXT,"+
                KEY_DATE+ " TEXT,"+
                KEY_TIME+  " TEXT"+")";
//                KEY_REMIND+" DATE"+")";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion >= newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    public long addNote(Note note){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(KEY_TIlTLE, note.getTiltle());
        c.put(KEY_CONTENT, note.getNoidung());
        c.put(KEY_DATE, note.getDate());
        c.put(KEY_TIME, note.getTime());
//        c.put(KEY_REMIND, Helper.convertDateToString(note.getRemindDate()));


        long ID = db.insert(DATABASE_TABLE,     null, c);
        Log.d("đã thêm", "ID" + ID);
        return ID;
    }
    public Note getNote(long id){
        //select * from databaseTable where id =1
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[] {KEY_ID,KEY_TIlTLE,KEY_CONTENT,KEY_DATE,KEY_TIME};
        Cursor cursor = db.query(DATABASE_TABLE,query,KEY_ID+"=?",
                new String[]{String.valueOf(id)},null, null, null,null);
        if(cursor != null)
            cursor.moveToFirst();

        return new Note(
                Long.parseLong(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
//                Helper.convertStringToDate(cursor.getString(5)));
    };

    public List<Note> getNotes(){

        List<Note> allNotes = new ArrayList<>();

        String query = " SELECT * FROM "+DATABASE_TABLE+" ORDER BY "+KEY_ID+" DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                Note note = new Note();
                note.setID(Long.parseLong(cursor.getString(0)));
                note.setTiltle(cursor.getString(1));
                note.setNoidung(cursor.getString(2));
                note.setDate(cursor.getString(3));
                note.setTime(cursor.getString(4));
//                note.setRemindDate(Helper.convertStringToDate(cursor.getString(5)));
                allNotes.add(note);

            }while (cursor.moveToNext());
        }
        return allNotes;
    }
    public int editNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        Log.d("Edited","Sửa tiêu đề: ->"+note.getTiltle()+"\n ID -> " +note.getID());
        c.put(KEY_TIlTLE, note.getTiltle());
        c.put(KEY_CONTENT, note.getNoidung());
        c.put(KEY_DATE, note.getDate());
        c.put(KEY_TIME, note.getTime());
//        c.put(KEY_REMIND, Helper.convertDateToString(note.getRemindDate()));
        return db.update(DATABASE_TABLE,c,KEY_ID+"=?",new String[]{String.valueOf(note.getID())});
    }

        void deleteNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, KEY_ID+"=?", new String[]{String.valueOf(id)});
        db.close();
    }

}
