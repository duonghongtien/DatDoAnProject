package tiendhph30203.poly.projectdatdoan.FragmentHoaDon;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import tiendhph30203.poly.projectdatdoan.DonMua.Adapter_ChoXacNhanCuaKhachHang;
import tiendhph30203.poly.projectdatdoan.DonMua.HoaDon;
import tiendhph30203.poly.projectdatdoan.DonMua.HoaDonDAO;
import tiendhph30203.poly.projectdatdoan.QuanLyKhachHang.KhachHang;
import tiendhph30203.poly.projectdatdoan.QuanLyKhachHang.KhachHangDAO;
import tiendhph30203.poly.projectdatdoan.R;

public class ChoXacNhanKhachHangFragment extends Fragment {
    private RecyclerView recyclerViewDonMua;
    private FloatingActionButton btnThemDonMua;
    HoaDonDAO qlhd;
    ArrayList<HoaDon> list = new ArrayList<>();

    public ChoXacNhanKhachHangFragment() {

    }


    public static ChoXacNhanKhachHangFragment newInstance() {
        ChoXacNhanKhachHangFragment fragment = new ChoXacNhanKhachHangFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choxacnhankhachhang, container, false);
        recyclerViewDonMua = view.findViewById(R.id.rcDonMua);

//        SharedPreferences preferences = getActivity().getSharedPreferences("thongtin", Context.MODE_PRIVATE);
//        String quyen = (preferences.getString("loaitaikhoan", ""));
//        if (quyen.equals("nguoidung")) {
//            String email = (preferences.getString("email", ""));
//            KhachHangDAO khachHangDAO = new KhachHangDAO(getContext());
//            KhachHang obj = khachHangDAO.getUserName3(email);
//            loadData(1, obj.getManguoidung() + "");
//            Log.d("TAG", "loadData: " + obj.getManguoidung());
//
//        }

        loadData();

        return view;
    }

    public void loadData() {
        qlhd = new HoaDonDAO(getContext());
        list = (ArrayList<HoaDon>) qlhd.getTrangThai0();
        Log.d("TAG", "loadDatazzz: " +list.toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewDonMua.setLayoutManager(linearLayoutManager);
        Adapter_ChoXacNhanCuaKhachHang adapterDonMua = new Adapter_ChoXacNhanCuaKhachHang(list, getContext(), qlhd);
        recyclerViewDonMua.setAdapter(adapterDonMua);
    }

//    private void loadData(int id, String manguoidung) {
//        ArrayList<HoaDon> listDXN = null;
//        if (id == 1) {
//            ArrayList<HoaDon> list = (ArrayList<HoaDon>) qlhd.getAllKH(Integer.parseInt(manguoidung));
//            listDXN = new ArrayList<>();
//            for (int i = 0; i < list.size(); i++) {
//                if (list.get(i).getTrangthai() == 0) {
//                    listDXN.add(list.get(i));
//                }
//            }
//        }
//        Log.d("HH", "ádfasdfs" + listDXN);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerViewDonMua.setLayoutManager(linearLayoutManager);
//        Adapter_ChoXacNhanCuaKhachHang hoaDonAdapter = new Adapter_ChoXacNhanCuaKhachHang(listDXN, getContext(), qlhd);
//        recyclerViewDonMua.setAdapter(hoaDonAdapter);
//    }


//    private void loadData(int id, String maKH) {
//        ArrayList<HoaDon> listDXN = null;
////        if (id == 1) {
////            ArrayList<HoaDon> list = (ArrayList<HoaDon>) qlhd.getAll();
////            listDXN = new ArrayList<>();
////            for (int i = 0; i < list.size(); i++) {
////                if (list.get(i).getTrangthai() == 0) {
////                    listDXN.add(list.get(i));
////                }
////            }
////        } else
//        if (id == 2) {
//            ArrayList<HoaDon> list = (ArrayList<HoaDon>) qlhd.getAllKH(maKH);
//            listDXN = new ArrayList<>();
//            for (int i = 0; i < list.size(); i++) {
//                if (list.get(i).getTrangthai() == 0) {
//                    listDXN.add(list.get(i));
//                }
//            }
//        }
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerViewDonMua.setLayoutManager(linearLayoutManager);
//        Adapter_ChoXacNhanCuaKhachHang adapterDonMua = new Adapter_ChoXacNhanCuaKhachHang(listDXN, getContext(), qlhd);
//        recyclerViewDonMua.setAdapter(adapterDonMua);
//    }
}





