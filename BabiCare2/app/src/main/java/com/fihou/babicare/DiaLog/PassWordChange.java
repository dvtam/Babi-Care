package com.fihou.babicare.DiaLog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fihou.babicare.Activitys.LogInActivity;
import com.fihou.babicare.Activitys.MainActivity;
import com.fihou.babicare.Data.UserInfos;
import com.fihou.babicare.R;

/**
 * Created by TAM on 25/03/2017.
 */

public class PassWordChange {
    EditText edtoldpass, edtnewpass, edtrenewpass;
    Button btnchangepass, btncancel;
    UserInfos userInfos;
    private String oldpass;
    private String newpass;
    private String renewpass;
    LogInActivity logInActivity = new LogInActivity();


    public void showDialog(final Activity activity, UserInfos userInfos) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.passwordchange_dialog);
        this.userInfos = userInfos;
//        logInActivity.jumtoLogin(new Intent(activity,LogInActivity.class));
        // Định dạng chiều cao và chiều rộng cho dialog
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        // căn giữa các đối tượng
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);

        edtoldpass = (EditText) dialog.findViewById(R.id.edtoldpass);
        edtnewpass = (EditText) dialog.findViewById(R.id.edtnewpass);
        edtrenewpass = (EditText) dialog.findViewById(R.id.edtrepassword);
        btnchangepass = (Button) dialog.findViewById(R.id.btnchangepass);
        btncancel = (Button) dialog.findViewById(R.id.btncancel);

        oldpass = edtoldpass.getText().toString();
        newpass = edtnewpass.getText().toString();
        renewpass = edtrenewpass.getText().toString();
        btnchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtoldpass.getText().length()>0&&edtnewpass.getText().length()>0&&edtrenewpass.getText().length()>0) {
                    if ((edtnewpass.getText().toString()).equals(edtrenewpass.getText().toString())) {
                        logInActivity.updatePassword(edtnewpass.getText().toString());
                    }else{
                        Toast.makeText(activity, "Mật khẩu nhập lại không khớp!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity, "Mời nhập đủ thông tin trước!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
