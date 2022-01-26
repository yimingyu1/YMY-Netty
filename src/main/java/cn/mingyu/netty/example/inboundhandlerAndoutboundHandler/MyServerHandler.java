package cn.mingyu.netty.example.inboundhandlerAndoutboundHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * ClassName: MyServerHandler
 * Description:
 * date: 2022/1/26 上午8:13
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long aLong) throws Exception {
        System.out.println("收到来自客户端" + channelHandlerContext.channel().remoteAddress() + "的Long型数据：" + aLong);
        channelHandlerContext.writeAndFlush(1331L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
