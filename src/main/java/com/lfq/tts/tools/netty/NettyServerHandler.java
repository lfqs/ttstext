package com.lfq.tts.tools.netty;

import cn.hutool.json.JSONObject;
import com.lfq.tts.tools.netty.utils.ResponseXmlData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
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
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //定义一个channle 组，管理所有的channel
    //GlobalEventExecutor.INSTANCE) 是全局的事件执行器，是一个单例
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 读取到客户端发来的数据数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //msg为接收到的客户端传递的数据   个人这边直接传的json 数据
        ByteBuf readMessage= (ByteBuf) msg;
        //解析客户端json 数据
        log.info("===============================================");
        log.info("接收到的数据"+readMessage.toString(StandardCharsets.UTF_8));
        //获取客户端的请求地址  取到的值为客户端的 ip+端口号
        String url=ctx.channel().remoteAddress().toString();//设备请求地址（个人将设备的请求地址当作 map 的key
        log.info("客户端的请求地址"+url);
        log.info("===============================================");
        //返回数据给客户端
        ResponseXmlData responseXmlData = new ResponseXmlData();
        responseXmlData.setCode("0000");
        responseXmlData.setMsg("受理成功！");
        ByteBuf buf = Unpooled.copiedBuffer(responseXmlData.toString(), Charset.defaultCharset());
        ctx.channel().writeAndFlush(buf).sync();
        if(ctx.channel().isActive()){
            ctx.close();
        }
    }

    /**
     * 当有客户端与服务器断开连接时执行此方法，此时会自动将此客户端从 channelGroup 中移除
     * 1.打印提示信息
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("有客户端与服务器断开连接。客户端地址：" + channel.remoteAddress());
    }


    /**
     * 有客户端与服务器发生连接时执行此方法
     * 1.打印提示信息
     * 2.将客户端保存到 channelGroup 中
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("有新的客户端与服务器发生连接。客户端地址：" + channel.remoteAddress());
        channelGroup.add(channel);
    }

    /**
     * 处理异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("发生异常。异常信息：{}", cause.getMessage());
        //关闭通道
        ctx.close();
    }

}
