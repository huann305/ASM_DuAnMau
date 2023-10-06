package com.example.asm_duanmau.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_duanmau.R;
import com.example.asm_duanmau.dao.SachDAO;
import com.example.asm_duanmau.model.Top;

import java.util.List;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.Top10ViewHolder> {
    List<Top> list;
    Context context;

    public Top10Adapter(List<Top> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Top10ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_top10, parent, false);
        return new Top10ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Top10ViewHolder holder, int position) {
        Top top = list.get(position);

        SachDAO sachDAO = new SachDAO(context);

        holder.tvTenSach.setText("Tên sách: " + sachDAO.getID(top.tenSach + "").getTenSach() + "");
        holder.tvSoLuong.setText("Số lượng: " + top.soLuong + "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Top10ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTenSach;
        TextView tvSoLuong;

        public Top10ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSach = itemView.findViewById(R.id.tv_ten_sach);
            tvSoLuong = itemView.findViewById(R.id.tv_so_luong);
        }
    }
}
