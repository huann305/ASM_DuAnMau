package com.example.asm_duanmau.ui.quanlyphieumuon;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.asm_duanmau.R;
import com.example.asm_duanmau.adapter.PhieuMuonAdapter;
import com.example.asm_duanmau.adapter.spinneradapter.SachSpinnerAdapter;
import com.example.asm_duanmau.adapter.spinneradapter.ThanhVienSpinnerAdapter;
import com.example.asm_duanmau.dao.PhieuMuonDAO;
import com.example.asm_duanmau.dao.SachDAO;
import com.example.asm_duanmau.dao.ThanhVienDAO;
import com.example.asm_duanmau.dao.ThuThuDAO;
import com.example.asm_duanmau.databinding.FragmentQuanLyPhieuMuonBinding;
import com.example.asm_duanmau.model.PhieuMuon;
import com.example.asm_duanmau.model.ThuThu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class QuanLyPhieuMuonFragment extends Fragment {

    private FragmentQuanLyPhieuMuonBinding binding;
    PhieuMuonAdapter adapter;
    PhieuMuonDAO dao;
    List<PhieuMuon> list;
    int maPM;
    String maTT;
    int maTV;
    int maSach;
    int tienThue;
    Date ngay;
    int traSach = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentQuanLyPhieuMuonBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dao = new PhieuMuonDAO(getContext());

        getData();


        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_them_phieu_muon);

                TextView tvMaPhieu = dialog.findViewById(R.id.tv_ma_phieu);
                TextView tvThuThu = dialog.findViewById(R.id.tv_thu_thu);
                TextView tvNgayMuon = dialog.findViewById(R.id.tv_ngay_muon);
                Spinner spnThanhVien = dialog.findViewById(R.id.spn_thanh_vien);
                Spinner spnSach = dialog.findViewById(R.id.spn_ten_sach);
                TextView tvTienThue = dialog.findViewById(R.id.edt_tien_thue);
                Button btnHuy = dialog.findViewById(R.id.btn_huy);
                Button btnTao = dialog.findViewById(R.id.btn_them);

                if(list.size() !=0){
                    maPM = list.get(list.size() - 1).getMaPM() + 1;
                }else{
                    maPM =0;
                }

                btnTao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(getContext());
                        phieuMuonDAO.add(new PhieuMuon(maPM, maTT, maTV, maSach, tienThue, ngay, traSach));

                        getData();
                        dialog.dismiss();
                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                SharedPreferences sharedPreferences = getContext().getSharedPreferences("save_acc", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                ThuThuDAO thuThuDAO = new ThuThuDAO(getContext());
                ThuThu thuThu = thuThuDAO.getThuThuById(username);
                maTT = thuThu.getMaTT();
                Date today = new Date();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String formattedDate = sdf.format(today);
                ngay = today;

                tvNgayMuon.setText("Ngày mượn: " + formattedDate + "");
                tvThuThu.setText("Thủ thư: " + thuThu.getHoTen());
                tvMaPhieu.setText("Mã phiếu: " + maPM);
                spnThanhVien.setAdapter(new ThanhVienSpinnerAdapter(getContext(), R.layout.item_spinner, new ThanhVienDAO(getContext()).getAll()));
                spnSach.setAdapter(new SachSpinnerAdapter(getContext(), R.layout.item_spinner, new SachDAO(getContext()).getAll()));

                spnSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        tienThue = new SachDAO(getContext()).getAll().get(position).getGiaThue();
                        maSach = new SachDAO(getContext()).getAll().get(position).getMaSach();
                        tvTienThue.setText("Giá thuê: " +  tienThue);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spnThanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maTV = new ThanhVienDAO(getContext()).getAll().get(position).getMaTV();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });

        return root;
    }

    private void getData(){
        try {
            list = dao.getAll();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        adapter = new PhieuMuonAdapter(list, getContext());

        binding.rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcv.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}