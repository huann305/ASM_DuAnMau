package com.example.asm_duanmau.ui.quanlyloaisach;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.asm_duanmau.R;
import com.example.asm_duanmau.adapter.LoaiSachAdapter;
import com.example.asm_duanmau.dao.LoaiSachDAO;
import com.example.asm_duanmau.databinding.FragmentQuanLyLoaiSachBinding;
import com.example.asm_duanmau.model.LoaiSach;

import java.util.ArrayList;
import java.util.List;

public class QuanLyLoaiSachFragment extends Fragment {

    private FragmentQuanLyLoaiSachBinding binding;
    List<LoaiSach> list;
    LoaiSachDAO loaiSachDAO;

    LoaiSachAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        list = new ArrayList<>();
        loaiSachDAO = new LoaiSachDAO(getContext());
        list = loaiSachDAO.getAll();
        binding = FragmentQuanLyLoaiSachBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_them_loai);

                EditText edtName = dialog.findViewById(R.id.edt_ten_loai_sach);
                Button btnHuy = dialog.findViewById(R.id.btn_huy);
                Button btnThem = dialog.findViewById(R.id.btn_them);

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = edtName.getText().toString();
                        LoaiSach loaiSach = new LoaiSach();
                        loaiSach.setTenLoai(name);
                        loaiSachDAO.add(loaiSach);
                        list = loaiSachDAO.getAll();
                        adapter.notifyDataSetChanged();
                        binding.rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                        adapter = new LoaiSachAdapter(list, getContext());
                        binding.rcv.setAdapter(adapter);
                        dialog.dismiss();
                    }
                });

                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });

        binding.rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LoaiSachAdapter(list, getContext());
        binding.rcv.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}