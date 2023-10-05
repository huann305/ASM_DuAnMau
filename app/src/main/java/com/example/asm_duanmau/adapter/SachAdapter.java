package com.example.asm_duanmau.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_duanmau.R;
import com.example.asm_duanmau.dao.LoaiSachDAO;
import com.example.asm_duanmau.dao.SachDAO;
import com.example.asm_duanmau.model.LoaiSach;
import com.example.asm_duanmau.model.Sach;

import java.util.List;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachViewHolder> {

    List<Sach> list;
    Context context;

    public SachAdapter(List<Sach> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quan_ly_sach, parent, false);
        return new SachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachViewHolder holder, int position) {
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
        Sach sach = list.get(position);
        String loaiSach = loaiSachDAO.getID(sach.getMaLoai() + "").getTenLoai();
        holder.tvMaSach.setText("Mã sách: "+sach.getMaSach() + "");
        holder.tvTenSach.setText("Tên sách: " + sach.getTenSach());
        holder.tvLoaiSach.setText("Loại" + loaiSach);
        holder.tvGiaSach.setText("Giá thuê: " + sach.getGiaThue() + "");
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SachDAO sachDAO = new SachDAO(context);
                sachDAO.delete(list.get(holder.getLayoutPosition()).getMaSach());
                list.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SachViewHolder extends RecyclerView.ViewHolder{
        TextView tvMaSach;
        TextView tvTenSach;
        TextView tvLoaiSach;
        TextView tvGiaSach;
        ImageView btnDelete;
        ImageView btnUpdate;

        public SachViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaSach = itemView.findViewById(R.id.tv_ma_sach);
            tvTenSach = itemView.findViewById(R.id.tv_ten_sach);
            tvLoaiSach = itemView.findViewById(R.id.tv_ten_loai_sach);
            tvGiaSach = itemView.findViewById(R.id.tv_tien_thue);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnUpdate = itemView.findViewById(R.id.btn_edit);
        }
    }
}
