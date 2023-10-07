package com.example.asm_duanmau.ui.doanhthu;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm_duanmau.dao.ThongKeDAO;
import com.example.asm_duanmau.databinding.FragmentDoanhThuBinding;

import java.util.Calendar;

public class DoanhThuFragment extends Fragment{
    FragmentDoanhThuBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDoanhThuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        
        binding.edtTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(binding.edtTuNgay);
            }
        });
        binding.edtDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(binding.edtDenNgay);
            }
        });

        binding.btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
                String tuNgay = binding.edtTuNgay.getText().toString();
                String denNgay = binding.edtDenNgay.getText().toString();

                if(thongKeDAO.getDoanhThu(tuNgay, denNgay) == -1){
                    binding.tvDoanhThu.setText(0 + "VNĐ");
                    return;
                }

                binding.tvDoanhThu.setText(thongKeDAO.getDoanhThu(tuNgay, denNgay) + "VNĐ");
            }
        });

        return root;
    }

    private void showDialog(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngay, thang;
                        if(dayOfMonth<10) {
                            ngay = "0" + dayOfMonth;
                        }else{
                            ngay = "" + dayOfMonth;
                        }
                        if(month + 1<10) {
                            thang = "0" + (month + 1);
                        }else{
                            thang = "" + (month + 1);
                        }

                        editText.setText(year + "/" + thang + "/" + ngay);
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}