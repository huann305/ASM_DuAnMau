package com.example.asm_duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_duanmau.database.DbHelper;
import com.example.asm_duanmau.model.LoaiSach;
import com.example.asm_duanmau.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    private DbHelper dbHelper;

    public SachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public List<Sach> getAllList(){
        List<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM SACH", null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
                } while (cursor.moveToNext());
                db.setTransactionSuccessful();
            }

        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }

        return list;
    }


    public boolean update(Sach obj, String id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();

        ContentValues contentValues = new ContentValues();

        contentValues.put("tenSach", obj.getTenSach());
        contentValues.put("giaThue", obj.getGiaThue());
        contentValues.put("maLoai", obj.getMaLoai());

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

        long check = database.insert("SACH", null, contentValues);
        return check != -1;
    }
    public boolean delete(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        List<Sach> list = getAllList();

        long check = database.delete("SACH", "maSach=?", new String[]{String.valueOf(list.get(id).getMaSach())});
        return check!=-1;
    }
}
