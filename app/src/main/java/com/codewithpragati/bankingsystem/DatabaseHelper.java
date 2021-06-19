package com.codewithpragati.bankingsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//SEND-TO-USER

public class DatabaseHelper extends SQLiteOpenHelper {
    private String TABLE_NAME = "user_table";
    private String TABLE_NAME1 = "transfers_table";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "User.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (PHONENUMBER INTEGER PRIMARY KEY, NAME TEXT,BALANCE DECIMAL,EMAIL VARCHAR,ACCOUNT_NO VARCHAR,IFSC_CODE VARCHAR)");
        db.execSQL("create table " + TABLE_NAME1 +" (TRANSACTIONID INTEGER PRIMARY KEY AUTOINCREMENT,DATE TEXT,FROMNAME TEXT,TONAME TEXT,AMOUNT DECIMAL,STATUS TEXT)");
        db.execSQL("insert into user_table values(8542361584,'PRAGATI',874210.00,'pragati7273@gmail.com','XXXXXXXXXXXX8542','UCOB5643190')");
        db.execSQL("insert into user_table values(8974538152,'KIRAN',38901.00,'animallover45@gmail.com','XXXXXXXXXXXX8985','MAHB9061238')");
        db.execSQL("insert into user_table values(9574818465,'DIPANSHU',435970.00,'djstar@gmail.com','XXXXXXXXXXXX5626','SBIN7654120')");
        db.execSQL("insert into user_table values(8969471536,'AAYUSHI',375000.50,'chanchalaayushi@gmail.com','XXXXXXXXXXXX6581','HDFC4561230')");
        db.execSQL("insert into user_table values(8745963214,'GARGI',261190.00,'speakergargi@gmail.com','XXXXXXXXXXXX2485','CNRB9876412')");
        db.execSQL("insert into user_table values(7852364159,'RAVI',67230.50,'ravibusinessman@gmail.com','XXXXXXXXXXXX9321','ICIB1360912')");
        db.execSQL("insert into user_table values(8547269851,'JAGRATI ',78123.00,'jaggubachha@gmail.com','XXXXXXXXXXXX6249','punb0065170')");
        db.execSQL("insert into user_table values(9854123675,'HEMA',418570.50,'hemabhabhi@gmail.com','XXXXXXXXXXXX6854','UTIB0967541')");
        db.execSQL("insert into user_table values(8965471236,'ANUSHKA',500000.00,'duplicatesharma@gmail.com','XXXXXXXXXXXX5952','UNIB1245890')");
        db.execSQL("insert into user_table values(8547236912,'VARUN',28900.50,'varunsinghania@gmail.com','XXXXXXXXXXXX1363','ARNB9012457')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        onCreate(db);
    }

    public Cursor readalldata(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user_table", null);
        return cursor;
    }

    public Cursor readparticulardata(String phonenumber){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user_table where phonenumber = " +phonenumber, null);
        return cursor;
    }

    public Cursor readselectuserdata(String phonenumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user_table except select * from user_table where phonenumber = " +phonenumber, null);
        return cursor;
    }

    public void updateAmount(String phonenumber, String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update user_table set balance = " + amount + " where phonenumber = " +phonenumber);
    }

    public Cursor readtransferdata(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from transfers_table", null);
        return cursor;
    }

    public boolean insertTransferData(String date,String from_name, String to_name, String amount, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("DATE", date);
        contentValues.put("FROMNAME", from_name);
        contentValues.put("TONAME", to_name);
        contentValues.put("AMOUNT", amount);
        contentValues.put("STATUS", status);
        Long result = db.insert(TABLE_NAME1, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
}
