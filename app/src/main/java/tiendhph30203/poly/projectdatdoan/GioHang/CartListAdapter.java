package tiendhph30203.poly.projectdatdoan.GioHang;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
import tiendhph30203.poly.projectdatdoan.DonMua.GioHangDAO2;
import tiendhph30203.poly.projectdatdoan.Interface.ChangeNumberItemCartList;
import tiendhph30203.poly.projectdatdoan.R;
import tiendhph30203.poly.projectdatdoan.SanPham.SanPham;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartListViewHolder> {
    ArrayList<GioHang> list;
    GioHangDAO2 gioHangDAO2;
    ChangeNumberItemCartList changeNumberItemCartList;
    Context context;

    public CartListAdapter(ArrayList<GioHang> list, Context context, ChangeNumberItemCartList changeNumberItemCartList) {
        this.list = list;
        gioHangDAO2 = new GioHangDAO2(context);
        this.changeNumberItemCartList = changeNumberItemCartList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.from(parent.getContext()).inflate(R.layout.activity_cart_list_adapter, parent, false);
        return new CartListAdapter.CartListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final GioHang gioHang = list.get(position);
        if(gioHang == null){
            return;
        }
//        if( list.get(position).getAnhsanpham() == null) {
////            Picasso.get().load(obj.getLinkAnhSP()).into(holder.imageAvataSanPham);
//            list.get(position).setAnhsanpham(gioHang.setAnhsanpham(););
//        }
//        }else if( list.get(position).getLinkanhsanpham() == null){
////            Bitmap bitmap = BitmapFactory.decodeByteArray(obj.getAnhSP(),0,obj.getAnhSP().length);
////            holder.imageAvataSanPham.setImageBitmap(bitmap);.
//            list.get(position).setLinkanhsanpham("Anh");
//        }

        if (list.get(position).getAnhsanpham() == null) {
            Picasso.get().load(list.get(position).getLinkanhsanpham()).into(holder.imageAvataSanPham);
        } else if (list.get(position).getLinkanhsanpham() == null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(list.get(position).getAnhsanpham(), 0, list.get(position).getAnhsanpham().length);
            holder.imageAvataSanPham.setImageBitmap(bitmap);
        }
        holder.tenSanPham.setText(gioHang.getTensanpham());
        holder.tvGiaGoc.setText(gioHang.getGiasanpham() + " VND");
        holder.tvSoLuong.setText(gioHang.getSoluong() + "");
        holder.tvGiaTong.setText((Math.round(gioHang.getSoluong() * gioHang.getGiasanpham() * 100) / 100) + " VNĐ");
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gioHangDAO2.plusNumber(list, position, context, holder.itemView.getRootView(), new ChangeNumberItemCartList() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemCartList.changed();
                    }
                });
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gioHangDAO2.minusNumber(list, position, holder.itemView.getContext(), new ChangeNumberItemCartList() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemCartList.changed();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class CartListViewHolder extends RecyclerView.ViewHolder {
        private ImageView btnMinus, btnPlus;
        private TextView tenSanPham, tvSoLuong, tvGiaGoc, tvGiaTong;
        CircleImageView imageAvataSanPham;

        public CartListViewHolder(@NonNull View itemView) {
            super(itemView);
            imageAvataSanPham = itemView.findViewById(R.id.imageAvataSanPham);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            tenSanPham = itemView.findViewById(R.id.tenSanPham);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            tvGiaGoc = itemView.findViewById(R.id.tvGiaGoc);
            tvGiaTong = itemView.findViewById(R.id.tvGiaTong);

        }
    }
}
