package tiendhph30203.poly.projectdatdoan.SanPham;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

public class SanPhamFragment extends Fragment  {
    private RecyclerView recycleViewSanPham;
    private FloatingActionButton btnThemSanPham;
    private Spinner spChonLoaiSanPham;
    SanPhamDAO sanPhamDAO;
    ArrayList<SanPham> list = new ArrayList<>();
    Bitmap bitmapSql = null;
    byte[] byteImage;
    CircleImageView avataSanPham;


    public SanPhamFragment() {

    }

    public static SanPhamFragment newInstance() {
        SanPhamFragment fragment = new SanPhamFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_san_pham, container, false);

        recycleViewSanPham = view.findViewById(R.id.recycleViewSanPham);
        btnThemSanPham = view.findViewById(R.id.btnThemSanPham);
        btnThemSanPham.setOnClickListener(new View.OnClickListener() {
            EditText edtThemTenSanPham, edtThemAnhSanPham, edtThemLinkAnhSanPham,
                    edtThemGiaSanPham, edtThemGiamGia, edtThemSoLuongTrongKho, edtThemNgaySanXuat, edtThemHanSuDung;
            Button btnDialogHuyThemSanPham, btnDialogThemSanPham, btnUpload;
            private SanPhamDAO sanPhamDAO = new SanPhamDAO(getContext());
            private LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO(getContext());
            private List<LoaiSanPham> list = (ArrayList<LoaiSanPham>) loaiSanPhamDAO.getAll();
            String linkAnhSp;

            @Override
            public void onClick(View view1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater1 = ((Activity) getContext()).getLayoutInflater();
                view1 = inflater1.inflate(R.layout.item_themsanpham, null);
                builder.setView(view1);
                Dialog dialog = builder.create();
                edtThemTenSanPham = view1.findViewById(R.id.edtThemTenSanPham);
//                edtThemAnhSanPham = view1.findViewById(R.id.edtThemAnhSanPham);
                btnUpload = view1.findViewById(R.id.btnUploadImage);
//                edtThemLinkAnhSanPham = view1.findViewById(R.id.edtThemLinkAnhSanPham);
                edtThemGiaSanPham = view1.findViewById(R.id.edtThemGiaSanPham);
                edtThemGiamGia = view1.findViewById(R.id.edtThemGiamGia);
                edtThemSoLuongTrongKho = view1.findViewById(R.id.edtThemSoLuongTrongKho);
                edtThemNgaySanXuat = view1.findViewById(R.id.edtThemNgaySanXuat);
                edtThemHanSuDung = view1.findViewById(R.id.edtThemHanSuDung);
                btnDialogThemSanPham = view1.findViewById(R.id.btnDialogThemSanPham);
                btnDialogHuyThemSanPham = view1.findViewById(R.id.btnDialogHuyThemSanPham);
                spChonLoaiSanPham = view1.findViewById(R.id.spChonLoaiSanPham);
                avataSanPham = view1.findViewById(R.id.edAnhSP);
                spChonLoai();

                btnDialogHuyThemSanPham.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });




                Calendar calendar = Calendar.getInstance();
                edtThemNgaySanXuat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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


                                edtThemNgaySanXuat.setText(ngay + "/" + thang + "/" + year);
                            }
                        }, calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.show();
                    }
                });

                edtThemHanSuDung.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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


                                edtThemHanSuDung.setText(ngay + "/" + thang + "/" + year);
                            }
                        }, calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.show();
                    }
                });


                btnUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 3);
                    }
                });




                btnDialogThemSanPham.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String tenSanPham = edtThemTenSanPham.getText().toString().trim();
                        String giaSanPham = edtThemGiaSanPham.getText().toString().trim();
                        String soLuongTrongKho = edtThemSoLuongTrongKho.getText().toString().trim();
                        String ngaySanXuat = edtThemNgaySanXuat.getText().toString().trim();
                        String hanSuDung = edtThemHanSuDung.getText().toString().trim();
                        String giamgia = edtThemGiamGia.getText().toString().trim();



                        // Kiểm tra tên sản phẩm
                        if (tenSanPham.isEmpty()) {
                            edtThemTenSanPham.setError("Tên sản phẩm không được để trống");
                            return;
                        } else if (!tenSanPham.equals(tenSanPham.toUpperCase())) {
                            edtThemTenSanPham.setError("Tên sản phẩm phải viết hoa");
                            return;
                        }
                        // Kiểm tra ảnh sản phẩm
                        if (bitmapSql == null && linkAnhSp == null) {
                            Toast.makeText(getContext(), "Bạn chưa chọn ảnh", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (bitmapSql != null) {
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmapSql.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
                            byteImage = byteArrayOutputStream.toByteArray();
                        }

                        // Kiểm tra giá sản phẩm
                        if (giaSanPham.isEmpty()) {
                            edtThemGiaSanPham.setError("Giá sản phẩm không được để trống");
                            return;
                        } else {
                            try {
                                double gia = Double.parseDouble(giaSanPham);
                                if (gia <= 0) {
                                    edtThemGiaSanPham.setError("Giá sản phẩm phải lớn hơn 0");
                                    return;
                                }
                            } catch (NumberFormatException e) {
                                edtThemGiaSanPham.setError("Giá sản phẩm phải là số");
                                return;
                            }
                        }

                        // Kiểm tra giảm giá
                        if (giamgia.isEmpty()) {
                            edtThemGiamGia.setError("Giảm giá sản phẩm không được để trống");
                            return;
                        } else {
                            try {
                                double gia = Double.parseDouble(giamgia);
                                if (gia < 0) {
                                    edtThemGiamGia.setError("Giảm giá sản phẩm không được âm");
                                    return;
                                }
                            } catch (NumberFormatException e) {
                                edtThemGiamGia.setError("Giảm giá sản phẩm phải là số");
                                return;
                            }
                        }

                        // Kiểm tra số lượng trong kho
                        if (soLuongTrongKho.isEmpty()) {
                            edtThemSoLuongTrongKho.setError("Số lượng trong kho không được để trống");
                            return;
                        } else {
                            try {
                                int soLuong = Integer.parseInt(soLuongTrongKho);
                                if (soLuong <= 0) {
                                    edtThemSoLuongTrongKho.setError("Số lượng trong kho không được âm");
                                    return;
                                }
                            } catch (NumberFormatException e) {
                                edtThemSoLuongTrongKho.setError("Số lượng trong kho phải là số");
                                return;
                            }
                        }

                        // Kiểm tra ngày sản xuất
                        if (ngaySanXuat.equals("")) {
                            edtThemNgaySanXuat.setError("Ngày sản xuất không được để trống");
                            return;
                        } else {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            sdf.setLenient(false);
                            try {
                                sdf.parse(ngaySanXuat);
                            } catch (ParseException e) {

                                edtThemNgaySanXuat.setError("Định dạng ngày tháng không hợp lệ (dd/MM/yyyy)");
                                return;
                            }
                        }

                        // Kiểm tra hạn sử dụng
                        if (hanSuDung.equals("")) {
                            edtThemHanSuDung.setError("Hạn sử dụng không được để trống");

                            return;
                        } else {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            sdf.setLenient(false);
                            try {
                                Date ngaySanXuatDate = sdf.parse(ngaySanXuat);
                                Date hanSuDungDate = sdf.parse(hanSuDung);
                                if (hanSuDungDate.before(ngaySanXuatDate)) {

                                    edtThemHanSuDung.setError("Hạn sử dụng phải sau ngày sản xuất");
                                    return;
                                }
                            } catch (ParseException e) {
                                edtThemHanSuDung.setError("Định dạng ngày tháng không hợp lệ (dd/MM/yyyy)");
                                return;
                            }
                        }


                        SanPham sanPham = new SanPham();
                        sanPham.setTensanpham(tenSanPham);
//                        sanPham.setAnhsanpham(linkAnhSanPham);
                        if (bitmapSql == null) {
                            sanPham.setLinkanhsanpham(linkAnhSp);
                        }
                        if (linkAnhSp == null) {
                            sanPham.setAnhsanpham(byteImage);
                        }
//                        sanPham.setLinkanhsanpham(linkAnhSanPham);
                        sanPham.setGiasanpham(giaSanPham);
                        sanPham.setGiamgia(giamgia);
                        sanPham.setSoluongtrongkho(Integer.parseInt(soLuongTrongKho));
                        sanPham.setNgaysanxuat(ngaySanXuat);
                        sanPham.setHansudung(hanSuDung);
                        for (LoaiSanPham loaiSanPham : list) {
                            if (loaiSanPham.getTenLoaiSanPham().equals(spChonLoaiSanPham.getSelectedItem().toString())) {
                                sanPham.setMaloai(loaiSanPham.getMaLoaiSanPham());
                            }
                        }
                        if (sanPhamDAO.insert(sanPham) > 0) {
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                        reload();
//                        if (edtThemLoaiSanPham.getText().toString().isEmpty()) {
//                            Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
//                        } else {
//                            if (loaiSanPhamDAO.insert(loaiSanPham) > 0) {
//                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
//                            }
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });
        reload();
        return view;
    }

    private void spChonLoai() {
        LoaiSanPhamDAO loaiSanPhamDAO1 = new LoaiSanPhamDAO(getContext());
        List<String> lst = new ArrayList<>();
        List<LoaiSanPham> list = (ArrayList<LoaiSanPham>) loaiSanPhamDAO1.getAll();
        for (LoaiSanPham loaiSanPham : list) {
            lst.add(loaiSanPham.getTenLoaiSanPham());
        }
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, lst);
        spChonLoaiSanPham.setAdapter(adapter);
    }


    public void reload() {
        sanPhamDAO = new SanPhamDAO(getContext());
        list = (ArrayList<SanPham>) sanPhamDAO.getAll();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recycleViewSanPham.setLayoutManager(linearLayoutManager);
        Adapter_SanPham sanPhamAdapter = new Adapter_SanPham(getContext());
        sanPhamAdapter.setData(list);
        recycleViewSanPham.setAdapter(sanPhamAdapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri uri =  data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                avataSanPham.setImageBitmap(bitmap);
                bitmapSql = bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }



}