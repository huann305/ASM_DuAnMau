package com.example.asm_duanmau.ui.quanlysach;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.asm_duanmau.R;
import com.example.asm_duanmau.adapter.spinneradapter.LoaiSachSpinnerAdapter;
import com.example.asm_duanmau.adapter.SachAdapter;
import com.example.asm_duanmau.dao.LoaiSachDAO;
import com.example.asm_duanmau.dao.SachDAO;
import com.example.asm_duanmau.databinding.FragmentQuanLySachBinding;
import com.example.asm_duanmau.model.LoaiSach;
import com.example.asm_duanmau.model.Sach;

import java.util.Comparator;
import java.util.List;

public class QuanLySachFragment extends Fragment {

    private FragmentQuanLySachBinding binding;
    List<Sach> list;
    List<Sach> listSearch;
    List<LoaiSach> listLS;
    SachDAO sachDAO;
    LoaiSachDAO loaiSachDAO;
    SachAdapter adapter;
    private String tenSach;
    private String giaThue;
    private int maLoai;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentQuanLySachBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sachDAO = new SachDAO(getContext());
        loaiSachDAO = new LoaiSachDAO(getContext());
        list = sachDAO.getAll();
        listSearch = sachDAO.getAll();
        listLS = loaiSachDAO.getAll();

        binding.btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.sort(new Comparator<Sach>() {
                    @Override
                    public int compare(Sach o1, Sach o2) {
                        return o1.getGiaThue()<o2.getGiaThue() ? -1 : 1;
                    }
                });

                binding.rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new SachAdapter(list, getContext());
                binding.rcv.setAdapter(adapter);
            }
        });
        binding.btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.sort(new Comparator<Sach>() {
                    @Override
                    public int compare(Sach o1, Sach o2) {
                        return o1.getGiaThue()>o2.getGiaThue() ? -1 : 1;
                    }
                });

                binding.rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new SachAdapter(list, getContext());
                binding.rcv.setAdapter(adapter);
            }
        });


        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                list = sachDAO.getAll();
                listSearch = sachDAO.getAll();

                list.clear();
                for(Sach sach:listSearch){
                    if(sach.getTenSach().contains(s)){
                        list.add(sach);

                    }
                }
                binding.rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new SachAdapter(list, getContext());
                binding.rcv.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_them_sach);

                Spinner spinner = dialog.findViewById(R.id.spn_loai_sach);
                EditText edtTenSach = dialog.findViewById(R.id.edt_ten_sach);
                EditText edtTienThue = dialog.findViewById(R.id.edt_tien_thue);
                EditText edtNamXuatBan = dialog.findViewById(R.id.edt_nam_xuat_ban);
                Button btnHuy = dialog.findViewById(R.id.btn_huy);
                Button btnThem = dialog.findViewById(R.id.btn_them);

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                spinner.setAdapter(new LoaiSachSpinnerAdapter(getContext(), R.layout.item_spinner, listLS));
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maLoai = listLS.get(position).getMaLoai();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String namXuatBan;
                        tenSach = edtTenSach.getText().toString();
                        giaThue = edtTienThue.getText().toString();
                        namXuatBan = edtNamXuatBan.getText().toString();
                        if(!adapter.validate(tenSach, giaThue, namXuatBan)){
                            Toast.makeText(getContext(), "Vui lòng kiểm tra dữ liệu", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        sachDAO.add(new Sach(0, tenSach, Integer.parseInt(giaThue), maLoai, Integer.parseInt(namXuatBan)));

                        list = sachDAO.getAll();
                        adapter = new SachAdapter(list, getContext());
                        binding.rcv.setAdapter(adapter);
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

        binding.rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SachAdapter(list, getContext());
        binding.rcv.setAdapter(adapter);
        return root;
    }

    private void getData(){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}