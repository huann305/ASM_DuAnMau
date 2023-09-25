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
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_duanmau.R;
import com.example.asm_duanmau.adapter.ThanhVienAdapter;
import com.example.asm_duanmau.dao.DAO;
import com.example.asm_duanmau.databinding.FragmentDoiMatKhauBinding;
import com.example.asm_duanmau.databinding.FragmentQuanLyThanhVienBinding;
import com.example.asm_duanmau.model.ThuThu;

import java.util.List;

public class QuanLyThanhVienFragment extends Fragment {

    private FragmentQuanLyThanhVienBinding binding;
    ThanhVienAdapter thanhVienAdapter;
    List<ThuThu> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuanLyThanhVienBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        DAO dao = new DAO(getContext());
        list = dao.getListThuThu();

        list.remove(0);

        thanhVienAdapter = new ThanhVienAdapter(list, getContext());

        binding.rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcv.setAdapter(thanhVienAdapter);

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_them_thu_thu);

                EditText edtUsername = dialog.findViewById(R.id.edt_username);
                EditText edtHoTen = dialog.findViewById(R.id.edt_ho_ten);
                Button btnTao = dialog.findViewById(R.id.btn_tao);
                Button btnHuy = dialog.findViewById(R.id.btn_huy);

                btnTao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DAO dao = new DAO(getContext());
                        if(!dao.addThuThu(new ThuThu(edtUsername.getText().toString(), edtHoTen.getText().toString()))){
                            Toast.makeText(getContext(), "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        list = dao.getListThuThu();
                        list.remove(0);

                        thanhVienAdapter = new ThanhVienAdapter(list, getContext());

                        binding.rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.rcv.setAdapter(thanhVienAdapter);

                        dialog.dismiss();
                    }
                });

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}