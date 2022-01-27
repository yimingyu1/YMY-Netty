package cn.mingyu.netty.example.dubborpc.netty;

import cn.mingyu.netty.example.dubborpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Arrays;

/**
 * @author yimingyu
 * @date 2022/01/27
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg = " + msg);
        /**
         * 约定协议
         * 每次发送消息都必须以 "HelloService#hello#"开头
         */
        if (msg.toString().startsWith("HelloService#hello#")){
            String arg = msg.toString().substring(msg.toString().lastIndexOf("#") + 1);
            ctx.writeAndFlush(new HelloServiceImpl().hello(arg));
        } else {
            ctx.writeAndFlush("发送的消息格式不正确，请输入格式为\"HelloService#hello#\"开头的消息");
        }
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
