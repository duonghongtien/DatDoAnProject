package tiendhph30203.poly.projectdatdoan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.sql.Blob;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import tiendhph30203.poly.projectdatdoan.DonMua.GioHang;
import tiendhph30203.poly.projectdatdoan.DonMua.GioHangDAO2;
import tiendhph30203.poly.projectdatdoan.Interface.ChangeNumberItemCartList;
import tiendhph30203.poly.projectdatdoan.QuanLyKhachHang.KhachHang;
import tiendhph30203.poly.projectdatdoan.QuanLyKhachHang.KhachHangDAO;
import tiendhph30203.poly.projectdatdoan.SanPham.SanPham;
import tiendhph30203.poly.projectdatdoan.SanPham.SanPhamDAO;

public class showchonthongtindonhang extends AppCompatActivity {
    Toolbar toolbar;
    SanPham sanPham;
    ArrayList<SanPham> list;
    int numberOrder = 1, tienSanPham = 0;
    TextView tvTenSanPhamChonThongTin, tvGiaSanPhamChonThongTin, tvSoLuongShowDetal, tvNoiDungChonThongTinDonHang,txtTongGia;
    ImageView  imgMinus, imgPlus;
    CircleImageView imgAnhSanPhamChonThongTin;
    Button btnThemVaoGioHang;
    KhachHang khachHang;
    private SanPhamDAO sanPhamDAO;
    private GioHangDAO2 gioHangDAO2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showchonthongtindonhang);
        toolbar = (Toolbar) findViewById(R.id.idToolBarGioHang);
        tvTenSanPhamChonThongTin = findViewById(R.id.tvTenSanPhamChonThongTin);
        tvGiaSanPhamChonThongTin = findViewById(R.id.tvGiaSanPhamChonThongTin);
        tvSoLuongShowDetal = findViewById(R.id.tvSoLuongShowDetal);
        tvNoiDungChonThongTinDonHang = findViewById(R.id.tvNoiDungChonThongTinDonHang);
        imgAnhSanPhamChonThongTin = findViewById(R.id.imgAnhSanPhamChonThongTin);
        txtTongGia = findViewById(R.id.txtTongGia);
        imgMinus = findViewById(R.id.imgMinus);
        imgPlus = findViewById(R.id.imgPlus);
        btnThemVaoGioHang = findViewById(R.id.btnThemVaoGioHang);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sản phẩm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sanPhamDAO = new SanPhamDAO(this);
        gioHangDAO2 = new GioHangDAO2(this);
        list = (ArrayList<SanPham>) sanPhamDAO.getAll();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        byte[] anhsanpham = bundle.getByteArray("anhsanpham");
        int masanpham = bundle.getInt("masanpham");
        String tensanpham = bundle.getString("tensanpham", "NULL");
        String giasanpham = bundle.getString("giasanpham", "NULL");
        int soluongtrongkho = bundle.getInt("soluongtrongkho", -1);
        String ngaysanxuat = bundle.getString("ngaysanxuat", "NULL");
        String hansudung = bundle.getString("hansudung", "NULL");
        String linkanhsanpham = bundle.getString("linkanhsanpham","NULL");
//        sanPham = list.get(position);
        //tvTenSanPhamChonThongTin.setText(list.get(position).getTensanpham());
//        if(anhsanpham== null){
//            Picasso.get().load(linkanhsanpham).into(imgAnhSanPhamChonThongTin);
//        }else if(linkanhsanpham == null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(anhsanpham,0,anhsanpham.length);
            imgAnhSanPhamChonThongTin.setImageBitmap(bitmap);
//        }
        tvTenSanPhamChonThongTin.setText(tensanpham);
        tvGiaSanPhamChonThongTin.setText(giasanpham + " VNĐ");
        txtTongGia.setText(giasanpham);

        tvNoiDungChonThongTinDonHang.setText(
                "Tên sản phẩm : " + tensanpham
                        + "\n" + "Giá : " + giasanpham + " VNĐ" + "\n"
                        + "Số lượng trong kho: " + soluongtrongkho + "\n"
                        + "Ngày sản xuất : " + ngaysanxuat
                        + "\n" + "Hạn sử dụng : " + hansudung
                        + "\n" + "Thuế : " + 2 + "%"
                        + "\n" + "Phí giao hàng : " + 2000 + " VNĐ");
        tvSoLuongShowDetal.setText(numberOrder + "");
        imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder < soluongtrongkho) {
                    numberOrder = numberOrder + 1;
                    tvSoLuongShowDetal.setText(numberOrder + "");
                } else {
                    Snackbar snackbar = Snackbar.make(v, "Sản phẩm trong kho không đủ", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

        });
        imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }

                tvSoLuongShowDetal.setText(numberOrder + "");
            }
        });

        SharedPreferences preferences = getSharedPreferences("thongtin", MODE_PRIVATE);
        String userName = (preferences.getString("username", ""));
        String email = (preferences.getString("email", ""));
        String quyen = (preferences.getString("loaitaikhoan", ""));
        if (quyen.equals("nguoidung")) {
            KhachHangDAO khachHangDAO = new KhachHangDAO(this);
            khachHang = khachHangDAO.getUserName3(email);
        }

        btnThemVaoGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GioHang objGioHang = new GioHang();
                if (quyen.equals("nguoidung")) {
                    if (gioHangDAO2.checkValue(masanpham + "", khachHang.getManguoidung() + "") > 0) {
                        Toast.makeText(showchonthongtindonhang.this, "Sản phẩm đã có trong giỏ hàng", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (soluongtrongkho == 0) {
                    Toast.makeText(showchonthongtindonhang.this, "Sản phẩm đã hết hàng", Toast.LENGTH_SHORT).show();
                    return;
                }
                objGioHang.setAnhsanpham(anhsanpham);
                objGioHang.setMasanpham(masanpham);
                objGioHang.setTensanpham(tensanpham);
                if (quyen.equals("nguoidung")) {
                    objGioHang.setManguoidung(khachHang.getManguoidung());
                }
                if (linkanhsanpham== null) {
                    objGioHang.setLinkanhsanpham(linkanhsanpham);
                }
//                if(list.get(position).getAnhsanpham() == null){
//                    objGioHang.setAnhsanpham(list.get(position).getAnhsanpham());
//                }
                objGioHang.setSoluong(numberOrder);
                objGioHang.setGiasanpham(Integer.parseInt(giasanpham));


                long kq = gioHangDAO2.insertGioHang(objGioHang);
                if (kq > 0) {
                    startActivity(new Intent(showchonthongtindonhang.this,MainActivity.class));
                    Toast.makeText(showchonthongtindonhang.this, "Thêm giỏ hàng thành công", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(showchonthongtindonhang.this, "Thêm giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}