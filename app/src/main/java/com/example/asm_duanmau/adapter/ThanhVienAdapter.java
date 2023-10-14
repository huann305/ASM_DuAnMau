package com.example.asm_duanmau.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_duanmau.R;
import com.example.asm_duanmau.dao.LoaiSachDAO;
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
        holder.tvHoten.setText("Họ tên: " + thanhVien.getHoTen());
        holder.tvMaTV.setText("Mã thành viên: " + thanhVien.getMaTV() + "");
        holder.tvNamSinh.setText("Năm sinh: " + thanhVien.getNamSinh());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");

                builder.setMessage("Bạn có muốn xóa " + list.get(holder.getLayoutPosition()).getHoTen() + "?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
                        if(thanhVienDAO.delete(list.get(holder.getLayoutPosition()).getMaTV()) == -1){
                            Toast.makeText(context, "Không thể xóa", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        list.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_them_thanh_vien);
                Window window = dialog.getWindow();

                TextView tvTitle = dialog.findViewById(R.id.tv_title);

                Button btnHuy = dialog.findViewById(R.id.btn_huy);
                Button btnSua = dialog.findViewById(R.id.btn_them);

                EditText edtName = dialog.findViewById(R.id.edt_ho_ten);
                EditText edtAge = dialog.findViewById(R.id.edt_nam_sinh);

                edtName.setText(list.get(holder.getLayoutPosition()).getHoTen() + "");
                edtAge.setText(list.get(holder.getLayoutPosition()).getNamSinh() + "");

                btnSua.setText("Sửa");
                tvTitle.setText("Sửa thông tin thành viên");

                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
                        String name = edtName.getText().toString();
                        String age = edtAge.getText().toString();

                        if(!validate(name, age)){
                            Toast.makeText(context, "Vui lòng nhập đúng thông tin", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Log.e("huh", validate(name, age) + "");

                        thanhVienDAO.update(new ThanhVien(0,name, age), list.get(holder.getLayoutPosition()).getMaTV() + "");
                        list = thanhVienDAO.getAll();

                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public boolean validate(String tenSach, String namSinh) {
        if (tenSach.trim().equals("") || namSinh.trim().equals("")) {
            return false;
        }
        if(!namSinh.matches("[0-9]+")){
            return false;
        }
        return true;

    }
    public class ThanhVienViewHolder extends RecyclerView.ViewHolder{
        TextView tvHoten;
        TextView tvMaTV;
        TextView tvNamSinh;
        ImageView btnDelete;
        CardView layout;

        public ThanhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHoten = itemView.findViewById(R.id.tv_username);
            tvMaTV = itemView.findViewById(R.id.tv_ma_thanh_vien);
            tvNamSinh = itemView.findViewById(R.id.tv_ho_ten);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
