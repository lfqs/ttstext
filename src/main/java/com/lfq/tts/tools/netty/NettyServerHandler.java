package com.lfq.tts.tools.netty;

import cn.hutool.json.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * @作者 lfq
 * @DATE 2024-08-05
 * current year
 **/
@Slf4j
@ChannelHandler.Sharable
@Component
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //msg为接收到的客户端传递的数据   个人这边直接传的json 数据
        ByteBuf readMessage= (ByteBuf) msg;
        //解析客户端json 数据
        log.info("接收到的数据"+readMessage.toString(CharsetUtil.UTF_8));
        //获取客户端的请求地址  取到的值为客户端的 ip+端口号
        String url=ctx.channel().remoteAddress().toString();//设备请求地址（个人将设备的请求地址当作 map 的key
        log.info("客户端的请求地址"+url);
        ByteBuf buf = Unpooled.copiedBuffer("sdadasdsada撒旦撒", Charset.defaultCharset());
        ctx.channel().writeAndFlush(buf).sync();
        ctx.channel().closeFuture().sync();
    }


}
