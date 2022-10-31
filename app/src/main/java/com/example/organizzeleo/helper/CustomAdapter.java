package com.example.organizzeleo.helper;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.organizzeleo.R;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    public CustomAdapter(Context context, ArrayList<ItemCustomSpinner> customSpinnerList) {
        super(context, 0, customSpinnerList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_layout, parent, false);
        }
        ItemCustomSpinner itens = (ItemCustomSpinner) getItem(position);
        ImageView spinnerImage = convertView.findViewById(R.id.image_custom);
        TextView spinnerText = convertView.findViewById(R.id.text_custom);
        if (itens != null) {
            spinnerImage.setImageResource(itens.getSpinnerImagem());
            spinnerText.setText(itens.getSpinnerString());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_dropdown_layout, parent, false);
        }
        ItemCustomSpinner itens = (ItemCustomSpinner) getItem(position);
        ImageView spinnerImageDropDown = convertView.findViewById(R.id.dropdown_image_custom);
        TextView spinnerTextDropdown = convertView.findViewById(R.id.dropdown_text_custom);
        if (itens != null) {
            spinnerImageDropDown.setImageResource(itens.getSpinnerImagem());
            spinnerTextDropdown.setText(itens.getSpinnerString());
        }
        return convertView;
    }
}
