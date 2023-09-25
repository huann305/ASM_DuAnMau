package com.example.asm_duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_duanmau.database.DbHelper;
import com.example.asm_duanmau.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class DAO {
    private DbHelper dbHelper;

    public DAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public List<ThuThu> getListThuThu(){
        List<ThuThu> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM THUTHU", null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    list.add(new ThuThu(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
                } while (cursor.moveToNext());
                db.setTransactionSuccessful();
            }

        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }

        return list;
    }


    public boolean updateThuThu(ThuThu thuThu, String id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();

        ContentValues contentValues = new ContentValues();

        contentValues.put("matKhau", thuThu.getMatKhau());

        database.setTransactionSuccessful();
        database.endTransaction();

        long check = database.update("THUTHU", contentValues, "maTT = ?", new String[]{String.valueOf(id)});
        return check != -1;
    }

    public boolean addThuThu(ThuThu thu){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();

        ContentValues contentValues = new ContentValues();

        contentValues.put("maTT", thu.getMaTT());
        contentValues.put("hoTen", thu.getHoTen());
        contentValues.put("matKhau", "huann305");
        contentValues.put("role", "thuthu");

        database.setTransactionSuccessful();
        database.endTransaction();

        long check = database.insert("THUTHU", null, contentValues);
        return check != -1;
    }
    public boolean deleteThuThu(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        List<ThuThu> list = getListThuThu();

        long check = database.delete("THUTHU", "maTT=?", new String[]{String.valueOf(list.get(id).getMaTT())});
        return check!=-1;
    }
}
