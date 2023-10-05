package com.example.asm_duanmau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "PNLIB.db";
    public DbHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableThuThu = "CREATE TABLE THUTHU (maTT TEXT PRIMARY KEY , " +
                "hoTen TEXT NOT NULL, " +
                "matKhau TEXT NOT NULL, " +
                "role TEXT NOT NULL)";
        db.execSQL(createTableThuThu);

        db.execSQL("INSERT INTO THUTHU VALUES('admin', 'Vũ Bá Huấn', 'admin', 'admin')");

        String createTableThanhVien = "CREATE TABLE THANHVIEN (maTV INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hoTen TEXT NOT NULL, " +
                "namSinh TEXT NOT NULL)";
        db.execSQL(createTableThanhVien);

        String createTableLoaiSach = "CREATE TABLE LOAISACH (maLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenLoai TEXT NOT NULL)";
        db.execSQL(createTableLoaiSach);

        String createTableSach = "CREATE TABLE SACH (maSach INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenSach TEXT NOT NULL, " +
                "giaThue INTEGER NOT NULL, " +
                "maLoai INTEGER NOT NULL, " +
                "FOREIGN KEY (maLoai) REFERENCES LOAISACH(maLoai) ON DELETE RESTRICT) ";
        db.execSQL(createTableSach);

        String createTablePhieuMuon = "CREATE TABLE PHIEUMUON (maPM INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maTT TEXT REFERENCES THUTHU(maTT), " +
                "maTV INTEGER REFERENCES THANHVIEN(maTV), " +
                "maSach INTEGER REFERENCES SACH(maSach), " +
                "tienThue INTEGER NOT NULL, " +
                "traSach INTEGER NOT NULL, " +
                "ngay TEXT NOT NULL)";
        db.execSQL(createTablePhieuMuon);

        //data mẫu
        db.execSQL("INSERT INTO LoaiSach VALUES (1, 'Thiếu nhi'),(2,'Tình cảm'),(3, 'Giáo khoa')");
        db.execSQL("INSERT INTO Sach VALUES (1, 'Hãy đợi đấy', 2500, 1), (2, 'Thằng cuội', 1000, 1), (3, 'Lập trình Android', 2000, 3)");
        db.execSQL("INSERT INTO ThuThu VALUES ('thuthu01','Nguyễn Văn Anh','1234','admin'),('thuthu02','Trần Văn Hùng','123abc','ThuThu')");
        db.execSQL("INSERT INTO ThanhVien VALUES (1,'Cao Thu Trang','2000'),(2,'Trần Mỹ Kim','2000')");
        //trả sách: 1: đã trả - 0: chưa trả
        db.execSQL("INSERT INTO PhieuMuon VALUES (1,'thuthu01',1, 1, 2500, 1, '2004/05/30')," +
                "(2,'thuthu01',1, 3, 2000, 0, '2004/05/30')," +
                "(3,'thuthu02',2, 1, 2000, 1, '2004/05/30')");
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
