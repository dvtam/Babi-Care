package com.fihou.babicare.Activitys;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fihou.babicare.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtUsername, edtPass, edtrePass, edtemail;
    private TextView tvQuaylai;
    private Button btnSignin;
    private LogInActivity logInActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initview();
        btnSignin.setOnClickListener(this);
        tvQuaylai.setOnClickListener(this);
    }

    private void initview() {
        edtUsername = (EditText) findViewById(R.id.edtusername);
        edtPass = (EditText) findViewById(R.id.edtpassword);
        edtrePass = (EditText) findViewById(R.id.edtrepassword);
        edtemail = (EditText) findViewById(R.id.edtemail);
        btnSignin = (Button) findViewById(R.id.btnSignin);
        tvQuaylai = (TextView) findViewById(R.id.tvQuaylai);
        logInActivity = new LogInActivity();
    }
    private void showErr(String mss) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_main), mss, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.DKGRAY);
        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.YELLOW);
        snackbar.show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignin:
                if (edtUsername.getText().length() > 0 &&edtPass.getText().length() > 0 &&edtrePass.getText().length() > 0 &&edtemail.getText().length() > 0) {
                    if ((edtPass.getText().toString()).equals((edtrePass.getText().toString()))){
                        logInActivity.SignUp(edtUsername.getText().toString(), edtPass.getText().toString(), edtemail.getText().toString());
                    }else{
                        showErr("Mật khẩu nhập lại không khớp!");
                    }

                } else {
                    showErr("Mời nhập thông tin trước khi đăng ký!");
                }
                break;
            case R.id.tvQuaylai:
                finish();
                break;
        }
    }
}
