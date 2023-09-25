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
import com.example.asm_duanmau.dao.DAO;
import com.example.asm_duanmau.model.ThuThu;

import java.util.List;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienViewHolder> {

    List<ThuThu> list;
    Context context;

    public ThanhVienAdapter(List<ThuThu> list, Context context) {
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
        ThuThu thuThu = list.get(position);
        holder.tvUsername.setText(thuThu.getMaTT());
        holder.tvHoTen.setText(thuThu.getHoTen());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DAO dao = new DAO(context);
                dao.deleteThuThu(holder.getAdapterPosition());
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
        TextView tvUsername;
        TextView tvHoTen;
        ImageView btnDelete;
        ImageView btnUpdate;

        public ThanhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvHoTen = itemView.findViewById(R.id.tv_ho_ten);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnUpdate = itemView.findViewById(R.id.btn_edit);
        }
    }
}
