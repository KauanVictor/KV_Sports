package com.example.varzea_organizada;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String TABLE_NAME = "IDU";
    private static final String DATABASE_NAME = "IDU.db";

    //COLS
    public static final String COLS_1 ="ID";
    public static final String COLS_2 ="name";
    public static final String COLS_3 ="address";
    public static final String COLS_4 ="phone";
    public static final String COLS_5 ="email";
    public static final String COLS_6 ="contact";



    public DatabaseHelper(@Nullable Context context)
    {

        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " +TABLE_NAME+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT, phone TEXT, email TEXT, contact TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);
    }


    public boolean insertAtleta(String contato, String estado, String cidade, String posicao)
    {

        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("contato", contato);
        contentValues.put("estado", estado);
        contentValues.put("cidade", cidade);
        contentValues.put("pf", posicao);

        long result  = MyDB.insert("atletas", null, contentValues);
        if (result == -1)  return false;

        else
            return true;
    }




   Cursor readAllData()
   {
       String query = "SELECT * FROM " + TABLE_NAME;
       SQLiteDatabase db = this.getReadableDatabase();
       Cursor cursor = null;
       if (db != null)
       {
           db.rawQuery(query, null);
       }
       return cursor;

   }
}
