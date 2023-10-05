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


    public ThanhVien getID(String id){
        String sql = "SELECT * FROM THANHVIEN WHERE maTV = ?";
        List<ThanhVien> list = getData(sql, id);
        return list.get(0);
    }
    public List<ThanhVien> getAll(){
        String sql = "SELECT * FROM THANHVIEN";
        return getData(sql);
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
    public int delete(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        return database.delete("THANHVIEN", "maTV= ?", new String[]{String.valueOf(id)});
    }

    private List<ThanhVien> getData(String sql, String ...selectionArgs){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<ThanhVien> list = new ArrayList<>();

        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            list.add(new ThanhVien(c.getInt(0),c.getString(1),c.getString(2)));
        }

        return list;
    }
}
