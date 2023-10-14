package com.example.asm_duanmau.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.asm_duanmau.MainActivity;
import com.example.asm_duanmau.dao.ThuThuDAO;
import com.example.asm_duanmau.databinding.ActivityDangNhapBinding;
import com.example.asm_duanmau.model.ThuThu;

import java.util.List;

public class DangNhap extends AppCompatActivity {
    ActivityDangNhapBinding binding;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDangNhapBinding.inflate(getLayoutInflater());
        sharedPreferences = getSharedPreferences("save_acc", Context.MODE_PRIVATE);

        binding.edtUsername.setText("admin");
        binding.edtPassword.setText("admin");

        ThuThuDAO thuThuDao = new ThuThuDAO(this);

        if (sharedPreferences.getBoolean("savePass", false)) {
            binding.edtUsername.setText(sharedPreferences.getString("username", ""));
            binding.edtPassword.setText(sharedPreferences.getString("pass", ""));
            binding.cbIsRemember.setChecked(true);
        }

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.edtUsername.getText().toString();
                String pass = binding.edtPassword.getText().toString();

                if(thuThuDao.checkLogin(username, pass)){
                    login(username, pass);
                }else{
                    Toast.makeText(DangNhap.this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });
        setContentView(binding.getRoot());
    }

    private void login(String username, String pass) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username", username);
        editor.putString("pass", pass);

        editor.putBoolean("savePass", binding.cbIsRemember.isChecked());

        editor.apply();

        startActivity(new Intent(DangNhap.this, MainActivity.class));
        finishAffinity();
    }
}