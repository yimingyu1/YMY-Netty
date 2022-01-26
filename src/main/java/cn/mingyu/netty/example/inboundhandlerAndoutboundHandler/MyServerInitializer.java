package cn.mingyu.netty.example.inboundhandlerAndoutboundHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * ClassName: MyServerInitializer
 * Description:
 * date: 2022/1/26 上午8:07
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 此时需要传入一个long型解码器
        socketChannel.pipeline()
                .addLast(new MyByteToLongDecoder())
                .addLast(new MyLongToByteEncoder())
                .addLast(new MyServerHandler());
    }
}
