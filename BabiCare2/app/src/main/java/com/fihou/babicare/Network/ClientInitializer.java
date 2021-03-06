package com.fihou.babicare.Network;

import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;

import java.nio.charset.StandardCharsets;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created by Lison on 5/6/2016.
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {

    final Handler handler;

    public ClientInitializer(Handler handler) {
        this.handler = handler;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        ch.pipeline().addLast(new StringDecoder(StandardCharsets.UTF_8));
        ch.pipeline().addLast(new StringEncoder(StandardCharsets.UTF_8));

        pipeline.addLast(new ClientHandler(handler));
    }
}
