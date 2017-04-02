package com.fihou.babicare.Adapter;


import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fihou.babicare.Data.ChitietChuongtrinh;
import com.fihou.babicare.Data.ChuongtrinhItem;
import com.fihou.babicare.R;

/***
 * ADAPTER
 */

public class ChuongtrinhAdapter extends BaseAdapter {
    private ArrayList<ChitietChuongtrinh> listchitietchuongtrinh;
    private Context mContext;

    public ChuongtrinhAdapter(ArrayList<ChitietChuongtrinh> listchitietchuongtrinh, Context mContext) {
        this.listchitietchuongtrinh = listchitietchuongtrinh;
        this.mContext = mContext;
    }

    public void setData(ArrayList<ChitietChuongtrinh> obj) {
        this.listchitietchuongtrinh = obj;
    }

    @Override
    public int getCount() {
        return listchitietchuongtrinh.size();
    }

    @Override
    public ChitietChuongtrinh getItem(int i) {
        return listchitietchuongtrinh.get(i);
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
        RelativeLayout lnitem= (RelativeLayout) view.findViewById(R.id.lnitem);
        ChitietChuongtrinh chitietChuongtrinh=listchitietchuongtrinh.get(i);
        tvtime.setText(chitietChuongtrinh.getThoigian());
        tvcontent.setText(chitietChuongtrinh.getNoidung());
//        Random r=new Random();
//        int[] arrcolor={R.color.colorAccent,R.color.forget_pass,R.color.green,R.color.blue,R.color.green2,R.color.yellow};
//        int position=r.nextInt(arrcolor.length);
//        lnitem.setBackgroundColor(arrcolor[position]);
        return view;
    }
}