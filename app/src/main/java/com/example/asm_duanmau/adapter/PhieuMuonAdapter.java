package com.example.asm_duanmau.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_duanmau.R;
import com.example.asm_duanmau.dao.PhieuMuonDAO;
import com.example.asm_duanmau.dao.SachDAO;
import com.example.asm_duanmau.dao.ThanhVienDAO;
import com.example.asm_duanmau.dao.ThuThuDAO;
import com.example.asm_duanmau.model.PhieuMuon;
import com.example.asm_duanmau.model.ThuThu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonViewHolder> {

    List<PhieuMuon> list;
    Context context;

    public PhieuMuonAdapter(List<PhieuMuon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PhieuMuonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quan_ly_phieu_muon, parent, false);
        return new PhieuMuonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonViewHolder holder, int position) {
        PhieuMuon phieuMuon = list.get(position);

        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
        ThuThuDAO thuThuDAO = new ThuThuDAO(context);
        SachDAO sachDAO = new SachDAO(context);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        holder.tvMaPhieuMuon.setText("Mã loại sách: "+phieuMuon.getMaPM());
        holder.tvThanhVien.setText("Thành viên: " + thanhVienDAO.getID(phieuMuon.getMaTV() + "").getHoTen());
        holder.tvThuThu.setText("Thủ thư: " + thuThuDAO.getThuThuById(phieuMuon.getMaTT()).getHoTen());
        holder.tvTenSach.setText("Tên sách: " + sachDAO.getID(phieuMuon.getMaSach() + "").getTenSach());
        holder.tvTienThue.setText("Tiền Thuê: " + sachDAO.getID(phieuMuon.getMaSach() + "").getGiaThue() + "");
        if(phieuMuon.getTraSach() == 0){
            holder.tvDaTraSach.setTextColor(Color.RED);
            holder.tvDaTraSach.setText("Chưa trả sách");
        }else{
            holder.tvDaTraSach.setTextColor(Color.BLUE);
            holder.tvDaTraSach.setText("Đã trả sách");
        }
        holder.tvvNgayMuon.setText("Ngày mượn: " + sdf.format(phieuMuon.getNgay().getTime()));

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");

                builder.setMessage("Bạn có muốn xóa " + list.get(holder.getLayoutPosition()).getMaPM() + "?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PhieuMuonDAO PhieuMuonDAO = new PhieuMuonDAO(context);

                        PhieuMuonDAO.delete(list.get(holder.getLayoutPosition()).getMaPM());
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PhieuMuonViewHolder extends RecyclerView.ViewHolder{
        TextView tvMaPhieuMuon;
        TextView tvThanhVien;
        TextView tvThuThu;
        TextView tvTenSach;
        TextView tvTienThue;
        TextView tvDaTraSach;
        TextView tvvNgayMuon;
        ImageView btnDelete;
        ImageView btnUpdate;

        public PhieuMuonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPhieuMuon = itemView.findViewById(R.id.tv_ma_phieu);
            tvThanhVien = itemView.findViewById(R.id.tv_thanh_vien);
            tvThuThu = itemView.findViewById(R.id.tv_thu_thu);
            tvTenSach = itemView.findViewById(R.id.tv_ten_sach);
            tvTienThue = itemView.findViewById(R.id.tv_tien_thue);
            tvDaTraSach = itemView.findViewById(R.id.tv_da_tra_sach);
            tvvNgayMuon = itemView.findViewById(R.id.tv_ngay_muon);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnUpdate = itemView.findViewById(R.id.btn_edit);
        }
    }
}
