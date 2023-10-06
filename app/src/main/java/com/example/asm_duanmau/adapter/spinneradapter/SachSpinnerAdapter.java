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
import com.example.asm_duanmau.model.Sach;

import java.util.Date;
import java.util.List;

public class SachSpinnerAdapter extends ArrayAdapter<Sach> {
    private Context context;
    private List<Sach> list;
    TextView tvMaSach, tvTenSach;

    public SachSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Sach> objects) {
        super(context, resource, objects);
        this.context = context;
        this.list = objects;
    }

    int maTV;
    int maSach;
    int tienThue;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_spinner, null);
        }

        Sach Sach = list.get(position);

        tvMaSach = view.findViewById(R.id.id);
        tvTenSach = view.findViewById(R.id.ten);

        tvMaSach.setText(Sach.getMaSach() + ". ");
        tvTenSach.setText(Sach.getTenSach() + "");

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner, parent, false);
        }

        Sach Sach = list.get(position);

        tvMaSach = view.findViewById(R.id.id);
        tvTenSach = view.findViewById(R.id.ten);

        tvMaSach.setText(Sach.getMaSach() + ". ");
        tvTenSach.setText(Sach.getTenSach() + "");

        return view;
    }
}
