package cn.mingyu.netty.example.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author yimingyu
 * @date 2022/01/26
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new MyProtocolMessageToByteEncoder())
                .addLast(new MyByteToProtocolMessage())
                .addLast(new MyClientHandler());
    }
}
