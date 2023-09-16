package com.example.asm_duanmau.ui.quanlythanhvien;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asm_duanmau.R;
import com.example.asm_duanmau.databinding.FragmentDoiMatKhauBinding;
import com.example.asm_duanmau.databinding.FragmentQuanLyThanhVienBinding;

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