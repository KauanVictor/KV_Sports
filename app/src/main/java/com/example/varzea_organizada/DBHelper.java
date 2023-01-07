package com.example.varzea_organizada;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database_name";
    private static final String TABLE_NAME = "table_name";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        String createTable = "create table " + TABLE_NAME
                + "(id INTEGER PRIMARY KEY, nome TEXT, telefone Integer, email TEXT)";
        MyDB.execSQL(createTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {

        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(MyDB);
    }

    public boolean add(String text, String telefone, String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", text);
        contentValues.put("telefone", telefone);
        contentValues.put("email", email);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public ArrayList getAll() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(cursor.getString(cursor.getColumnIndex("nome")));
            cursor.moveToNext();
        }
        return arrayList;
    }

}
