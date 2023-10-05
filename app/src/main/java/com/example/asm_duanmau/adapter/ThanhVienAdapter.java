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
import com.example.asm_duanmau.dao.ThanhVienDAO;
import com.example.asm_duanmau.model.ThanhVien;

import java.util.List;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienViewHolder> {

    List<ThanhVien> list;
    Context context;

    public ThanhVienAdapter(List<ThanhVien> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ThanhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quan_ly_thanh_vien, parent, false);
        return new ThanhVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienViewHolder holder, int position) {
        ThanhVien thanhVien = list.get(position);
        holder.tvHoten.setText(thanhVien.getHoTen());
        holder.tvMaTV.setText(thanhVien.getMaTV() + "");
        holder.tvNamSinh.setText(thanhVien.getNamSinh());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
                thanhVienDAO.delete(list.get(holder.getLayoutPosition()).getMaTV());
                list.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ThanhVienViewHolder extends RecyclerView.ViewHolder{
        TextView tvHoten;
        TextView tvMaTV;
        TextView tvNamSinh;
        ImageView btnDelete;
        ImageView btnUpdate;

        public ThanhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHoten = itemView.findViewById(R.id.tv_username);
            tvMaTV = itemView.findViewById(R.id.tv_ma_thanh_vien);
            tvNamSinh = itemView.findViewById(R.id.tv_ho_ten);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnUpdate = itemView.findViewById(R.id.btn_edit);
        }
    }
}
