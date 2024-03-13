package tiendhph30203.poly.projectdatdoan.GioHang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import tiendhph30203.poly.projectdatdoan.DonMua.GioHang;
import tiendhph30203.poly.projectdatdoan.R;

public class CartListComfirmAdapter extends RecyclerView.Adapter<CartListComfirmAdapter.CartListViewHolder> {
    private ArrayList<GioHang> list;


    public CartListComfirmAdapter(ArrayList<GioHang> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cart_list_comfirm_adapter, parent, false);
        return new CartListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListViewHolder holder, int position) {
        final GioHang obj = list.get(position);
        if (obj == null) {
            return;
        }
        if (list.get(position).getAnhsanpham() == null) {
            Picasso.get().load(list.get(position).getLinkanhsanpham()).into(holder.imgSanPham);
        } else if (list.get(position).getLinkanhsanpham() == null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(list.get(position).getAnhsanpham(), 0, list.get(position).getAnhsanpham().length);
            holder.imgSanPham.setImageBitmap(bitmap);
        }
        holder.tvTenSP.setText("Sản phẩm: " + obj.getTensanpham());
        holder.tvSoLuong.setText("Số lượng: " + obj.getSoluong() + "");
        holder.tvThanhTien.setText("Thành tiền: " + (Math.round(obj.getSoluong() * obj.getGiasanpham() * 100) / 100) + " VNĐ");

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class CartListViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgSanPham;

        private TextView tvTenSP, tvSoLuong, tvThanhTien;

        public CartListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            tvThanhTien = itemView.findViewById(R.id.tvThanhTien);


        }
    }
}
