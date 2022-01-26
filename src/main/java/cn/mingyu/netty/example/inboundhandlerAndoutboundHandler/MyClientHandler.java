package cn.mingyu.netty.example.inboundhandlerAndoutboundHandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * ClassName: MyClientHandler
 * Description:
 * date: 2022/1/26 上午8:30
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long aLong) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端 channelActive 被调用");
//        ctx.writeAndFlush(12341L);
        ctx.writeAndFlush(Unpooled.copiedBuffer("asdfasdfasasfdasdf", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
