package com.example.asm_duanmau.ui.doimatkhau;

import static androidx.core.app.ActivityCompat.finishAffinity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asm_duanmau.dao.DAO;
import com.example.asm_duanmau.databinding.FragmentDoiMatKhauBinding;
import com.example.asm_duanmau.model.ThuThu;
import com.example.asm_duanmau.ui.activity.DangNhap;

public class DoiMatKhauFragment extends Fragment {

    private FragmentDoiMatKhauBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDoiMatKhauBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newPass = binding.edtNewPass.getText().toString();
                String confirm = binding.edtConfirmPass.getText().toString();
                String oldPass = binding.edtOldPass.getText().toString();

                if(newPass.trim().equals("") || oldPass.trim().equals("") || confirm.trim().equals("")){
                    Toast.makeText(getContext(), "Không được nhập khoảng trắng hoặc để trống", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(newPass.length() < 6){
                    Toast.makeText(getContext(), "Mật khẩu phải lớn hơn 6 kí tự", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(newPass.equals(oldPass)){
                    Toast.makeText(getContext(), "Mật khẩu mới không được giống mật khẩu cũ", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("save_acc", Context.MODE_PRIVATE);

                if(oldPass.equals(sharedPreferences.getString("pass", ""))){
                    if(newPass.equals(confirm)){
                        DAO dao = new DAO(getContext());
                        dao.updateThuThu(new ThuThu("", "", newPass, ""), sharedPreferences.getString("username", ""));
                        Toast.makeText(getContext(), "Đổi mật khẩu thành công, đăng nhập lại để tiếp tục", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getContext(), DangNhap.class));
                        finishAffinity(getActivity());
                    }else{
                        Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
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