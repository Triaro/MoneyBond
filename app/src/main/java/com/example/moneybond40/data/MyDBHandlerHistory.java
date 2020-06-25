package com.example.moneybond40.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.moneybond40.model.Name;
import com.example.moneybond40.params.Params;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandlerHistory extends SQLiteOpenHelper {
    public MyDBHandlerHistory(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            String create2 = "CREATE TABLE " + Params.TABLE_NAME2 + "("
                    + Params.KEY_IDH + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Params.KEY_AMOUNT + " TEXT, " + Params.KEY_TIME + " TEXT " + ")";
            Log.d("dbAbhi", "Query being run is " + create2);
            db.execSQL(create2);
        }
    }

    public void addHistory(Name name){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(Params.KEY_IDH, name.getIdH());
        values.put(Params.KEY_AMOUNT, name.getAmount());
        values.put(Params.KEY_TIME, name.getTime());

        db.insert(Params.TABLE_NAME2, null, values);
        Log.d("dbAbhi","Successfully Inserted");
        db.close();
    }

    public List<Name> getHistory(){
        List<Name> historyList= new ArrayList<>();
        SQLiteDatabase db= this.getReadableDatabase();

        //generate the query to read from the database
        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor= db.rawQuery(select, null);

        //Loop through row
        if(cursor.moveToFirst()){
            do {
                Name name = new Name();
                name.setId(Integer.parseInt(cursor.getString(0)));
                name.setAmount(cursor.getString(1));
                name.setTime(cursor.getString(2));

                historyList.add(name);
            }while(cursor.moveToNext());
        }
        Log.d("ListCreated","History List Successfully Created");
        return historyList;


    }
 public int updateHistory(Name name){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(Params.KEY_AMOUNT, name.getAmount());
        values.put(Params.KEY_TIME, name.getTime());
        values.put(Params.KEY_NUMBER, name.getNumber());

        //Lets update now
        return db.update(Params.TABLE_NAME, values, Params.KEY_ID
                + "=?", new String[]{String.valueOf(name.getId())});
    }

    public void deleteName(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Params.TABLE_NAME, Params.KEY_ID +"=?", new String[]{String.valueOf(id)});
        db.close();

        Log.d("delete", "DB Deleted Successfully");
    }
}
