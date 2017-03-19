package com.fihou.babicare.Network;

/**
 * Created by TAM on 18/03/2017.
 */

import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.fihou.babicare.Activitys.LogInActivity;
import com.fihou.babicare.config.Config;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.unix.Socket;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import static com.fihou.babicare.config.Config.host;
import static com.fihou.babicare.config.Config.port;

public class NettyConnection {
    public static boolean EPOLL = Epoll.isAvailable();
    EventLoopGroup eventLoopGroup;
    public static Channel channel;
    String line;
    BufferedReader reader;
    InputStreamReader in;


}
