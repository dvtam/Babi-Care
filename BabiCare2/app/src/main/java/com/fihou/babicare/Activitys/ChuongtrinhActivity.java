package com.fihou.babicare.Activitys;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fihou.babicare.Data.ChitietChuongtrinh;
import com.fihou.babicare.Data.Save;
import com.fihou.babicare.R;

public class ChuongtrinhActivity extends AppCompatActivity {
    private TextView tvnoidung, tvtitle, tvchude;
    ChitietChuongtrinh chitietChuongtrinh=new ChitietChuongtrinh();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuongtrinh);
        initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int id = getIntent().getExtras().getInt("id");
        chitietChuongtrinh=LogInActivity.listchitiet.get(id);
        getSupportActionBar().setTitle(chitietChuongtrinh.getThoigian() + " - Thứ " + chitietChuongtrinh.getThu());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        tvnoidung.setText(chitietChuongtrinh.getNoidung());
        tvtitle.setText(chitietChuongtrinh.getTieude());
        tvchude.setText(chitietChuongtrinh.getChude());
    }

    private void initView() {
        tvnoidung = (TextView) findViewById(R.id.tvnoidung);
        tvtitle = (TextView) findViewById(R.id.tvtitle);
        tvchude = (TextView) findViewById(R.id.tvchude);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Nếu nhấn vào nút back thì quay lại màn hình trước
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
