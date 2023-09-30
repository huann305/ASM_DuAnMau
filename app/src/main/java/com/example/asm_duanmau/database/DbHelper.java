package com.example.asm_duanmau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "PNLIB";
    public DbHelper(@Nullable Context context) {
        super(context, DBNAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableThuThu = "CREATE TABLE THUTHU (maTT TEXT PRIMARY KEY , hoTen TEXT NOT NULL, matKhau TEXT NOT NULL, role TEXT NOT NULL)";
        db.execSQL(createTableThuThu);

        db.execSQL("INSERT INTO THUTHU VALUES('admin', 'Vũ Bá Huấn', 'admin', 'admin')");

        String createTableThanhVien = "CREATE TABLE THANHVIEN (maTV INTEGER PRIMARY KEY AUTOINCREMENT, hoTen TEXT NOT NULL, namSinh INTEGER NOT NULL)";
        db.execSQL(createTableThanhVien);

        String createTableLoaiSach = "CREATE TABLE LOAISACH (maLoai INTEGER PRIMARY KEY AUTOINCREMENT, tenLoai TEXT NOT NULL)";
        db.execSQL(createTableLoaiSach);

        String createTableSach = "CREATE TABLE SACH (maSach INTEGER PRIMARY KEY AUTOINCREMENT, tenSach TEXT NOT NULL, giaThue INTEGER NOT NULL, maLoai INTEGER REFERENCES LOAISACH(maLoai))";
        db.execSQL(createTableSach);

        String createTablePhieuMuon = "CREATE TABLE PHIEUMUON (maPM INTEGER PRIMARY KEY AUTOINCREMENT, maTT TEXT REFERENCES THUTHU(maTT), maTV INTEGER REFERENCES THANHVIEN(maTV), maSach INTEGER REFERENCES SACH(maSach), tienThue INTEGER NOT NULL, ngay DATE NOT NULL, traSach INTEGER NOT NULL)";
        db.execSQL(createTablePhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS  THUTHU");
        db.execSQL("DROP TABLE IF EXISTS  LOAISACH");
        db.execSQL("DROP TABLE IF EXISTS  SACH");
        db.execSQL("DROP TABLE IF EXISTS  PHIEUMUON");
        db.execSQL("DROP TABLE IF EXISTS  THANHVIEN");

        onCreate(db);
    }
}
