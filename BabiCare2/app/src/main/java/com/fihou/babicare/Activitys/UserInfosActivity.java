package com.fihou.babicare.Activitys;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fihou.babicare.Adapter.TypeUserAdapter;
import com.fihou.babicare.Data.Save;
import com.fihou.babicare.Data.TypeUser;
import com.fihou.babicare.Data.UserInfos;
import com.fihou.babicare.R;

import java.util.ArrayList;

public class UserInfosActivity extends AppCompatActivity implements View.OnClickListener {
    ViewGroup frmUserInfo, frmEditUserInfo;
    FloatingActionButton fab;
    private static int isshowInfo;
    private LogInActivity logInActivity;
    TypeUserAdapter adapter;
    public static ProgressDialog dialog;
    ArrayList<TypeUser> listtype = new ArrayList<>();
    Spinner sploainguoidung;
    private static int idloainguoidunng;
    TextView tvuserfullname, tvusergender, tvuserbirthday, tvuseraddress, tvuserloai, tvuseremail, tvuserphone;
    EditText edtfullname, edtbirthday, edtaddress, edtemail, edtphone;
    RadioButton rdmale, rdfemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Save.userInfos.getTentaikhoan());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        isshowInfo = 1;
        settab();
        fab.setOnClickListener(this);
        adapter = new TypeUserAdapter(this, R.layout.activity_register, getListtype(), getResources());
        adapter.notifyDataSetChanged();
        sploainguoidung.setAdapter(adapter);
        sploainguoidung.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idloainguoidunng = listtype.get((int) adapterView.getItemIdAtPosition(i)).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(UserInfosActivity.this, "Bạn có muốn chọn lại loại người dùng không?", Toast.LENGTH_SHORT).show();
            }
        });
        setUserInfo();
    }

    private ArrayList<TypeUser> getListtype() {
        listtype = Save.listtype;
        return listtype;
    }

    public void dismissprogress() {
        if (dialog != null && dialog.isShowing() && !isFinishing()) {
            dialog.dismiss();
        }
    }

    private void initView() {
        dialog = new ProgressDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        logInActivity = new LogInActivity();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        frmUserInfo = (ViewGroup) findViewById(R.id.frm_userinfo);
        frmEditUserInfo = (ViewGroup) findViewById(R.id.frm_edituserinfo);
        sploainguoidung = (Spinner) findViewById(R.id.spuserloai);
        tvuserfullname = (TextView) findViewById(R.id.tvuserfullname);
        tvusergender = (TextView) findViewById(R.id.tvusergender);
        tvuserbirthday = (TextView) findViewById(R.id.tvuserbirthday);
        tvuseraddress = (TextView) findViewById(R.id.tvuseraddress);
        tvuseremail = (TextView) findViewById(R.id.tvuseremail);
        tvuserloai = (TextView) findViewById(R.id.tvuserloai);
        tvuserphone = (TextView) findViewById(R.id.tvuserphone);

        edtfullname = (EditText) findViewById(R.id.edtuserfullname);
        edtbirthday = (EditText) findViewById(R.id.edtuserbirthday);
        edtaddress = (EditText) findViewById(R.id.edtuseraddress);
        edtemail = (EditText) findViewById(R.id.edtuseremail);
        edtphone = (EditText) findViewById(R.id.edtuserphone);
        rdmale = (RadioButton) findViewById(R.id.rdusermale);
        rdfemale = (RadioButton) findViewById(R.id.rduserfemale);

    }

    private void setUserInfo() {
        tvuserfullname.setText(Save.userInfos.getHoten());
        tvusergender.setText(Save.userInfos.getGioitinh());
        tvuserbirthday.setText(Save.userInfos.getNgaysinh());
        tvuseraddress.setText(Save.userInfos.getDiachi());
        tvuseremail.setText(Save.userInfos.getEmail());
        for (TypeUser item : Save.listtype) {
            if (Save.userInfos.getIdloai() == item.getId()) {
                tvuserloai.setText(item.getTenloai());
                break;
            }
        }
//        tvuserloai.setText("" + Save.userInfos.getIdloai());
        tvuserphone.setText(Save.userInfos.getDienthoai());


        //////////set gia tri de edit///////////
        edtfullname.setText(Save.userInfos.getHoten());
        if ((Save.userInfos.getGioitinh()).equals("Nam")) {
            rdmale.setChecked(true);
        } else {
            rdfemale.setChecked(true);
        }
        edtbirthday.setText(Save.userInfos.getNgaysinh());
        edtaddress.setText(Save.userInfos.getDiachi());
        edtemail.setText(Save.userInfos.getEmail());
        sploainguoidung.setSelection(Save.userInfos.getIdloai() - 1);
        edtphone.setText(Save.userInfos.getDienthoai());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Nếu nhấn vào nút back thì quay lại màn hình trước
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void onBack() {
        if (isshowInfo == 1) {
            onBackPressed();
        } else if (isshowInfo == 2) {
            frmUserInfo.setVisibility(View.VISIBLE);
            frmEditUserInfo.setVisibility(View.INVISIBLE);
            fab.setImageResource(R.drawable.ic_mode_edit_white_36dp);
        } else if (isshowInfo == 3) {
            onBackPressed();
        }
    }

    private void settab() {
        if (isshowInfo == 1) {
            frmUserInfo.setVisibility(View.VISIBLE);
            frmEditUserInfo.setVisibility(View.INVISIBLE);
            fab.setImageResource(R.drawable.ic_mode_edit_white_36dp);
        } else if (isshowInfo == 2) {
            frmUserInfo.setVisibility(View.INVISIBLE);
            frmEditUserInfo.setVisibility(View.VISIBLE);
            fab.setImageResource(R.drawable.icon_save);
        } else {
            saveUser();
            frmUserInfo.setVisibility(View.VISIBLE);
            frmEditUserInfo.setVisibility(View.INVISIBLE);
            fab.setImageResource(R.drawable.ic_mode_edit_white_36dp);
        }
    }

    private void saveUser() {
        dialog.show();
        UserInfos userInfos = new UserInfos();
        userInfos.setId(Save.userInfos.getId());
        userInfos.setHoten(edtfullname.getText().toString());
        if (rdmale.isChecked()) {
            userInfos.setGioitinh("Nam");
        } else {
            userInfos.setGioitinh("Nữ");
        }
        userInfos.setNgaysinh(edtbirthday.getText().toString());
        userInfos.setDiachi(edtaddress.getText().toString());
        userInfos.setEmail(edtemail.getText().toString());
        userInfos.setDienthoai(edtphone.getText().toString());
        userInfos.setIdloai(idloainguoidunng);
        userInfos.setTentaikhoan(Save.userInfos.getTentaikhoan());
        userInfos.setMatkhau(Save.userInfos.getMatkhau());
        Save.userInfos = userInfos;
        logInActivity.editUser(userInfos);
    }

    private void tabFab() {
        switch (isshowInfo) {
            case 1:
                isshowInfo = 2;
                break;
            case 2:
                isshowInfo = 3;
                break;
            case 3:
                isshowInfo = 1;
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                tabFab();
                settab();
                break;

        }
    }
}
