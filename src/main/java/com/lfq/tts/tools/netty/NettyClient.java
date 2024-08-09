package com.lfq.tts.tools.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;

/**
 * @作者 lfq
 * @DATE 2024-08-02
 * current year
 **/
public class NettyClient {

    public static void main(String[] args) throws Exception {
        // 创建EventLoopGroup
         EventLoopGroup boosGroup = new NioEventLoopGroup();
        try {
            // 创建Bootstrap
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(boosGroup)
                    .remoteAddress("127.0.0.1", 9080)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new SimpleChannelInboundHandler<String>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                                    System.out.println("Received message: " + msg);
                                }
                            });
                        }
                    });
            ChannelFuture  f = bootstrap.connect().addListener((ChannelFuture futureListener) -> {
                if(futureListener.isSuccess()) {
                    System. out.println( "与服务端连接成功!");
                }
            });
            // 假设你有一个String需要发送
            String message = "Hello, World!";
            // 转换String为ByteBuf
            ByteBuf buf = Unpooled.copiedBuffer(message, Charset.defaultCharset());

            f.channel().writeAndFlush(buf).sync();
            f.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
        }
    }

}
