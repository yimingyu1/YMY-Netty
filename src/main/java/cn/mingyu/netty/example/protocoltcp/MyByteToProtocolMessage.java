package cn.mingyu.netty.example.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author yimingyu
 * @date 2022/01/26
 */
public class MyByteToProtocolMessage extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("MyByteToProtocolMessage decode 被调用");
        int len = byteBuf.readInt();
        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes);
        ProtocolMessage protocolMessage = new ProtocolMessage();
        protocolMessage.setLen(len);
        protocolMessage.setBytes(bytes);
        list.add(protocolMessage);
    }
}
