package com.example.asm_duanmau.ui.quanlythanhvien;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm_duanmau.R;
import com.example.asm_duanmau.adapter.ThanhVienAdapter;
import com.example.asm_duanmau.dao.ThanhVienDAO;
import com.example.asm_duanmau.dao.ThuThuDAO;
import com.example.asm_duanmau.databinding.FragmentQuanLyThanhVienBinding;
import com.example.asm_duanmau.model.ThanhVien;
import com.example.asm_duanmau.model.ThuThu;

import java.util.List;

public class QuanLyThanhVienFragment extends Fragment {

    private FragmentQuanLyThanhVienBinding binding;
    private ThanhVienDAO thanhVienDAO;
    private ThanhVienAdapter thanhVienAdapter;
    List<ThanhVien> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuanLyThanhVienBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        thanhVienDAO = new ThanhVienDAO(getContext());
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_them_thanh_vien);

                Button btnAdd = dialog.findViewById(R.id.btn_them);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText edtName = dialog.findViewById(R.id.edt_ho_ten);
                        EditText edtAge = dialog.findViewById(R.id.edt_nam_sinh);

                        String name = edtName.getText().toString();
                        String age = edtAge.getText().toString();

                        if(!thanhVienAdapter.validate(name, age)){
                            Toast.makeText(getContext(), "Vui lòng kiểm tra thông tin và nhập lại", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        thanhVienDAO.add(new ThanhVien(0,name, age));
                        list = thanhVienDAO.getAll();

                        thanhVienAdapter = new ThanhVienAdapter(list, getContext());
                        binding.rcv.setAdapter(thanhVienAdapter);

                        dialog.dismiss();
                    }
                });

                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });


        list = thanhVienDAO.getAll();
        binding.rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        thanhVienAdapter = new ThanhVienAdapter(list, getContext());
        binding.rcv.setAdapter(thanhVienAdapter);
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}