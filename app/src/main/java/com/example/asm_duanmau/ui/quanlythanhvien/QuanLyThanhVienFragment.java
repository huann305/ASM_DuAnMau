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
import com.example.asm_duanmau.dao.ThuThuDAO;
import com.example.asm_duanmau.databinding.FragmentQuanLyThanhVienBinding;
import com.example.asm_duanmau.model.ThuThu;

import java.util.List;

public class QuanLyThanhVienFragment extends Fragment {

    private FragmentQuanLyThanhVienBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuanLyThanhVienBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}