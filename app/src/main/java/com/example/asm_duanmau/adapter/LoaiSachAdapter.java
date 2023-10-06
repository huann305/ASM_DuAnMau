package com.example.asm_duanmau.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");

                builder.setMessage("Bạn có muốn xóa " + list.get(holder.getLayoutPosition()).getTenLoai() + "?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoaiSachDAO LoaiSachDAO = new LoaiSachDAO(context);

                        if(LoaiSachDAO.delete(list.get(holder.getLayoutPosition()).getMaLoai()) == -1) {
                            Toast.makeText(context, "Không thế xóa", Toast.LENGTH_SHORT).show();
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
                dialog.setContentView(R.layout.dialog_them_loai);
                Window window = dialog.getWindow();

                TextView tvTitle = dialog.findViewById(R.id.tv_title);

                Button btnHuy = dialog.findViewById(R.id.btn_huy);
                Button btnSua = dialog.findViewById(R.id.btn_them);
                EditText edtTenLoai = dialog.findViewById(R.id.edt_ten_loai_sach);
                
                edtTenLoai.setText(loaiSach.getTenLoai());

                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);

                        String tenSach = edtTenLoai.getText().toString();

                        if(tenSach.trim().equals("")){
                            Toast.makeText(context, "Không được để trống", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        loaiSachDAO.update(new LoaiSach(0, tenSach), list.get(holder.getLayoutPosition()).getMaLoai() + "");
                        Toast.makeText(context, "Cập nhật sách thành công", Toast.LENGTH_SHORT).show();
                        list = loaiSachDAO.getAll();
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

                btnSua.setText("Sửa");
                tvTitle.setText("Sửa thông tin loại sách");

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

    public class LoaiSachViewHolder extends RecyclerView.ViewHolder{
        TextView tvMaLoaiSach;
        TextView tvTenLoaiSach;
        ImageView btnDelete;
        CardView layout;

        public LoaiSachViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaLoaiSach = itemView.findViewById(R.id.tv_ma_loai_sach);
            tvTenLoaiSach = itemView.findViewById(R.id.tv_ten_loai_sach);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
