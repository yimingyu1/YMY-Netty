package cn.mingyu.netty.example.inboundhandlerAndoutboundHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * ClassName: MyLongToByteEncoder
 * Description:
 * date: 2022/1/26 上午8:27
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Long aLong, ByteBuf byteBuf) throws Exception {
        System.out.println("客户端 encode 被调用");
        byteBuf.writeLong(aLong);
    }
}
