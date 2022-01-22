package cn.mingyu.netty.nettycase;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * ClassName: NettyServerHandler
 * Description:
 * date: 2022/1/22 下午5:32
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     *
     * @param ctx 上下文对象， 含有管道pipeline，通道channel， 地址
     * @param msg 就是客户端发送的数据 默认为object
     * @throws Exception
     */
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        System.out.println(Thread.currentThread().getName());
        System.out.println("server ctx = " + ctx);
        // 假定这个一个非常耗时的操作，则会阻塞pipeline的执行，
        /**
         * 解决方案1: 异步执行 -》将这个耗时的操作放到channel对应的NIOEventLoop的TaskQueue中
         */
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~001", CharsetUtil.UTF_8));

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        /**
         * 将msg转为byteBuf
         * byteBuf是Netty提供的， 不是NIO的ByteBuffer
         */
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是：" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址是：" + ctx.channel().remoteAddress());


    }

    // 数据读取完毕
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 将数据写入缓存并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~", CharsetUtil.UTF_8));
    }

    // 出现异常，关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
