package com.example.asm_duanmau.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_duanmau.R;
import com.example.asm_duanmau.adapter.spinneradapter.SachSpinnerAdapter;
import com.example.asm_duanmau.adapter.spinneradapter.ThanhVienSpinnerAdapter;
import com.example.asm_duanmau.dao.PhieuMuonDAO;
import com.example.asm_duanmau.dao.SachDAO;
import com.example.asm_duanmau.dao.ThanhVienDAO;
import com.example.asm_duanmau.dao.ThuThuDAO;
import com.example.asm_duanmau.model.PhieuMuon;
import com.example.asm_duanmau.model.Sach;
import com.example.asm_duanmau.model.ThanhVien;
import com.example.asm_duanmau.model.ThuThu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonViewHolder> {

    List<PhieuMuon> list;
    Context context;
    int maTV;
    int maSach;
    int tienThue;
    int traSach = 0;

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

        holder.tvMaPhieuMuon.setText("Mã loại sách: " + phieuMuon.getMaPM());
        holder.tvThanhVien.setText("Thành viên: " + thanhVienDAO.getID(phieuMuon.getMaTV() + "").getHoTen());
        holder.tvThuThu.setText("Thủ thư: " + thuThuDAO.getThuThuById(phieuMuon.getMaTT()).getHoTen());
        holder.tvTenSach.setText("Tên sách: " + sachDAO.getID(phieuMuon.getMaSach() + "").getTenSach());
        holder.tvTienThue.setText("Tiền Thuê: " + sachDAO.getID(phieuMuon.getMaSach() + "").getGiaThue() + "");
        if (phieuMuon.getTraSach() == 0) {
            holder.tvDaTraSach.setTextColor(Color.RED);
            holder.tvDaTraSach.setText("Chưa trả sách");
        } else {
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
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_them_phieu_muon);
                Window window = dialog.getWindow();

                TextView tvTitle = dialog.findViewById(R.id.tv_title);

                Button btnHuy = dialog.findViewById(R.id.btn_huy);
                Button btnSua = dialog.findViewById(R.id.btn_them);
                TextView tvMaPhieu = dialog.findViewById(R.id.tv_ma_phieu);
                TextView tvThuThu = dialog.findViewById(R.id.tv_thu_thu);
                TextView tvNgayMuon = dialog.findViewById(R.id.tv_ngay_muon);
                Spinner spnThanhVien = dialog.findViewById(R.id.spn_thanh_vien);
                Spinner spnSach = dialog.findViewById(R.id.spn_ten_sach);
                TextView tvTienThue = dialog.findViewById(R.id.edt_tien_thue);
                CheckBox checkBox = dialog.findViewById(R.id.cbDaTraSach);

                checkBox.setVisibility(View.VISIBLE);

                if(list.get(holder.getLayoutPosition()).getTraSach() == 1) checkBox.setChecked(true);

                btnSua.setText("Sửa");
                tvTitle.setText("Sửa phiếu mượn");

                ThuThuDAO thuThuDAO = new ThuThuDAO(context);
                ThuThu thuThu = thuThuDAO.getThuThuById(phieuMuon.getMaTT());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String formattedDate = sdf.format(phieuMuon.getNgay());

                tvNgayMuon.setText("Ngày mượn: " + formattedDate + "");
                tvThuThu.setText("Thủ thư: " + thuThu.getHoTen());
                tvMaPhieu.setText("Mã phiếu: " + phieuMuon.getMaPM());

                spnThanhVien.setAdapter(new ThanhVienSpinnerAdapter(context, R.layout.item_spinner, thanhVienDAO.getAll()));
                spnSach.setAdapter(new SachSpinnerAdapter(context, R.layout.item_spinner, sachDAO.getAll()));

                Sach sach = sachDAO.getID(list.get(holder.getLayoutPosition()).getMaSach() + "");

                List<Sach> sachList = sachDAO.getAll();

                for (Sach s : sachList) {
                    if (s.getMaSach() == sach.getMaSach()) {
                        spnSach.setSelection(sachList.indexOf(s));
                    }
                }
                List<ThanhVien> thanhVienList = thanhVienDAO.getAll();
                ThanhVien thanhVien = thanhVienDAO.getID(list.get(holder.getLayoutPosition()).getMaTV() + "");

                for (ThanhVien s : thanhVienList) {
                    if (s.getMaTV() == thanhVien.getMaTV()) {
                        spnThanhVien.setSelection(thanhVienList.indexOf(s));
                    }
                }

                spnSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        tienThue = new SachDAO(context).getAll().get(position).getGiaThue();
                        maSach = new SachDAO(context).getAll().get(position).getMaSach();
                        tvTienThue.setText("Giá thuê: " +  tienThue);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spnThanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maTV = new ThanhVienDAO(context).getAll().get(position).getMaTV();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                        if(checkBox.isChecked()) traSach =1;
                        phieuMuonDAO.update(new PhieuMuon(0, "0", maTV, maSach, tienThue, null, traSach), list.get(holder.getLayoutPosition()).getMaPM() + "");
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        try {
                            list = phieuMuonDAO.getAll();
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
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
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PhieuMuonViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaPhieuMuon;
        TextView tvThanhVien;
        TextView tvThuThu;
        TextView tvTenSach;
        TextView tvTienThue;
        TextView tvDaTraSach;
        TextView tvvNgayMuon;
        ImageView btnDelete;
        CardView layout;

        public PhieuMuonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPhieuMuon = itemView.findViewById(R.id.tv_ma_phieu);
            tvThanhVien = itemView.findViewById(R.id.tv_thanh_vien);
            tvThuThu = itemView.findViewById(R.id.tv_thu_thu);
            tvTenSach = itemView.findViewById(R.id.tv_ten_sach);
            tvTienThue = itemView.findViewById(R.id.edt_tien_thue);
            tvDaTraSach = itemView.findViewById(R.id.tv_da_tra_sach);
            tvvNgayMuon = itemView.findViewById(R.id.tv_ngay_muon);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
