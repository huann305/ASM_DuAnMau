package com.example.asm_duanmau.ui.top10;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asm_duanmau.R;
import com.example.asm_duanmau.adapter.Top10Adapter;
import com.example.asm_duanmau.dao.ThongKeDAO;
import com.example.asm_duanmau.databinding.FragmentTop10Binding;
import com.example.asm_duanmau.model.Top;

import java.util.List;

public class Top10Fragment extends Fragment {
    FragmentTop10Binding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTop10Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
        List<Top> list = thongKeDAO.getTop();

        binding.rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcv.setAdapter(new Top10Adapter(list, getContext()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}