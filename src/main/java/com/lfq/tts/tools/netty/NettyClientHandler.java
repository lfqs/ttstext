package com.lfq.tts.tools.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @作者 lfq
 * @DATE 2024-08-05
 * current year
 **/
@Slf4j
@ChannelHandler.Sharable
@Component
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    //定义一个channle 组，管理所有的channel
    //GlobalEventExecutor.INSTANCE) 是全局的事件执行器，是一个单例
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 读取到发来的数据数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //msg为接收到的客户端传递的数据   个人这边直接传的json 数据
        ByteBuf readMessage= (ByteBuf) msg;
        //解析客户端json 数据
        log.info("===============================================");
        log.info("客户端接收到的数据"+readMessage.toString(StandardCharsets.UTF_8));
        log.info("===============================================");
//        ctx.channel().closeFuture().sync();
    }

}
