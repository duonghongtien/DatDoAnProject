package tiendhph30203.poly.projectdatdoan.DonMua;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tiendhph30203.poly.projectdatdoan.FragmentHoaDon.HoaDonChiTietActivity;
import tiendhph30203.poly.projectdatdoan.LoaiSanPham.LoaiSanPham;
import tiendhph30203.poly.projectdatdoan.LoaiSanPham.LoaiSanPhamDAO;
import tiendhph30203.poly.projectdatdoan.QuanLyKhachHang.KhachHang;
import tiendhph30203.poly.projectdatdoan.QuanLyKhachHang.KhachHangDAO;
import tiendhph30203.poly.projectdatdoan.R;
import tiendhph30203.poly.projectdatdoan.SanPham.SanPham;
import tiendhph30203.poly.projectdatdoan.SanPham.SanPhamDAO;

public class Adapter_ChoXacNhanCuaKhachHang extends RecyclerView.Adapter<Adapter_ChoXacNhanCuaKhachHang.ViewHolder> {
    HoaDonChiTiet objHoaDonChiTiet;
    ArrayList<HoaDon> list;
    Context context;
    SanPham sanPham;
    SanPhamDAO sanPhamDAO;
    private HoaDonDAO hoaDonDAO;

    public Adapter_ChoXacNhanCuaKhachHang(ArrayList<HoaDon> list, Context context, HoaDonDAO hoaDonDAO) {
        this.list = list;
        this.context = context;
        this.hoaDonDAO = hoaDonDAO;
    }

    public Adapter_ChoXacNhanCuaKhachHang(Context context, ArrayList<HoaDon> list, SanPhamDAO sanPhamDAO) {
        this.context = context;
        this.list = list;
        this.sanPhamDAO = sanPhamDAO;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recyclechoxacnhancuakhachhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        SharedPreferences preferences = context.getSharedPreferences("thongtin", Context.MODE_PRIVATE);
        String quyen = (preferences.getString("loaitaikhoan", ""));
        String email = (preferences.getString("email", ""));
        HoaDon hoaDon = list.get(position);
        objHoaDonChiTiet = new HoaDonChiTiet();
        Log.d("TAG", "onBindViewHoldervvvv: " + hoaDon.toString());


        KhachHang khachHang = null;
        if (hoaDon.getManguoidung() != 0) {
            KhachHangDAO khachHangDAO = new KhachHangDAO(context);
            khachHang = khachHangDAO.getUserName3(email);
            if (khachHang != null) {
                holder.txtTenNguoiDung.setText(khachHang.getHoten());
            }
        } else {
            holder.txtTenNguoiDung.setText("Khách hàng tại quầy");
        }

        Log.d("fsdf", "Hotenkhachhang: " + khachHang.getHoten());
        holder.txtMaHoaDon.setText("" + hoaDon.getMahoadon());
        holder.txtMaNguoiDung.setText("" + hoaDon.getManguoidung());

        holder.txtNgayMua.setText("" + hoaDon.getNgaymua());
        holder.txtTongTien.setText("" + hoaDon.getTongtien() + " VNĐ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HoaDonChiTietActivity.class);
                intent.putExtra("mahoadon", hoaDon.getMahoadon());
                context.startActivity(intent);

            }
        });

        holder.huydonhang.setOnClickListener(new View.OnClickListener() {
            Button btnDialogYesLoaiSanPham, btnDialogNoLoaiSanPham;

            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater1 = ((Activity) context).getLayoutInflater();
                v = inflater1.inflate(R.layout.item_deleteloaisanpham, null);
                builder.setView(v);
                Dialog dialog = builder.create();
                btnDialogYesLoaiSanPham = v.findViewById(R.id.btnDialogYesLoaiSanPham);
                btnDialogNoLoaiSanPham = v.findViewById(R.id.btnDialogNoLoaiSanPham);
                btnDialogNoLoaiSanPham.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnDialogYesLoaiSanPham.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        HoaDonDAO hoaDonDAO = new HoaDonDAO(context);
                        SanPhamDAO sanPhamDAO = new SanPhamDAO(context); // Thêm dòng này

                        int maHoaDon = list.get(position).getMahoadon();
                        ArrayList<HoaDonChiTiet> hoaDonChiTietList = hoaDonDAO.getHoaDonChiTietByMaHoaDon(maHoaDon);

                        for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTietList) {
                            SanPham sanPham = sanPhamDAO.getID(hoaDonChiTiet.getMaSP() + "");
                            if (sanPham != null) {
                                // Phục hồi số lượng trong kho
                                sanPham.setSoluongtrongkho(sanPham.getSoluongtrongkho() + hoaDonChiTiet.getSoLuong());
                                sanPhamDAO.update(sanPham);
                            }
                        }
                        int check = hoaDonDAO.delete(list.get(position).getMahoadon());
                        if (check > 0) {
                            list.clear();
                            list = (ArrayList<HoaDon>) hoaDonDAO.getTrangThai0();
                            notifyDataSetChanged();
                            Toast.makeText(context, "Đã hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Đã có sản phẩm, không thể xóa", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();

            }
        });


        int tinhTrang = list.get(position).getTrangthai();
        if (tinhTrang == 0) {
            holder.txtTrangThai.setText("Trạng thái: " + "Chờ xác nhận");
            holder.txtTrangThai.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaHoaDon, txtMaNguoiDung, txtMaSanPham, txtTenNguoiDung, txtNgayMua, txtTongTien, txtSoLuongDaMua, txtTrangThai;
        Button huydonhang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaHoaDon = itemView.findViewById(R.id.txtMaHoaDon2);
            txtMaNguoiDung = itemView.findViewById(R.id.txtMaNguoiDung2);
            txtTenNguoiDung = itemView.findViewById(R.id.txtTenNguoiDung2);
            txtNgayMua = itemView.findViewById(R.id.txtNgayMua2);
            txtTongTien = itemView.findViewById(R.id.txtTongTien2);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai2);
            huydonhang = itemView.findViewById(R.id.huydonhang);
        }
    }
}
