package com.example.asm_duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_duanmau.database.DbHelper;
import com.example.asm_duanmau.model.LoaiSach;
import com.example.asm_duanmau.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO {
    private DbHelper dbHelper;

    public LoaiSachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public List<LoaiSach> getAllList(){
        List<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM LOAISACH", null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    list.add(new LoaiSach(cursor.getInt(0),cursor.getString(1)));
                } while (cursor.moveToNext());
                db.setTransactionSuccessful();
            }

        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }

        return list;
    }


    public boolean update(LoaiSach obj, String id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();

        ContentValues contentValues = new ContentValues();

        contentValues.put("tenLoai", obj.getTenLoai());

        database.setTransactionSuccessful();
        database.endTransaction();

        long check = database.update("LOAISACH", contentValues, "maLoai = ?", new String[]{String.valueOf(id)});
        return check != -1;
    }

    public boolean add(LoaiSach obj){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();

        ContentValues contentValues = new ContentValues();

        contentValues.put("tenLoai", obj.getTenLoai());

        database.setTransactionSuccessful();
        database.endTransaction();

        long check = database.insert("LOAISACH", null, contentValues);
        return check != -1;
    }
    public boolean delete(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        List<LoaiSach> list = getAllList();

        long check = database.delete("LOAISACH", "maLoai=?", new String[]{String.valueOf(list.get(id).getMaLoai())});
        return check!=-1;
    }
}
