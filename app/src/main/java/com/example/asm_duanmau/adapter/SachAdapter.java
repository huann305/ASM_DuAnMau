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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_duanmau.R;
import com.example.asm_duanmau.adapter.spinneradapter.LoaiSachSpinnerAdapter;
import com.example.asm_duanmau.dao.LoaiSachDAO;
import com.example.asm_duanmau.dao.SachDAO;
import com.example.asm_duanmau.model.LoaiSach;
import com.example.asm_duanmau.model.Sach;

import java.util.List;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachViewHolder> {

    List<Sach> list;
    Context context;
    private String tenSach;
    private int giaThue;
    private int maLoai;

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
        holder.tvLoaiSach.setText("Loại: " + loaiSach);
        holder.tvGiaSach.setText("Giá thuê: " + sach.getGiaThue() + "");
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");

                builder.setMessage("Bạn có muốn xóa " + list.get(holder.getLayoutPosition()).getTenSach() + "?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SachDAO sachDAO = new SachDAO(context);

                        if(sachDAO.delete(list.get(holder.getLayoutPosition()).getMaSach()) == -1){
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
                dialog.setContentView(R.layout.dialog_them_sach);
                Window window = dialog.getWindow();

                TextView tvTitle = dialog.findViewById(R.id.tv_title);

                Button btnHuy = dialog.findViewById(R.id.btn_huy);
                Button btnSua = dialog.findViewById(R.id.btn_them);
                Spinner spinner = dialog.findViewById(R.id.spn_loai_sach);
                EditText edtTenSach = dialog.findViewById(R.id.edt_ten_sach);
                EditText edtTienThue = dialog.findViewById(R.id.tv_tien_thue);

                edtTienThue.setText(list.get(holder.getLayoutPosition()).getGiaThue() + "");
                edtTenSach.setText(list.get(holder.getLayoutPosition()).getTenSach() + "");

                List<LoaiSach> loaiSachList = loaiSachDAO.getAll();

                spinner.setAdapter(new LoaiSachSpinnerAdapter(context, R.layout.item_spinner, loaiSachList));

                for(LoaiSach l : loaiSachList){
                    if(list.get(holder.getLayoutPosition()).getMaLoai() == l.getMaLoai()) spinner.setSelection(loaiSachList.indexOf(l));
                }
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maLoai = loaiSachDAO.getAll().get(position).getMaLoai();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btnSua.setText("Sửa");
                tvTitle.setText("Sửa thông tin sách");

                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SachDAO sachDAO = new SachDAO(context);
                        tenSach = edtTenSach.getText().toString();
                        giaThue = Integer.parseInt(edtTienThue.getText().toString());

                        sachDAO.update(new Sach(0, tenSach, giaThue, maLoai), list.get(holder.getLayoutPosition()).getMaSach() + "");

                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        list = sachDAO.getAll();
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

    public class SachViewHolder extends RecyclerView.ViewHolder{
        TextView tvMaSach;
        TextView tvTenSach;
        TextView tvLoaiSach;
        TextView tvGiaSach;
        ImageView btnDelete;
        CardView layout;

        public SachViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            tvMaSach = itemView.findViewById(R.id.tv_ma_sach);
            tvTenSach = itemView.findViewById(R.id.tv_ten_sach);
            tvLoaiSach = itemView.findViewById(R.id.tv_ten_loai_sach);
            tvGiaSach = itemView.findViewById(R.id.tv_tien_thue);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
