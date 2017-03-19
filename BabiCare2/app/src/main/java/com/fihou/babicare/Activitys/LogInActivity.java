package com.fihou.babicare.Activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import cc.lison.pojo.EchoFile;
import cc.lison.pojo.EchoMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundInvoker;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import static com.fihou.babicare.config.Config.freeMem;
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
                        Login();
                    } else {
                        showErr("Mời nhập thông tin trước!");
                    }
                }
                return false;
            }
        });

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
                        }
                    });

                    channel.closeFuture().sync();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
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

                    break;
                case 0x01:
                    //receive
//                    et_scroll.setText(et_scroll.getText() + m + "\r\n");
                    switch (m) {
                        case MessageKey.SUCCESS:
                            avi.hide();
                            Intent home = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(home);
                            break;
                        case MessageKey.FAIL:
                            avi.hide();
                            showErr("Tài khoản hoặc mật khẩu không đúng!");
                            break;
                    }
                    break;
                case 0x02:
                    //send complete
                    Log.i("SEND", "Username password");
                    break;
                case 0x03:
                    //send
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
                case 0x05:
//                    JSONArray signup = UserSignUp();
//                    if (data == null)
//                        return;
//                    channel.writeAndFlush(data.toString()).addListener(new ChannelFutureListener() {
//                        @Override
//                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                            handler.obtainMessage(0x02).sendToTarget();
//                        }
//                    });
                    break;
                default:
                    Toast.makeText(activity, "UNKNOWN MSG: " + m, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

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
        avi.show();
        if (ClientHandler.isconnected) {
            if (UserLogin(edtUsername.getText().toString(), edtPassword.getText().toString()) != null) {
                handler.obtainMessage(0x03).sendToTarget();
            }
        } else {
            showErr("Máy chủ tạm thời không thể kết nối!");
        }

    }

    private JSONArray UserSignUp(String username, String password, String email) {
        JSONArray arr = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            data.put("username", username);
            data.put("password", password);
            data.put("email", email);
            jsonObject.put("to", MessageKey.SIGNUP);
            jsonObject.put("data", data);
            arr.put(jsonObject);
            Log.i("SIGNUP", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arr;
    }

    public void SignUp(String username, String password, String email) {

        if (UserSignUp(username, password, email) != null) {
            handler.obtainMessage(0x03).sendToTarget();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnlogin:
                if (edtUsername.getText().toString().length() > 0 && edtPassword.getText().toString().length() > 0) {
                    Login();
                } else {
                    showErr("Mời nhập thông tin trước!");
                }

                break;
            case R.id.tvDangky:
                Intent register = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(register);
                break;
        }
    }
}
