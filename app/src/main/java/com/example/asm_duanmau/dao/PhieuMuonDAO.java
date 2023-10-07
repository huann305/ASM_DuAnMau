package com.example.asm_duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_duanmau.database.DbHelper;
import com.example.asm_duanmau.model.PhieuMuon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhieuMuonDAO {
    private DbHelper dbHelper;

    public PhieuMuonDAO(Context context) {
        dbHelper = new DbHelper(context);
    }


    public PhieuMuon getID(String id) throws ParseException {
        String sql = "SELECT * FROM PHIEUMUON WHERE maPM = ?";
        List<PhieuMuon> list = getData(sql, id);
        return list.get(0);
    }
    public List<PhieuMuon> checkSach(String id) {
        String sql = "SELECT * FROM PHIEUMUON WHERE maSach = ?";
        List<PhieuMuon> list = null;
        try {
            list = getData(sql, id);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<PhieuMuon> checkThanhVien(String id) {
        String sql = "SELECT * FROM PHIEUMUON WHERE maTV = ?";
        List<PhieuMuon> list = null;
        try {
            list = getData(sql, id);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<PhieuMuon> getAll() throws ParseException {
        String sql = "SELECT * FROM PHIEUMUON";
        return getData(sql);
    }

    public boolean update(PhieuMuon obj, String id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();

        ContentValues contentValues = new ContentValues();

//        contentValues.put("maTT", obj.getMaTT());
        contentValues.put("maTV", obj.getMaTV());
        contentValues.put("maSach", obj.getMaSach());
        contentValues.put("tienThue", obj.getTienThue());
//        contentValues.put("ngay", obj.getNgay().getTime());
        contentValues.put("traSach", obj.getTraSach());

        database.setTransactionSuccessful();
        database.endTransaction();

        long check = database.update("PHIEUMUON", contentValues, "maPM = ?", new String[]{String.valueOf(id)});
        return check != -1;
    }

    public boolean add(PhieuMuon obj){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();

        ContentValues contentValues = new ContentValues();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        contentValues.put("maTT", obj.getMaTT());
        contentValues.put("maTV", obj.getMaTV());
        contentValues.put("maSach", obj.getMaSach());
        contentValues.put("tienThue", obj.getTienThue());
        contentValues.put("ngay", sdf.format(obj.getNgay()));
        contentValues.put("traSach", obj.getTraSach());

        database.setTransactionSuccessful();
        database.endTransaction();

        long check = database.insert("PHIEUMUON", null, contentValues);
        return check != -1;
    }
    public int delete(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        return database.delete("PhieuMuon", "maPM= ?", new String[]{String.valueOf(id)});
    }

    private List<PhieuMuon> getData(String sql, String ...selectionArgs) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<PhieuMuon> list = new ArrayList<>();

        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            list.add(new PhieuMuon(c.getInt(0),c.getString(1), c.getInt(2), c.getInt(3), c.getInt(4), dateFormat.parse(c.getString(6)), c.getInt(5)));
        }

        return list;
    }
}
