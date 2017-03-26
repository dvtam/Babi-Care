package com.fihou.babicare.Network;

/**
 * Created by TAM on 17/03/2017.
 */

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<String> {
    private Channel channel;
    final Handler handler;
    public static boolean isconnected=false;
    public static boolean getdata=false;
    public ClientHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.channel = ctx.channel();
        Log.i("connected to server: ", "" + ctx.channel().remoteAddress());
        Message message = handler.obtainMessage(0x01);
        message.obj = "Cennected";
        message.sendToTarget();
        isconnected=true;
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Message message = handler.obtainMessage(0x01);
        message.obj = "Disconnect from server";
        message.sendToTarget();
        isconnected=false;
        super.channelInactive(ctx);
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        getdata=true;
        Message message = handler.obtainMessage(0x01);
        message.obj = msg;
        message.sendToTarget();
        Log.i("#####", msg);

    }
}
