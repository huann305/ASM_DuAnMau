package com.example.asm_duanmau.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_duanmau.database.DbHelper;
import com.example.asm_duanmau.model.Top;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {
    private SQLiteDatabase db;
    private Context context;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public ThongKeDAO(Context context) {
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<Top> getTop(){
        String sqlTop = "SELECT maSach, COUNT (maSach) as soLuong FROM PHIEUMUON GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
        List<Top> list = new ArrayList<>();
        SachDAO sachDAO = new SachDAO(context);
        Cursor c = db.rawQuery(sqlTop, null);
        while (c.moveToNext()){
            list.add(new Top(c.getString(c.getColumnIndex("maSach")), c.getInt(c.getColumnIndex("soLuong"))));
        }
        return list;
    }

    public int getDoanhThu(String tuNgay, String denNgay){
        String sql = "SELECT SUM(tienThue) as doanhThu FROM PHIEUMUON WHERE ngay BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, new String[]{tuNgay, denNgay});
        while (c.moveToNext()){
            list.add(Integer.parseInt(c.getString(c.getColumnIndex("doanhThu"))));
        }
        return list.get(0);
    }
}
