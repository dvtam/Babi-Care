package com.fihou.babicare.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fihou.babicare.Data.TypeUser;
import com.fihou.babicare.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TAM on 21/03/2017.
 */

public class TypeUserAdapter extends ArrayAdapter<TypeUser> {
    private Context context;
    public Resources res;
    private ArrayList<TypeUser> listtype;
    TypeUser typeUser = null;
    LayoutInflater inflater;

    public TypeUserAdapter(Context context, int textViewResourceId, ArrayList<TypeUser> listtype, Resources resLocal) {
        super(context, textViewResourceId, listtype);
        this.context = context;
        this.listtype = listtype;
        this.res = resLocal;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.typeuser_item, parent, false);
        }
        typeUser = new TypeUser();
        typeUser = (TypeUser) listtype.get(position);
        TextView tenloai = (TextView) convertView.findViewById(R.id.tenloai);
        tenloai.setText(typeUser.getTenloai());
        return convertView;
    }
}
