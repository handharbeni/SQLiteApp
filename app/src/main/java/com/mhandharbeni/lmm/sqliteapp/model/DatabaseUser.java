package com.mhandharbeni.lmm.sqliteapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mhandharbeni.lmm.sqliteapp.database.User;

/**
 * Created by LMM on 11/15/2017.
 */

public class DatabaseUser extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "SQLITEAPP";
    private static final String TABLE_NAME = "User";

    private static final String FIELD_ID = "id";
    public static final String FIELD_NAMA = "nama";
    public static final String FIELD_ALAMAT = "alamat";

    private SQLiteDatabase sql;

    public DatabaseUser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        sql = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE "+TABLE_NAME+" (" +
                FIELD_ID+" INTEGER PRIMARY KEY,"+
                FIELD_NAMA+" TEXT,"+
                FIELD_ALAMAT+" TEXT"+
                ")";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void add(User user){
        ContentValues values = new ContentValues();
        values.put(FIELD_ID, user.getId());
        values.put(FIELD_NAMA, user.getNama());
        values.put(FIELD_ALAMAT, user.getAlamat());
        sql.insert(TABLE_NAME, null, values);
    }
    public Cursor get(){
        Cursor cursor = sql.query(TABLE_NAME, new String[]{FIELD_ID, FIELD_NAMA, FIELD_ALAMAT}, null, null, null, null, null);
        return cursor;
    }
    public User get(Integer id){
        Cursor cursor = sql.query(TABLE_NAME, new String[]{FIELD_ID, FIELD_NAMA, FIELD_ALAMAT}, FIELD_ID+"=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(Integer.valueOf(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        return user;
    }
    public Cursor get(String field, String value){
        Cursor cursor = sql.query(TABLE_NAME, new String[]{FIELD_ID, FIELD_NAMA, FIELD_ALAMAT}, field+"=?", new String[]{value}, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();

//        User user = new User(Integer.valueOf(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        return cursor;
    }
    public int getCountAll(){
        int count = 0;
        Cursor cursor = sql.query(TABLE_NAME, new String[]{FIELD_ID, FIELD_NAMA, FIELD_ALAMAT}, null, null, null, null, null);
        if (cursor != null)
            count = cursor.getCount();

        return count;
    }
    public int update(User user){
        ContentValues values = new ContentValues();
        values.put(FIELD_NAMA, user.getNama());
        values.put(FIELD_ALAMAT, user.getAlamat());

        return sql.update(TABLE_NAME, values, FIELD_ID+"=?", new String[]{String.valueOf(user.getId())});
    }
    public void delete(User user){
        sql.delete(TABLE_NAME, FIELD_ID+"=?", new String[]{String.valueOf(user.getId())});
    }
    public void closeSQL(){
        if (sql!=null || sql.isOpen()){
            sql.close();
        }
    }
}
