package com.example.asm_duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_duanmau.database.DbHelper;
import com.example.asm_duanmau.model.ThanhVien;
import com.example.asm_duanmau.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    private DbHelper dbHelper;

    public ThanhVienDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public List<ThanhVien> getAllList(){
        List<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM THANHVIEN", null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    list.add(new ThanhVien(cursor.getString(0), cursor.getString(1), cursor.getInt(2)));
                } while (cursor.moveToNext());
                db.setTransactionSuccessful();
            }

        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }

        return list;
    }


    public boolean update(ThanhVien thanhVien, String id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();

        ContentValues contentValues = new ContentValues();

        contentValues.put("hoTen", thanhVien.getHoTen());
        contentValues.put("namSinh", thanhVien.getNamSinh());

        database.setTransactionSuccessful();
        database.endTransaction();

        long check = database.update("THANHVIEN", contentValues, "maTV = ?", new String[]{String.valueOf(id)});
        return check != -1;
    }

    public boolean add(ThanhVien thanhVien){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();

        ContentValues contentValues = new ContentValues();

        contentValues.put("hoTen", thanhVien.getHoTen());
        contentValues.put("namSinh", thanhVien.getNamSinh());

        database.setTransactionSuccessful();
        database.endTransaction();

        long check = database.insert("THANHVIEN", null, contentValues);
        return check != -1;
    }
    public boolean delete(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        List<ThanhVien> list = getAllList();

        long check = database.delete("THANHVIEN", "maTV=?", new String[]{String.valueOf(list.get(id).getMaTV())});
        return check!=-1;
    }
}
