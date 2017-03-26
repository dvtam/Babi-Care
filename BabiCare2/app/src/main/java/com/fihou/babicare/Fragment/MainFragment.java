package com.fihou.babicare.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.fihou.babicare.Adapter.ChuongtrinhAdapter;
import com.fihou.babicare.Data.ChuongtrinhItem;
import com.fihou.babicare.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private GridView gvChuongtrinh, gvThucdon;
    ArrayList<ChuongtrinhItem> listct=new ArrayList<>();
    ChuongtrinhAdapter adapter;
    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        gvChuongtrinh= (GridView) view.findViewById(R.id.gvChuongtrinh);
        gvThucdon= (GridView) view.findViewById(R.id.gvThucdon);
        listct.add(new ChuongtrinhItem("7h-8h","Đón trẻ tới trường + ăn sáng"));
        listct.add(new ChuongtrinhItem("7h-8h","Đón trẻ tới trường + ăn sáng"));
        listct.add(new ChuongtrinhItem("7h-8h","Đón trẻ tới trường + ăn sáng"));
        listct.add(new ChuongtrinhItem("7h-8h","Đón trẻ tới trường + ăn sáng"));
        listct.add(new ChuongtrinhItem("7h-8h","Đón trẻ tới trường + ăn sáng"));
        adapter=new ChuongtrinhAdapter(listct,getContext());
        gvChuongtrinh.setAdapter(adapter);
        return view;
    }
}
