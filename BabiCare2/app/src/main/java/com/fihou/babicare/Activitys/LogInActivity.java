package com.fihou.babicare.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fihou.babicare.R;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private TextView tvQuenMatkhau, tvDangky;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_);
        initView();
        btnLogin.setOnClickListener(this);
        tvDangky.setOnClickListener(this);
        edtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    loginApp(textView);
                }
                return false;
            }
        });
    }

    private void initView() {
        edtUsername = (EditText) findViewById(R.id.edtusername);
        edtPassword = (EditText) findViewById(R.id.edtpassword);
        btnLogin = (Button) findViewById(R.id.btnlogin);
        tvQuenMatkhau = (TextView) findViewById(R.id.tvquenMatkhau);
        tvDangky = (TextView) findViewById(R.id.tvDangky);
    }
    private void showProgressBar() {
        progress = new ProgressDialog(LogInActivity.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Đang đăng nhập...");

        progress.show();
    }

    private void dismisProgress() {
        if (progress != null && progress.isShowing() && !isFinishing()) {
            progress.dismiss();
        }
    }
    private void loginApp(View view) {
        String txtuser=edtUsername.getText().toString();
        String txtpass=edtPassword.getText().toString();
        if (edtUsername.getText().length() > 0 && edtPassword.getText().length() > 0) {
            showProgressBar();
            Intent main=new Intent(LogInActivity.this,MainActivity.class);
            startActivity(main);
            dismisProgress();

        } else {
            Snackbar.make(view, "Mời nhập tên đăng nhập và mật khẩu", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnlogin:
                loginApp(view);
                break;
            case R.id.tvDangky:
                Intent main = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(main);
                break;
        }
    }
}
