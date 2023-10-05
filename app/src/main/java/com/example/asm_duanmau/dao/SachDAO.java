package com.example.asm_duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_duanmau.database.DbHelper;
import com.example.asm_duanmau.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    private DbHelper dbHelper;

    public SachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }


    public Sach getID(String id){
        String sql = "SELECT * FROM SACH WHERE maSach = ?";
        List<Sach> list = getData(sql, id);
        return list.get(0);
    }
    public List<Sach> getAll(){
        String sql = "SELECT * FROM SACH";
        return getData(sql);
    }

    public boolean update(Sach obj, String id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();

        ContentValues contentValues = new ContentValues();

        contentValues.put("tenSach", obj.getTenSach());
        contentValues.put("giaThue", obj.getMaLoai());
        contentValues.put("maLoai", obj.getGiaThue());

        database.setTransactionSuccessful();
        database.endTransaction();

        long check = database.update("SACH", contentValues, "maSach = ?", new String[]{String.valueOf(id)});
        return check != -1;
    }

    public boolean add(Sach obj){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();

        ContentValues contentValues = new ContentValues();

        contentValues.put("tenSach", obj.getTenSach());
        contentValues.put("giaThue", obj.getGiaThue());
        contentValues.put("maLoai", obj.getMaLoai());

        database.setTransactionSuccessful();
        database.endTransaction();

        long check = database.insert("Sach", null, contentValues);
        return check != -1;
    }
    public int delete(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        return database.delete("SACH", "maSach= ?", new String[]{String.valueOf(id)});
    }

    private List<Sach> getData(String sql, String ...selectionArgs){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Sach> list = new ArrayList<>();

        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            list.add(new Sach(c.getInt(0),c.getString(1),c.getInt(2), c.getInt(3)));
        }

        return list;
    }
}
