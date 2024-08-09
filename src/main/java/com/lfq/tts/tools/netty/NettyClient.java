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
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @作者 lfq
 * @DATE 2024-08-02
 * current year
 **/
public class NettyClient {

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });
//            ChannelFuture  f = b.connect(new InetSocketAddress("127.0.0.1", 9080)).addListener((ChannelFuture futureListener) -> {
//                if(futureListener.isSuccess()) {
//                    System. out.println( "与服务端连接成功!");
//                }
//            });
            ChannelFuture f = b.connect("127.0.0.1", 9080).sync();
            // 假设你有一个String需要发送
            String message = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><返回报文><报文内容><CODE>0000</CODE><MSG>成功</MSG></报文内容></返回报文>";
            // 转换String为ByteBuf
            ByteBuf buf = Unpooled.copiedBuffer(message, Charset.defaultCharset());
            f.channel().writeAndFlush(buf).sync();
            if(f.isSuccess()) {
                f.channel().closeFuture().sync();
                }
        } finally {
            group.shutdownGracefully();
        }
    }


    public static void main(String[] args) throws Exception {
        new NettyClient().start();
    }

}
