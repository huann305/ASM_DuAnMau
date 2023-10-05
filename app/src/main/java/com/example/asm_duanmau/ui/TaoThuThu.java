package com.example.asm_duanmau.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm_duanmau.R;
import com.example.asm_duanmau.dao.ThuThuDAO;
import com.example.asm_duanmau.model.ThuThu;

import java.util.List;

public class TaoThuThu extends Fragment {
    List<ThuThu> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tao_thu_thu, container, false);

        ThuThuDAO thuThuDao = new ThuThuDAO(getContext());
        list = thuThuDao.getListThuThu();

        EditText edtUsername = view.findViewById(R.id.edt_username);
        EditText edtHoTen = view.findViewById(R.id.edt_ho_ten);
        Button btnTao = view.findViewById(R.id.btn_them);

        btnTao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThuThuDAO thuThuDao = new ThuThuDAO(getContext());
                if(!thuThuDao.addThuThu(new ThuThu(edtUsername.getText().toString(), edtHoTen.getText().toString()))){
                    Toast.makeText(getContext(), "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(getContext(), "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                    edtHoTen.setText("");
                    edtUsername.setText("");
                    edtUsername.setFocusable(false);
                    edtHoTen.setFocusable(false);
                }
                list = thuThuDao.getListThuThu();
            }
        });

        return view;
    }
}