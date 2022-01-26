package cn.mingyu.netty.example.inboundhandlerAndoutboundHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * ClassName: MyClientInitializer
 * Description:
 * date: 2022/1/26 上午8:27
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new MyLongToByteEncoder())
                .addLast(new MyByteToLongDecoder())
                .addLast(new MyClientHandler());
    }
}
