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
        gvChuongtrinh= (GridView) view.findViewById(R.id.lvChuongtrinh);
        gvThucdon= (GridView) view.findViewById(R.id.gvThucdon);


        gvChuongtrinh.setAdapter(adapter);
        return view;
    }
}
