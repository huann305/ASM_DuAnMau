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
import com.example.asm_duanmau.model.LoaiSach;

import java.util.List;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.LoaiSachViewHolder> {

    List<LoaiSach> list;
    Context context;

    public LoaiSachAdapter(List<LoaiSach> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public LoaiSachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quan_ly_loai_sach, parent, false);
        return new LoaiSachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSachViewHolder holder, int position) {
        LoaiSach loaiSach = list.get(position);
        holder.tvMaLoaiSach.setText("Mã loại sách: "+loaiSach.getMaLoai());
        holder.tvTenLoaiSach.setText("Tên loại sách: "+loaiSach.getTenLoai());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiSachDAO LoaiSachDAO = new LoaiSachDAO(context);
                LoaiSachDAO.delete(list.get(holder.getLayoutPosition()).getMaLoai());
                list.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LoaiSachViewHolder extends RecyclerView.ViewHolder{
        TextView tvMaLoaiSach;
        TextView tvTenLoaiSach;
        ImageView btnDelete;
        ImageView btnUpdate;

        public LoaiSachViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaLoaiSach = itemView.findViewById(R.id.tv_ma_loai_sach);
            tvTenLoaiSach = itemView.findViewById(R.id.tv_ten_loai_sach);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnUpdate = itemView.findViewById(R.id.btn_edit);
        }
    }
}
