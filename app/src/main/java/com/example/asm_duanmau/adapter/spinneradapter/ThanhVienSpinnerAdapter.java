package com.example.asm_duanmau.adapter.spinneradapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.asm_duanmau.R;
import com.example.asm_duanmau.model.ThanhVien;

import java.util.List;

public class ThanhVienSpinnerAdapter extends ArrayAdapter<ThanhVien> {
    private Context context;
    private List<ThanhVien> list;
    TextView tvMaThanhVien, tvTenThanhVien;

    public ThanhVienSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<ThanhVien> objects) {
        super(context, resource, objects);
        this.context = context;
        this.list = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_spinner, null);
        }

        ThanhVien thanhVien = list.get(position);

        tvMaThanhVien = view.findViewById(R.id.id);
        tvTenThanhVien = view.findViewById(R.id.ten);

        tvMaThanhVien.setText(thanhVien.getMaTV() + ". ");
        tvTenThanhVien.setText(thanhVien.getHoTen() + "");

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner, parent, false);
        }

        ThanhVien thanhVien = list.get(position);

        tvMaThanhVien = view.findViewById(R.id.id);
        tvTenThanhVien = view.findViewById(R.id.ten);

        tvMaThanhVien.setText(thanhVien.getMaTV() + ". ");
        tvTenThanhVien.setText(thanhVien.getHoTen() + "");

        return view;
    }
}
