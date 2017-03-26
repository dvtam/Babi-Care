package com.fihou.babicare.Adapter;


import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fihou.babicare.Data.ChuongtrinhItem;
import com.fihou.babicare.R;

/***
 * ADAPTER
 */

public class ChuongtrinhAdapter extends BaseAdapter {
    private ArrayList<ChuongtrinhItem> listchuongtrinh;
    private Context mContext;

    public ChuongtrinhAdapter(ArrayList<ChuongtrinhItem> listchuongtrinh, Context mContext) {
        this.listchuongtrinh = listchuongtrinh;
        this.mContext = mContext;
    }

    public void setData(ArrayList<ChuongtrinhItem> obj) {
        this.listchuongtrinh = obj;
    }

    @Override
    public int getCount() {
        return listchuongtrinh.size();
    }

    @Override
    public ChuongtrinhItem getItem(int i) {
        return listchuongtrinh.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_chuongtrinh_item, viewGroup, false);
        }
        TextView tvtime= (TextView) view.findViewById(R.id.tvtime);
        TextView tvcontent= (TextView) view.findViewById(R.id.tvcontent);
        ChuongtrinhItem chuongtrinhItem=listchuongtrinh.get(i);
        tvtime.setText(chuongtrinhItem.getTime());
        tvcontent.setText(chuongtrinhItem.getContent());
        return view;
    }
}