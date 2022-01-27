package cn.mingyu.netty.example.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author yimingyu
 * @date 2022/01/27
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext channelHandlerContext;
    private String result;
    private String param;


    /**
     * 客户端handler的执行顺序
     * 1.channelActive方法执行，给this.channelHandlerContext赋值
     * 2.call方法执行,向channchannelHandlerContextel中写去请求信息并进入等待
     * 3.channelRead方法开始执行，读取channel中的数据并唤醒call方法
     * 4.call方法继续执行，返回请求的结果
     * 5.这期间如果有异常就执行exceptionCaught方法
     * @param ctx
     * @throws Exception
     */

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.channelHandlerContext = ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.result = msg.toString();
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public synchronized Object call() throws Exception {
        this.channelHandlerContext.writeAndFlush(this.param);
        wait();
        return this.result;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
