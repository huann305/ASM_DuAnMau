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
import com.example.asm_duanmau.model.LoaiSach;

import java.util.List;

public class LoaiSachSpinnerAdapter extends ArrayAdapter<LoaiSach> {
    private Context context;
    private List<LoaiSach> list;
    TextView tvMaLoaiSach, tvTenLoaiSach;

    public LoaiSachSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<LoaiSach> objects) {
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

        LoaiSach loaiSach = list.get(position);

        tvMaLoaiSach = view.findViewById(R.id.id);
        tvTenLoaiSach = view.findViewById(R.id.ten);

        tvMaLoaiSach.setText(loaiSach.getMaLoai() + ". ");
        tvTenLoaiSach.setText(loaiSach.getTenLoai() + "");

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner, parent, false);
        }

        LoaiSach loaiSach = list.get(position);

        tvMaLoaiSach = view.findViewById(R.id.id);
        tvTenLoaiSach = view.findViewById(R.id.ten);

        tvMaLoaiSach.setText(loaiSach.getMaLoai() + ". ");
        tvTenLoaiSach.setText(loaiSach.getTenLoai() + "");

        return view;
    }
}
