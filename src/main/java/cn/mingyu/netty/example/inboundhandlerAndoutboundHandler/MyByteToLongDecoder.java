package cn.mingyu.netty.example.inboundhandlerAndoutboundHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * ClassName: MyByteToLongDecoder
 * Description:
 * date: 2022/1/26 上午8:10
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    /**
     *
     * @param channelHandlerContext 上线文对象
     * @param byteBuf  入站的bytebuf
     * @param list   list集合，将解码后的数据传给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("服务端 decode 被调用");
        if (byteBuf.readableBytes() >= 8){
            list.add(byteBuf.readLong());
        }
    }
}
