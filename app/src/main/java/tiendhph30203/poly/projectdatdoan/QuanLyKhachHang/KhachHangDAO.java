package tiendhph30203.poly.projectdatdoan.QuanLyKhachHang;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tiendhph30203.poly.projectdatdoan.Database.Database;
import tiendhph30203.poly.projectdatdoan.DonMua.HoaDon;

public class KhachHangDAO {
    Database database;
    SQLiteDatabase db;

    public KhachHangDAO(Context context) {
        database = new Database(context);
        db = database.getWritableDatabase();
    }

    @SuppressLint("Range")
    public ArrayList<KhachHang> getDSKhachHang() {
        ArrayList<KhachHang> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM nguoidung", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {

                KhachHang khachHang = new KhachHang();
                khachHang.setManguoidung(cursor.getInt(cursor.getColumnIndex("manguoidung")));
                khachHang.setHoten(cursor.getString(cursor.getColumnIndex("hoten")));
                khachHang.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                khachHang.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                khachHang.setSodienthoai(cursor.getString(cursor.getColumnIndex("sodienthoai")));
                khachHang.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                khachHang.setDiachi(cursor.getString(cursor.getColumnIndex("diachi")));
                khachHang.setLoaitaikhoan(cursor.getString(cursor.getColumnIndex("loaitaikhoan")));
                list.add(khachHang);


            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themkhachhang(String hoten, String username, String password, String sodienthoai, String email, String diachi, String loaitaikhoan) {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("hoten", hoten);
        values.put("username", username);
        values.put("password", password);
        values.put("sodienthoai", sodienthoai);
        values.put("email", email);
        values.put("diachi", diachi);
        values.put("loaitaikhoan", "nguoidung");
        long check = sqLiteDatabase.insert("nguoidung", null, values);
        if (check == -1)
            return false;
        return true;
    }

    public KhachHang getID(String id) {
        String sql = "SELECT * FROM nguoidung WHERE manguoidung=?";
        List<KhachHang> list = searchByManguoidung(Integer.parseInt(id));
        return list.get(0);
    }





    @SuppressLint("Range")
    public List<KhachHang> searchByManguoidung(int manguoidung) {
        ArrayList<KhachHang> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();

        String sql = "SELECT * FROM nguoidung WHERE manguoidung = ?";
        String[] selectionArgs = { String.valueOf(manguoidung) };

        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                KhachHang khachHang = new KhachHang();
                khachHang.setManguoidung(cursor.getInt(cursor.getColumnIndex("manguoidung")));
                khachHang.setHoten(cursor.getString(cursor.getColumnIndex("hoten")));
                khachHang.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                khachHang.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                khachHang.setSodienthoai(cursor.getString(cursor.getColumnIndex("sodienthoai")));
                khachHang.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                khachHang.setDiachi(cursor.getString(cursor.getColumnIndex("diachi")));
                khachHang.setLoaitaikhoan(cursor.getString(cursor.getColumnIndex("loaitaikhoan")));
                list.add(khachHang);
            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        return list;
    }

    public int update(KhachHang obj) {
        ContentValues values = new ContentValues();
        values.put("hoten", obj.getHoten());
        values.put("username", obj.getUsername());
        values.put("password", obj.getPassword());
        values.put("sodienthoai", obj.getSodienthoai());
        values.put("email", obj.getEmail());
        values.put("diachi", obj.getDiachi());
        values.put("loaitaikhoan", obj.getDiachi());
        return db.update("nguoidung", values, "manguoidung=?", new String[]{String.valueOf(obj.getManguoidung())});
    }

    //get data nhieu tham so
    @SuppressLint("Range")
    public List<KhachHang> getData(String sql, String... selectionArgs) {
        ArrayList<KhachHang> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM nguoidung", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                KhachHang khachHang = new KhachHang();
                khachHang.setManguoidung(cursor.getInt(cursor.getColumnIndex("manguoidung")));
                khachHang.setHoten(cursor.getString(cursor.getColumnIndex("hoten")));
                khachHang.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                khachHang.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                khachHang.setSodienthoai(cursor.getString(cursor.getColumnIndex("sodienthoai")));
                khachHang.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                khachHang.setDiachi(cursor.getString(cursor.getColumnIndex("diachi")));
                khachHang.setLoaitaikhoan(cursor.getString(cursor.getColumnIndex("loaitaikhoan")));
                list.add(khachHang);
            } while (cursor.moveToNext());

        }
        return list;
    }

    public List<KhachHang> searchByEmail(String email) {
        ArrayList<KhachHang> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();

        String sql = "SELECT * FROM nguoidung WHERE email = ?";
        String[] selectionArgs = { email };

        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            int columnIndexManguoidung = cursor.getColumnIndex("manguoidung");
            int columnIndexHoten = cursor.getColumnIndex("hoten");
            int columnIndexUsername = cursor.getColumnIndex("username");
            int columnIndexPassword = cursor.getColumnIndex("password");
            int columnIndexSodienthoai = cursor.getColumnIndex("sodienthoai");
            int columnIndexEmail = cursor.getColumnIndex("email");
            int columnIndexDiachi = cursor.getColumnIndex("diachi");
            int columnIndexLoaitaikhoan = cursor.getColumnIndex("loaitaikhoan");

            do {
                KhachHang khachHang = new KhachHang();

                if (columnIndexManguoidung >= 0) {
                    khachHang.setManguoidung(cursor.getInt(columnIndexManguoidung));
                }
                if (columnIndexHoten >= 0) {
                    khachHang.setHoten(cursor.getString(columnIndexHoten));
                }
                if (columnIndexUsername >= 0) {
                    khachHang.setUsername(cursor.getString(columnIndexUsername));
                }
                if (columnIndexPassword >= 0) {
                    khachHang.setPassword(cursor.getString(columnIndexPassword));
                }
                if (columnIndexSodienthoai >= 0) {
                    khachHang.setSodienthoai(cursor.getString(columnIndexSodienthoai));
                }
                if (columnIndexEmail >= 0) {
                    khachHang.setEmail(cursor.getString(columnIndexEmail));
                }
                if (columnIndexDiachi >= 0) {
                    khachHang.setDiachi(cursor.getString(columnIndexDiachi));
                }
                if (columnIndexLoaitaikhoan >= 0) {
                    khachHang.setLoaitaikhoan(cursor.getString(columnIndexLoaitaikhoan));
                }

                list.add(khachHang);
            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        return list;
    }





    public KhachHang getUserName(String id) {
        String sql = "SELECT * FROM nguoidung WHERE username=?";
        List<KhachHang> list = getData(sql, id);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public KhachHang getUserName3(String id) {
        String sql = "SELECT * FROM nguoidung WHERE email=?";
        List<KhachHang> list = searchByEmail(id);
        return list.get(0);


    }

    public KhachHang getUserName2(String id) {
        String sql = "SELECT * FROM nguoidung WHERE username=?";
        List<KhachHang> list = getData(sql, id);
        if (!list.isEmpty()) {
            return list.get(1);
        }

        return null;
    }


    public boolean capNhapThongTin(int manguoidung, String hoten, String username, String email, String diachi) {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoten", hoten);
        values.put("username", username);
        values.put("email", email);
        values.put("diachi", diachi);
        long check = sqLiteDatabase.update("nguoidung", values, "manguoidung = ?", new String[]{String.valueOf(manguoidung)});
        if (check == -1)
            return false;
        return true;

    }


    public int xoaThongTinThanhVien(int manguoidung) {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM hoadon WHERE manguoidung = ?", new String[]{String.valueOf(manguoidung)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        long check = sqLiteDatabase.delete("nguoidung", "manguoidung = ?", new String[]{String.valueOf(manguoidung)});
        if (check == -1)
            return 0;
        return 1;
    }

    public int checkSoDienThoai(String soDienThoai) {
        Cursor cursor = db.rawQuery("SELECT * FROM nguoidung WHERE sodienthoai = ?", new String[]{String.valueOf(soDienThoai)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        return 1;
    }

    public int checkUserName(String userName) {

        Cursor cursor = db.rawQuery("SELECT * FROM nguoidung WHERE username = ?", new String[]{String.valueOf(userName)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        return 1;

    }


    public int checkGmail(String gmail) {

        Cursor cursor = db.rawQuery("SELECT * FROM nguoidung WHERE email = ?", new String[]{String.valueOf(gmail)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        return 1;

    }


}
