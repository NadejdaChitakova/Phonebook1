package com.example.phonebook1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

public class DBActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbactivity);
    }

    protected  interface OnQuerySuccess{
        public void onSuccess();
    }

    protected void ExecSQL(String SQL, Object[] args, OnQuerySuccess onQuerySuccess) throws Exception{
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath()+"/PHONEBOOK.db",null);
        if (args != null)
            db.execSQL(SQL, args);
        else
            db.execSQL(SQL);
        db.close();
        onQuerySuccess.onSuccess();
    }

    protected  interface OnSelectSuccess{
        public void onElementSelected(String id, String firstname, String lastname, String phoneNum, String dateOfBirth,String email, String category);
    }

    protected void SelectSQL(String selectQ, String[] args, OnSelectSuccess onSelectSuccess)throws Exception{
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath()+"/PHONEBOOK.db",null);
        Cursor cursor = db.rawQuery(selectQ, args);

        while(cursor.moveToNext()){
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("ID"));
            @SuppressLint("Range") String firstname = cursor.getString(cursor.getColumnIndex("FIRSTNAME"));
            @SuppressLint("Range") String lastname = cursor.getString(cursor.getColumnIndex("LASTNAME"));
            @SuppressLint("Range") String phoneNum = cursor.getString(cursor.getColumnIndex("PHONE_NUM"));
            @SuppressLint("Range") String dateOfBirth = cursor.getString(cursor.getColumnIndex("DATE_OF_BIRTH"));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("EMAIL"));
            @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex("CATEGORY"));
            onSelectSuccess.onElementSelected(id, firstname, lastname, phoneNum,dateOfBirth,email,category);
        }
        db.close();
    }

    protected  void InitDB() throws Exception{
        ExecSQL("CREATE TABLE IF NOT EXISTS PHONEBOOK(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FIRSTNAME TEXT NOT NULL," +
                "LASTNAME TEXT NULL," +
                "PHONE_NUM TEXT NOT NULL," +
                "DATE_OF_BIRTH TEXT  NULL," +
                "EMAIL TEXT NULL," +
                "CATEGORY TEXT NULL,"+
                "UNIQUE(FIRSTNAME, PHONE_NUM))",null, () -> Toast.makeText(getApplicationContext(),"DB Init successful", Toast.LENGTH_LONG).show());
    }
}