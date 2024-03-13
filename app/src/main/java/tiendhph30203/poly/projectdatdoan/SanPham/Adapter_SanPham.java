package tiendhph30203.poly.projectdatdoan.SanPham;


import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tiendhph30203.poly.projectdatdoan.LoaiSanPham.LoaiSanPham;
import tiendhph30203.poly.projectdatdoan.LoaiSanPham.LoaiSanPhamDAO;
import tiendhph30203.poly.projectdatdoan.R;

public class Adapter_SanPham extends RecyclerView.Adapter<Adapter_SanPham.ViewHolder> {

    List<SanPham> list;
    Context context;
    private SanPhamDAO sanPhamDAO;
    LoaiSanPhamDAO loaiSanPhamDAO;

    CircleImageView avataSanPham;
    String linkAnhSp;
    Bitmap bitmapSql;
    byte[] byteImage;

    public Adapter_SanPham(Context context) {
        this.context = context;
        loaiSanPhamDAO = new LoaiSanPhamDAO(context);
    }

    public void setData(List<SanPham> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Adapter_SanPham(ArrayList<SanPham> list, Context context, SanPhamDAO sanPhamDAO) {
        this.list = list;
        this.context = context;
        this.sanPhamDAO = sanPhamDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recyclesanpham, parent, false);
        return new Adapter_SanPham.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        List<LoaiSanPham> loaiSanPham1 = loaiSanPhamDAO.getAll();
        String tenLoaiSanPham = "";
        for (LoaiSanPham loaiSanPham : loaiSanPham1) {
            if (loaiSanPham.getMaLoaiSanPham() == list.get(position).getMaloai()) {
                tenLoaiSanPham = loaiSanPham.getTenLoaiSanPham();
            }
        }
        holder.txtLoaiSanPham.setText(tenLoaiSanPham);
        holder.txtTenSanPham.setText(list.get(position).getTensanpham());
        // holder.txtAnhSanPham.setText(list.get(position).getAnhsanpham());
//        holder.txtLinkAnhSanPham.setText(list.get(position).getLinkanhsanpham());
        holder.txtGiaSanPham.setText(list.get(position).getGiasanpham());
        holder.txtGiamGia.setText(list.get(position).getGiamgia());
        holder.txtSoLuongTrongKho.setText(String.valueOf(list.get(position).getSoluongtrongkho()));
        holder.txtNgaySanXuat.setText(list.get(position).getNgaysanxuat());
        holder.txtHanSuDung.setText(list.get(position).getHansudung());

        if (list.get(position).getAnhsanpham() == null) {
            Picasso.get().load(list.get(position).getLinkanhsanpham()).into(holder.avataSanPham);
        } else if (list.get(position).getLinkanhsanpham() == null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(list.get(position).getAnhsanpham(), 0, list.get(position).getAnhsanpham().length);
            holder.avataSanPham.setImageBitmap(bitmap);
        }
        holder.ibDeleteSanPham.setOnClickListener(new View.OnClickListener() {
            Button btnDiaLogXoaSanPham, btnDiaLogHuyXoaSanPham;
            private SanPhamDAO sanPhamDAO = new SanPhamDAO(context);

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater1 = ((Activity) context).getLayoutInflater();
                v = inflater1.inflate(R.layout.item_deleltesanpham, null);
                builder.setView(v);
                Dialog dialog = builder.create();
                btnDiaLogHuyXoaSanPham = v.findViewById(R.id.btnDiaLogHuyXoaSanPham);
                btnDiaLogXoaSanPham = v.findViewById(R.id.btnDiaLogXoaSanPham);

                btnDiaLogHuyXoaSanPham.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnDiaLogXoaSanPham.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int check = sanPhamDAO.delete(list.get(position).getMasanpham());
                        switch (check) {
                            case 1:
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list = (ArrayList<SanPham>) sanPhamDAO.getAll();
                                notifyDataSetChanged();
                                dialog.dismiss();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                break;
                            case -1:
                                Toast.makeText(context, "Xóa không thành công vì đang có hóa đơn", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                break;

                        }
                    }
                });
                dialog.show();
            }
        });
        holder.ibUpdateSanPham.setOnClickListener(new View.OnClickListener() {

            EditText edtSuaTenSanPham, edtSuaAnhSanPham, edtSuaLinkAnhSanPham, edtSuaGiaSanPham, edtSuaGiamGia, edtSuaSoLuongTrongKho, edtSuaNgaySanXuat, edtSuaHanSuDung;
            Spinner spUpdateChonLoaiSanPham;
            Button btnDialodHuySuaSanPham, btnDialogSuaSanPham, btnUploadImageud;
            CircleImageView edAnhSPud;
            SanPhamDAO sanPhamDAO = new SanPhamDAO(context);

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater1 = ((Activity) context).getLayoutInflater();
                v = inflater1.inflate(R.layout.item_updatesanpham, null);
                builder.setView(v);
                Dialog dialog = builder.create();
//                edAnhSPud = v.findViewById(R.id.edAnhSPud);
//                avataSanPham = v.findViewById(R.id.avataSanPham);
//                btnUploadImageud = v.findViewById(R.id.btnUploadImageud);
                edtSuaTenSanPham = v.findViewById(R.id.edtSuaTenSanPham);
                edtSuaGiaSanPham = v.findViewById(R.id.edtSuaGiaSanPham);
                edtSuaGiamGia = v.findViewById(R.id.edtSuaGiamGia);
                edtSuaSoLuongTrongKho = v.findViewById(R.id.edtSuaSoLuongTrongKho);
                edtSuaNgaySanXuat = v.findViewById(R.id.edtSuaNgaySanXuat);
                edtSuaHanSuDung = v.findViewById(R.id.edtSuaHanSuDung);
                btnDialodHuySuaSanPham = v.findViewById(R.id.btnDialodHuySuaSanPham);
                btnDialogSuaSanPham = v.findViewById(R.id.btnDialogSuaSanPham);
                spUpdateChonLoaiSanPham = v.findViewById(R.id.spUpdateChonLoaiSanPham);


                edtSuaHanSuDung.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String ngaySanXuat = edtSuaNgaySanXuat.getText().toString().trim();
                        String hanSuDung = edtSuaHanSuDung.getText().toString().trim();

                        // Kiểm tra hạn sử dụng
                        if (hanSuDung.isEmpty()) {
                            edtSuaHanSuDung.setError("Hạn sử dụng không được để trống");

                            return;
                        } else {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            sdf.setLenient(false);
                            try {
                                Date ngaySanXuatDate = sdf.parse(ngaySanXuat);
                                Date hanSuDungDate = sdf.parse(hanSuDung);
                                if (hanSuDungDate.before(ngaySanXuatDate)) {

                                    edtSuaHanSuDung.setError("Hạn sử dụng phải sau ngày sản xuất");
                                    return;
                                }
                            } catch (ParseException e) {
                                edtSuaHanSuDung.setError("Định dạng ngày tháng không hợp lệ (dd/MM/yyyy)");
                                return;
                            }
                        }
                    }
                });

                edtSuaNgaySanXuat.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {


                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String ngaySanXuat = edtSuaNgaySanXuat.getText().toString().trim();

                        // Kiểm tra ngày sản xuất
                        if (ngaySanXuat.isEmpty()) {
                            edtSuaNgaySanXuat.setError("Ngày sản xuất không được để trống");
                            return;
                        } else {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            sdf.setLenient(false);
                            try {
                                sdf.parse(ngaySanXuat);
                            } catch (ParseException e) {

                                edtSuaNgaySanXuat.setError("Định dạng ngày tháng không hợp lệ (dd/MM/yyyy)");
                                return;
                            }
                        }
                    }
                });


                Calendar calendar = Calendar.getInstance();
                edtSuaNgaySanXuat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                String ngay = "";
                                String thang = "";
                                if (day < 10) {
                                    ngay = "0" + day;
                                } else {
                                    ngay = String.valueOf(day);
                                }

                                if ((month + 1) < 10) {
                                    thang = "0" + (month + 1);
                                } else {
                                    thang = String.valueOf(month + 1);
                                }


                                edtSuaNgaySanXuat.setText(ngay + "/" + thang + "/" + year);
                            }
                        }, calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.show();
                    }
                });

                edtSuaHanSuDung.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                String ngay = "";
                                String thang = "";
                                if (day < 10) {
                                    ngay = "0" + day;
                                } else {
                                    ngay = String.valueOf(day);
                                }

                                if ((month + 1) < 10) {
                                    thang = "0" + (month + 1);
                                } else {
                                    thang = String.valueOf(month + 1);
                                }


                                edtSuaHanSuDung.setText(ngay + "/" + thang + "/" + year);
                            }
                        }, calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.show();
                    }
                });
//                Bitmap bitmap  = BitmapFactory.decodeByteArray(list.get(position).getAnhsanpham(), 0, list.get(position).getAnhsanpham().length);
//                edAnhSPud.setImageBitmap(bitmap);

//
//                edAnhSPud.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        ((Activity) context).startActivityForResult(intent, 3);
//
//                    }
//
//                });
//
//                Log.d("TAG", "onClick: " + edAnhSPud);


                edtSuaTenSanPham.setText(list.get(position).getTensanpham());
                edtSuaGiaSanPham.setText(list.get(position).getGiasanpham());
                edtSuaGiamGia.setText(list.get(position).getGiamgia());
                edtSuaSoLuongTrongKho.setText(String.valueOf(list.get(position).getSoluongtrongkho()));
                edtSuaNgaySanXuat.setText(list.get(position).getNgaysanxuat());
                edtSuaHanSuDung.setText(list.get(position).getHansudung());

                SanPham sanPham = new SanPham();
                List<String> lst = new ArrayList<>();
                List<LoaiSanPham> ok = (ArrayList<LoaiSanPham>) loaiSanPhamDAO.getAll();
                for (LoaiSanPham loaiSanPham : ok) {
                    lst.add(loaiSanPham.getTenLoaiSanPham());
                }
                spUpdateChonLoaiSanPham.setSelection(sanPham.getMaloai());

                ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, lst);
                spUpdateChonLoaiSanPham.setAdapter(adapter);
                btnDialodHuySuaSanPham.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnDialogSuaSanPham.setOnClickListener(new View.OnClickListener() {
                    private List<LoaiSanPham> ok = (ArrayList<LoaiSanPham>) loaiSanPhamDAO.getAll();

                    @Override
                    public void onClick(View v) {


                        String tenSanPham = edtSuaTenSanPham.getText().toString().trim();
                        String giaSanPham = edtSuaGiaSanPham.getText().toString().trim();
                        String soLuongTrongKho = edtSuaSoLuongTrongKho.getText().toString().trim();
                        String ngaySanXuat = edtSuaNgaySanXuat.getText().toString().trim();
                        String hanSuDung = edtSuaHanSuDung.getText().toString().trim();
                        String giamgia = edtSuaGiamGia.getText().toString().trim();


                        // Kiểm tra tên sản phẩm
                        if (tenSanPham.isEmpty()) {
                            edtSuaTenSanPham.setError("Tên sản phẩm không được để trống");
                            return;
                        } else if (!tenSanPham.equals(tenSanPham.toUpperCase())) {
                            edtSuaTenSanPham.setError("Tên sản phẩm phải viết hoa");
                            return;
                        }
                        // Kiểm tra ảnh sản phẩm
//                        if (bitmap == null && linkAnhSp == null) {
//                            Toast.makeText(context, "Bạn chưa chọn ảnh", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        if (bitmapSql != null) {
//                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                            bitmapSql.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
//                            byteImage = byteArrayOutputStream.toByteArray();
//                        }

                        // Kiểm tra giá sản phẩm
                        if (giaSanPham.isEmpty()) {
                            edtSuaGiaSanPham.setError("Giá sản phẩm không được để trống");
                            return;
                        } else {
                            try {
                                double gia = Double.parseDouble(giaSanPham);
                                if (gia <= 0) {
                                    edtSuaGiaSanPham.setError("Giá sản phẩm phải lớn hơn 0");
                                    return;
                                }
                            } catch (NumberFormatException e) {
                                edtSuaGiaSanPham.setError("Giá sản phẩm phải là số");
                                return;
                            }
                        }

                        // Kiểm tra giảm giá
                        if (giamgia.isEmpty()) {
                            edtSuaGiamGia.setError("Giảm giá sản phẩm không được để trống");
                            return;
                        } else {
                            try {
                                double gia = Double.parseDouble(giamgia);
                                if (gia < 0) {
                                    edtSuaGiamGia.setError("Giảm giá sản phẩm không được âm");
                                    return;
                                }
                            } catch (NumberFormatException e) {
                                edtSuaGiamGia.setError("Giảm giá sản phẩm phải là số");
                                return;
                            }
                        }

                        // Kiểm tra số lượng trong kho
                        if (soLuongTrongKho.isEmpty()) {
                            edtSuaSoLuongTrongKho.setError("Số lượng trong kho không được để trống");
                            return;
                        } else {
                            try {
                                int soLuong = Integer.parseInt(soLuongTrongKho);
                                if (soLuong <= 0) {
                                    edtSuaSoLuongTrongKho.setError("Số lượng trong kho không được âm");
                                    return;
                                }
                            } catch (NumberFormatException e) {
                                edtSuaSoLuongTrongKho.setError("Số lượng trong kho phải là số");
                                return;
                            }
                        }

                        // Kiểm tra ngày sản xuất
                        if (ngaySanXuat.isEmpty()) {
                            edtSuaNgaySanXuat.setError("Ngày sản xuất không được để trống");
                            return;
                        } else {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            sdf.setLenient(false);
                            try {
                                sdf.parse(ngaySanXuat);
                            } catch (ParseException e) {

                                edtSuaNgaySanXuat.setError("Định dạng ngày tháng không hợp lệ (dd/MM/yyyy)");
                                return;
                            }
                        }

                        // Kiểm tra hạn sử dụng
                        if (hanSuDung.isEmpty()) {
                            edtSuaHanSuDung.setError("Hạn sử dụng không được để trống");

                            return;
                        } else {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            sdf.setLenient(false);
                            try {
                                Date ngaySanXuatDate = sdf.parse(ngaySanXuat);
                                Date hanSuDungDate = sdf.parse(hanSuDung);
                                if (hanSuDungDate.before(ngaySanXuatDate)) {

                                    edtSuaHanSuDung.setError("Hạn sử dụng phải sau ngày sản xuất");
                                    return;
                                }
                            } catch (ParseException e) {
                                edtSuaHanSuDung.setError("Định dạng ngày tháng không hợp lệ (dd/MM/yyyy)");
                                return;
                            }
                        }
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                        SanPham sanPham1 = list.get(position);
                        sanPham1.setTensanpham(edtSuaTenSanPham.getText().toString());
//                        sanPham1.setAnhsanpham(edtSuaAnhSanPham.getText().toString());
//                        sanPham1.setLinkanhsanpham(edtSuaLinkAnhSanPham.getText().toString());
                        sanPham1.setGiasanpham(edtSuaGiaSanPham.getText().toString());
                        sanPham1.setGiamgia(edtSuaGiamGia.getText().toString());
                        sanPham1.setSoluongtrongkho(Integer.parseInt(edtSuaSoLuongTrongKho.getText().toString()));
                        sanPham1.setNgaysanxuat(edtSuaNgaySanXuat.getText().toString());
                        sanPham1.setHansudung(edtSuaHanSuDung.getText().toString());
                        if (bitmapSql == null) {
                            sanPham.setLinkanhsanpham(sanPham.getLinkanhsanpham());
                        }
                        if (bitmapSql != null) {
                            bitmapSql.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
                            byte[] byteImage = byteArrayOutputStream.toByteArray();
                            sanPham.setLinkanhsanpham(null);
                            sanPham.setAnhsanpham(byteImage);
                        }

                        for (LoaiSanPham loaiSanPham : ok) {
                            if (loaiSanPham.getTenLoaiSanPham().equals(spUpdateChonLoaiSanPham.getSelectedItem().toString())) {
                                sanPham1.setMaloai(loaiSanPham.getMaLoaiSanPham());
                            }
                        }
                        if (sanPhamDAO.update(sanPham1) > 0) {
                            list.clear();
                            list = (ArrayList<SanPham>) sanPhamDAO.getAll();
                            setData(list);
                            dialog.dismiss();
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                dialog.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtLoaiSanPham, txtTenSanPham, txtLinkAnhSanPham,
                txtGiaSanPham, txtGiamGia, txtSoLuongTrongKho, txtNgaySanXuat, txtHanSuDung;

        ImageView ibUpdateSanPham, ibDeleteSanPham, txtAnhSanPham;
        ImageView imgChamThan;
        CircleImageView avataSanPham, edAnhSPupdate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLoaiSanPham = itemView.findViewById(R.id.txtLoaiSanPham);
            txtTenSanPham = itemView.findViewById(R.id.txtTenSanPham);
            avataSanPham = itemView.findViewById(R.id.imageAvataSanPham);
            txtGiaSanPham = itemView.findViewById(R.id.txtGiaSanPham);
            txtGiamGia = itemView.findViewById(R.id.txtGiamGia);
            txtSoLuongTrongKho = itemView.findViewById(R.id.txtSoLuongTrongKho);
            txtNgaySanXuat = itemView.findViewById(R.id.txtNgaySanXuat);
            txtHanSuDung = itemView.findViewById(R.id.txtHanSuDung);
            ibUpdateSanPham = itemView.findViewById(R.id.ibUpdateSanPham);
            ibDeleteSanPham = itemView.findViewById(R.id.ibDeleteSanPham);


        }
    }


}
