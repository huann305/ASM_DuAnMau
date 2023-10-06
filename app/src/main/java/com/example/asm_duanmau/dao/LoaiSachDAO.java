package com.example.asm_duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_duanmau.database.DbHelper;
import com.example.asm_duanmau.model.LoaiSach;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO {
    private DbHelper dbHelper;
    Context context;

    public LoaiSachDAO(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }


    public LoaiSach getID(String id){
        String sql = "SELECT * FROM LOAISACH WHERE maLoai = ?";
        List<LoaiSach> list = getData(sql, id);
        return list.get(0);
    }
    public List<LoaiSach> getAll(){
        String sql = "SELECT * FROM LoaiSach";
        return getData(sql);
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
    public int delete(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        SachDAO sachDAO = new SachDAO(context);
        if(!(sachDAO.checkLoaiSach(id + "").size() > 0)){
            return database.delete("LOAISACH", "maLoai= ?", new String[]{String.valueOf(id)});
        }else{
            return -1;
        }
    }

    private List<LoaiSach> getData(String sql, String ...selectionArgs){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<LoaiSach> list = new ArrayList<>();

        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            list.add(new LoaiSach(c.getInt(0),c.getString(1)));
        }

        return list;
    }
}
