package com.example.moneybond40.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.moneybond40.model.LentName;
import com.example.moneybond40.params.Params;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {
    public MyDBHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create= "CREATE TABLE " + Params.TABLE_NAME + "("
                + Params.KEY_ID+ " INTEGER PRIMARY KEY, " + Params.KEY_LENTNAME + " TEXT, " +
                Params.KEY_LENTMONEY + " TEXT"+ ")";
        Log.d("dbAbhi", "Query being run is "+ create);
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addName(LentName name){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(Params.KEY_LENTNAME, name.getLentName());
        values.put(Params.KEY_LENTMONEY, name.getLentMoney());

        db.insert(Params.TABLE_NAME, null, values);
        Log.d("dbAbhi","Successfully Inserted");
        db.close();
    }

    public List<LentName> getAllNames(){
        List<LentName> nameList= new ArrayList<>();
        SQLiteDatabase db= this.getReadableDatabase();

        //generate the query to read from the database
        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor= db.rawQuery(select, null);

        //Loop through row
        if(cursor.moveToFirst()){
            do {
                LentName lentName = new LentName();
                lentName.setId(Integer.parseInt(cursor.getString(0)));
                lentName.setLentName(cursor.getString(1));
                lentName.setLentMoney(cursor.getString(2));
                nameList.add(lentName);
            }while(cursor.moveToNext());
        }
        return nameList;
    }

    public int updateName(LentName name){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(Params.KEY_LENTNAME, name.getLentName());
        values.put(Params.KEY_LENTMONEY, name.getLentMoney());

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
