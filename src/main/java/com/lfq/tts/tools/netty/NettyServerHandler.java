package com.lfq.tts.tools.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @作者 lfq
 * @DATE 2024-08-02
 * current year
 **/
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    // 服务器端读取到 客户端发送过来的数据，然后通过 Channel 回写数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info(String.format("服务器端读取到从客户端:%s 发送过来的数据：%s", ctx.channel().remoteAddress(), msg.toString()));
        ctx.channel().writeAndFlush(msg.toString());
    }

    // 捕获到异常的处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}