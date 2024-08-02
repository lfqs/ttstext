package com.lfq.tts.tools.netty;

import com.lfq.tts.config.NettyConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.concurrent.ForkJoinPool;

/**
 * @作者 lfq
 * @DATE 2024-08-02
 * current year
 * 服务器端在启动的时候开放一个端口：19080
 * 客户端在启动的时候通过 ip 和 端口连上服务器端
 * 客户端和服务器端都通过 Channel 对象向彼此发送数据
 * 服务器和客户端都通过继承 ChannelInboundHandlerAdapter 类实现对消息的读取和回写等操作
 * 服务器和客户端都通过 StringDecoder 和 StringEncoder 实现对消息的解码和转码操作
 * 服务器和客户端启动的时候都会阻塞当前线程，因此需要在一个单独的线程中进行启动
 *
 * 消息发送的例子
 * 本例是一个 spring boot web 项目，项目占用了 8080 端口
 * 服务器端在启动的时候开放 19080 端口（注意不要和 web 端口冲突了）
 * 客户端在启动的时候连上服务器端
 * 通过 web api 向客户端发送数据，客户端再通过 Channel 对象向服务器端发送数据
 * 服务器接收到客户端数据后也通过 Channel 对象向客户端发送数据
 **/
@Slf4j
@Component
public class NettyServer {
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup();

    @Resource
    private NettyConfig nettyConfig;

    public void startServer(int port) {
        try {
            ServerBootstrap sbs = new ServerBootstrap().group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 添加一个字符串解码器，用于将接收到的ByteBuf转换成字符串
                            // 这里假设使用的是UTF-8字符集
                            ch.pipeline().addLast("decoder", new StringDecoder());
                            // 添加一个字符串编码器，用于将发送的字符串转换成ByteBuf
                            // 这样服务器发送字符串时，客户端可以直接接收到字符串
                            ch.pipeline().addLast("encoder", new StringEncoder());
                            // 添加自定义的ChannelInboundHandlerAdapter来处理业务逻辑
                            ch.pipeline().addLast("handler",new NettyServerHandler());
                        }

                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            // 绑定端口，开始接收进来的连接
            sbs.bind(port).addListener(future -> {
                log.info(String.format("服务器启动成功，并监听端口：%s ", port));
            });

        } catch (Exception e) {
            log.error("启动 netty 服务器端出现异常", e);
        }
    }

    // 服务器端启动，并绑定 19080 端口
    @PostConstruct
    public void init() {
        ForkJoinPool.commonPool().submit(() -> startServer(nettyConfig.getPort()));
    }

    @PreDestroy
    public void destroy() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
