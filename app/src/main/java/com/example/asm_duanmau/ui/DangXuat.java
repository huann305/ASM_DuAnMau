package com.example.asm_duanmau.ui;

import static androidx.core.app.ActivityCompat.finishAffinity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asm_duanmau.MainActivity;
import com.example.asm_duanmau.R;
import com.example.asm_duanmau.ui.activity.DangNhap;

public class DangXuat extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        startActivity(new Intent(getContext(), DangNhap.class));
        finishAffinity(getActivity());
        return inflater.inflate(R.layout.fragment_dang_xuat, container, false);
    }
}