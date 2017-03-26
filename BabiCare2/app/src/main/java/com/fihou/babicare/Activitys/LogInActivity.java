package com.fihou.babicare.Activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
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

import com.fihou.babicare.Data.Save;
import com.fihou.babicare.Data.TypeUser;
import com.fihou.babicare.Data.UserInfos;
import com.fihou.babicare.Network.ClientHandler;
import com.fihou.babicare.Network.ClientInitializer;
import com.fihou.babicare.Network.MessageKey;
import com.fihou.babicare.Network.NettyConnection;
import com.fihou.babicare.R;
import com.fihou.babicare.Service.StartService;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.UUID;

import cc.lison.pojo.EchoFile;
import cc.lison.pojo.EchoMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import static com.fihou.babicare.config.Config.host;
import static com.fihou.babicare.config.Config.port;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private TextView tvQuenMatkhau, tvDangky;
    ProgressDialog progress;
    StartService service;
    Activity activity;
    public static Channel channel;
    String line;
    NettyConnection connection;
    private Uri uri;
    private int dataLength = 1024;
    private int sumCountpackage = 0;
    public static boolean EPOLL = Epoll.isAvailable();
    NioEventLoopGroup group;
    ChannelFuture channelFuture;
    public static ProgressDialog dialog;
    private AVLoadingIndicatorView avi;
    public static String username_regis;
    public static String pass_regis;
    public static String email_regis;
    public static int idloainguoidung;
    public static ArrayList<TypeUser> listTypuser;
    private static boolean connected = false;
    RegisterActivity registerActivity;
    SharedPreferences sharedPreferences;
    UserInfos userInfos;
    String newpass;
    Intent login;
    UserInfosActivity userInfosActivity;

    public LogInActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_);
        initView();
        dialog = new ProgressDialog(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        btnLogin.setOnClickListener(this);
        tvDangky.setOnClickListener(this);
        edtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (edtUsername.getText().toString().length() > 0 && edtPassword.getText().toString().length() > 0) {
                        savedataSharedPreferences();
                        Login();
                    } else {
                        showErr("Mời nhập thông tin trước!");
                    }
                }
                return false;
            }
        });
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        getuser_pass();
    }

    private void checkConnect() {
        if (ClientHandler.isconnected) {
            connected = true;
        } else {
            connected = true;
        }
    }

    private void getuser_pass() {
        String username = sharedPreferences.getString("username", null);
        edtUsername.setText(username);
        String pass = sharedPreferences.getString("password", null);
        edtPassword.setText(pass);
    }

    private void showErr(String mss) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_log_in_), mss, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.DKGRAY);
        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    private void initView() {
        activity = this;
        connect(handler);
        edtUsername = (EditText) findViewById(R.id.edtusername);
        edtPassword = (EditText) findViewById(R.id.edtpassword);
        btnLogin = (Button) findViewById(R.id.btnlogin);
        tvQuenMatkhau = (TextView) findViewById(R.id.tvquenMatkhau);
        tvDangky = (TextView) findViewById(R.id.tvDangky);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        registerActivity = new RegisterActivity();
        userInfosActivity=new UserInfosActivity();
        login=new Intent(getApplicationContext(),LogInActivity.class);
    }

    public void connect(final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                try {
                    group = new NioEventLoopGroup();

                    Bootstrap bootstrap = new Bootstrap();
                    bootstrap.group(group);
                    bootstrap.channel(NioSocketChannel.class);
                    bootstrap.handler(new ClientInitializer(handler));
                    bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
                    bootstrap.option(ChannelOption.TCP_NODELAY, true);
                    channelFuture = bootstrap.connect(new InetSocketAddress(host, port));
                    channel = channelFuture.sync().channel();
                    channelFuture.addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            handler.obtainMessage(0x00).sendToTarget();
                            connected = true;
                        }
                    });

                    channel.closeFuture().sync();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private String Message(String json) {
        String Message = "";
        if (json != null && json.length() > 0) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Message = object.getString("to");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return Message;
    }

    public void getUserinfos(String json) {
        UserInfos userInfos = null;
        if (json != null && json.length() > 0) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    JSONObject rs = object.getJSONObject("data");
                    try {
                        userInfos = new UserInfos(rs.getInt("id"), rs.getString("hoten"), rs.getString("ngaysinh"), rs.getString("gioitinh"), rs.getString("diachi"), rs.getString("dienthoai"), rs.getString("email"), rs.getString("tentaikhoan"), rs.getString("matkhau"), rs.getInt("idloai"));
                    } catch (JSONException e) {
                        userInfos = new UserInfos(rs.getInt("id"), rs.getString("email"), rs.getString("tentaikhoan"), rs.getString("matkhau"), rs.getInt("idloai"));
                    }
                }
                Save.userInfos = userInfos;
                Log.i("USER INFO", "" + userInfos.getHoten());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private JSONArray toMessJSON(String mess) {
        JSONArray arr = new JSONArray();
        JSONObject oject = new JSONObject();
        try {
            oject.put("to", MessageKey.LOADTYPEUSER);
            arr.put(oject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arr;
    }
    private void dismisProgress() {
        if (progress != null && progress.isShowing() && !isFinishing()) {
            progress.dismiss();
        }
    }
    private void getType(String json) {
        ArrayList<TypeUser> listType = new ArrayList<>();
        TypeUser typeUser;
        if (json != null && json.length() > 0) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    JSONArray js = object.getJSONArray("data");
                    for (int j = 0; j < js.length(); j++) {
                        JSONObject item = js.getJSONObject(j);
                        typeUser = new TypeUser(item.getInt("id"), item.getString("tenloai"));
                        listType.add(typeUser);
                    }
                }
                Save.listtype = listType;
                Log.i("Save listtype", Save.listtype.get(0).getTenloai().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String m = msg.obj + "";
            switch (msg.what) {
                case 0x00:
                    //connected

                    String hello = "LOGIN";
                    EchoMessage em = new EchoMessage();
                    byte[] b = hello.getBytes();
                    em.setBytes(b);
                    em.setSumCountPackage(b.length);
                    em.setCountPackage(1);
                    em.setSend_time(System.currentTimeMillis());
//                    channel.writeAndFlush(hello);
                    loadLoaiND();
                    break;
                case 0x01:
                    //receive
//                    et_scroll.setText(et_scroll.getText() + m + "\r\n");
                    switch (Message(m)) {
                        case MessageKey.SUCCESS://Đăng nhập thành công
                            getUserinfos(m);
                            dialog.hide();
                            Intent home = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(home);
                            break;
                        case MessageKey.FAIL://Đăng nhập thất bại
                            dialog.hide();
                            showErr("Tài khoản hoặc mật khẩu không đúng!");
                            break;
                        case MessageKey.LOADTYPEUSER://nhận danh sách loại người dùng
                            getType(m);
                            break;
                        case MessageKey.SINGUP_SUCCESS://Đăng ký tài khoản thành công
                            Toast.makeText(getApplicationContext(), "Đăng ký tài khoản thành công!", Toast.LENGTH_SHORT).show();

                            break;
                        case MessageKey.SINGUP_FAIL://Đăng ký tài khoản thất bại
                            Toast.makeText(getApplicationContext(), "Đăng ký tài khoản thất bại!", Toast.LENGTH_SHORT).show();
                            break;
                        case MessageKey.GETSPLAN:
                            break;
                        case MessageKey.GETMENU:
                            break;
                        case MessageKey.UPDATEUSER:
                            userInfosActivity.dismissprogress();
                            Toast.makeText(getApplicationContext(), "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                            break;
                        case MessageKey.UPDATEFAIL:
                            userInfosActivity.dismissprogress();
                            Toast.makeText(getApplicationContext(), "Cập nhật thông tin thất bại!", Toast.LENGTH_SHORT).show();
                            break;
                        case MessageKey.UPDATEPASS:
                            Toast.makeText(getApplicationContext(), "Cập nhật mật khẩu thành công!", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(LogInActivity.this,LogInActivity.class));
                            break;
                        case MessageKey.UPDATEPASS_FAIL:
                            Toast.makeText(getApplicationContext(), "Cập nhật mật khẩu thất bại!", Toast.LENGTH_SHORT).show();
                            break;
                        case MessageKey.GETCHUONGTRINH:
                            break;
                    }
                    break;
                case 0x02:
                    //send complete
                    Log.i("SEND", "Gửi dữ liệu tới serrver thành công!");
                    break;
                case 0x03:
                    //send json to server
                    JSONArray data = UserLogin(edtUsername.getText().toString(), edtPassword.getText().toString());
                    if (data == null)
                        return;
                    channel.writeAndFlush(data.toString()).addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            handler.obtainMessage(0x02).sendToTarget();
                        }
                    });

                    break;
                case 0x04:
                    //send pic

                    try {

                        ContentResolver resolver = activity.getContentResolver();
                        InputStream reader = resolver.openInputStream(uri);
                        byte[] bytes = new byte[reader.available()];

                        reader.read(bytes);
                        reader.close();

                        //byte[] bytes=toByteArray(filePath);

                        if ((bytes.length % dataLength == 0))
                            sumCountpackage = bytes.length / dataLength;
                        else
                            sumCountpackage = (bytes.length / dataLength) + 1;
                        EchoFile msgFile = new EchoFile();
                        msgFile.setSumCountPackage(sumCountpackage);
                        //msgFile.setCountPackage(i);
                        msgFile.setCountPackage(1);
                        msgFile.setBytes(bytes);
                        //msgFile.setFile_md5("Iknowyournew.jpg");
                        msgFile.setFile_name(Build.MANUFACTURER + "-" + UUID.randomUUID() + ".jpg");
                        channel.writeAndFlush(msgFile);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException i) {
                        i.printStackTrace();
                    }

                    post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ContentResolver resolver = activity.getContentResolver();
                                InputStream reader = resolver.openInputStream(uri);
                                byte[] bytes = new byte[reader.available()];

                                reader.read(bytes);
                                reader.close();

                                channel.writeAndFlush("filelength:" + bytes.length + "\r\n");
                                channel.flush();
                                channel.read();

                                //final ByteBuf buff = Unpooled.copiedBuffer(bytes);
                                //channel.writeAndFlush(buff.readBytes(bytes));
                                channel.writeAndFlush(bytes);

                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                channel.flush();
                                channel.read();
                            }
                        }
                    });
                    break;
                case 0x05://Gửi dữ liệu đăng ký tài khoản
                    JSONArray signup = UserSignUp(username_regis, pass_regis, email_regis, idloainguoidung);
                    if (signup == null)
                        return;
                    channel.writeAndFlush(signup.toString()).addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            handler.obtainMessage(0x02).sendToTarget();
                        }
                    });
                    break;
                case 0x06://Gửi yêu cầu nhận danh sách loại người dùng
                    Log.i("GETTYPE", toMessJSON(MessageKey.LOADTYPEUSER).toString());
                    channel.writeAndFlush(toMessJSON(MessageKey.LOADTYPEUSER).toString()).addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            handler.obtainMessage(0x02).sendToTarget();
                        }
                    });
                    break;
                case 0x07://Gửi yêu cầu nhận chương trình học
                    Log.i("GETSPLAN", toMessJSON(MessageKey.GETSPLAN).toString());
                    channel.writeAndFlush(toMessJSON(MessageKey.GETSPLAN).toString()).addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            handler.obtainMessage(0x02).sendToTarget();
                        }
                    });
                    break;
                case 0x08://Gửi yêu cầu nhận lịch học
                    Log.i("GETMENU", toMessJSON(MessageKey.GETMENU).toString());
                    channel.writeAndFlush(toMessJSON(MessageKey.GETMENU).toString()).addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            handler.obtainMessage(0x02).sendToTarget();
                        }
                    });
                    break;
                case 0x09:
                    JSONArray update = updateUserinfo(userInfos);
                    if (update == null)
                        return;
                    channel.writeAndFlush(update.toString()).addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            handler.obtainMessage(0x02).sendToTarget();
                        }
                    });
                    break;
                case 0x10:
                    JSONArray updatepass = updateUserpasword(newpass);
                    if (updatepass == null)
                        return;
                    channel.writeAndFlush(updatepass.toString()).addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            handler.obtainMessage(0x02).sendToTarget();
                        }
                    });
                    break;
                default:
                    Toast.makeText(activity, "UNKNOWN MSG: " + m, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void loadLoaiND() {
        if (ClientHandler.isconnected) {
            handler.obtainMessage(0x06).sendToTarget();
        } else {
            showErr("Máy chủ tạm thời không thể kết nối!");
        }
    }

    private void getChuongtrinh() {
        if (ClientHandler.isconnected) {
            handler.obtainMessage(0x07).sendToTarget();
        } else {
            showErr("Máy chủ tạm thời không thể kết nối!");
        }
    }

    private void getLichhoc() {
        if (ClientHandler.isconnected) {
            handler.obtainMessage(0x08).sendToTarget();
        } else {
            showErr("Máy chủ tạm thời không thể kết nối!");
        }
    }

    private JSONArray UserLogin(String username, String password) {
        JSONArray arr = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            data.put("username", username);
            data.put("password", password);
            jsonObject.put("to", MessageKey.LOGIN);
            jsonObject.put("data", data);
            arr.put(jsonObject);
            Log.i("LOGIN", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arr;
    }

    private void Login() {
        dialog.show();
        if (ClientHandler.isconnected) {
            if (UserLogin(edtUsername.getText().toString(), edtPassword.getText().toString()) != null) {
                handler.obtainMessage(0x03).sendToTarget();
            }
        } else {
            showErr("Máy chủ tạm thời không thể kết nối!");
        }

    }

    private JSONArray UserSignUp(String username, String password, String email, int idloainguoidung) {
        JSONArray arr = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            data.put("username", username);
            data.put("password", password);
            data.put("email", email);
            data.put("idloainguoidung", idloainguoidung);
            jsonObject.put("to", MessageKey.SIGNUP);
            jsonObject.put("data", data);
            arr.put(jsonObject);
            Log.i("SIGNUP", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arr;
    }
    public  void jumtoLogin(Activity intent){
        Intent i=new Intent(intent,LogInActivity.class);
        startActivity(i);
    }
    public void updatePassword(String newpass){
        this.newpass=newpass;
        handler.obtainMessage(0x10).sendToTarget();
    }
    private JSONArray updateUserpasword(String newpass) {
        JSONArray arr = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            data.put("id", Save.userInfos.getId());
            data.put("hoten", Save.userInfos.getHoten());
            data.put("gioitinh", Save.userInfos.getGioitinh());
            data.put("diachi", Save.userInfos.getDiachi());
            data.put("dienthoai", Save.userInfos.getDienthoai());
            data.put("email", Save.userInfos.getEmail());
            data.put("ngaysinh", Save.userInfos.getNgaysinh());
            data.put("idloai", Save.userInfos.getIdloai());
            data.put("tentaikhoan",Save.userInfos.getTentaikhoan());
            data.put("matkhau",newpass);
            jsonObject.put("to", MessageKey.UPDATEPASS);
            jsonObject.put("data", data);
            arr.put(jsonObject);
            Log.i("UPDATEUSERPAS", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arr;
    }
    private JSONArray updateUserinfo(UserInfos userInfos) {
        JSONArray arr = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            data.put("id", userInfos.getId());
            data.put("hoten", userInfos.getHoten());
            data.put("gioitinh", userInfos.getGioitinh());
            data.put("diachi", userInfos.getDiachi());
            data.put("dienthoai", userInfos.getDienthoai());
            data.put("email", userInfos.getEmail());
            data.put("ngaysinh", userInfos.getNgaysinh());
            data.put("idloai", userInfos.getIdloai());
            data.put("tentaikhoan",Save.userInfos.getTentaikhoan());
            data.put("matkhau",Save.userInfos.getMatkhau());
            jsonObject.put("to", MessageKey.UPDATEUSER);
            jsonObject.put("data", data);
            arr.put(jsonObject);
            Log.i("UPDATEUSERINFO", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arr;
    }
    public void editUser(UserInfos userInfos) {
        this.userInfos=userInfos;
        handler.obtainMessage(0x09).sendToTarget();
    }

    public void SignUp(String username, String password, String email, int idloainguoidung) {

        if (UserSignUp(username, password, email, idloainguoidung) != null) {
            this.username_regis = username;
            this.pass_regis = password;
            this.email_regis = email;
            this.idloainguoidung = idloainguoidung;
            handler.obtainMessage(0x05).sendToTarget();
        }
    }

    private void savedataSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", edtUsername.getText().toString());
        editor.putString("password", edtPassword.getText().toString());
        editor.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnlogin:
                if (edtUsername.getText().toString().length() > 0 && edtPassword.getText().toString().length() > 0) {
                    savedataSharedPreferences();
                    Login();
                } else {
                    showErr("Mời nhập thông tin trước!");
                }

                break;
            case R.id.tvDangky:

                while (ClientHandler.getdata = false) {
                    dialog.show();
                }
                Intent register = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(register);
                break;
        }
    }
}
