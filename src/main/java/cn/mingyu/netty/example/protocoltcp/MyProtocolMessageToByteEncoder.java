package cn.mingyu.netty.example.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author yimingyu
 * @date 2022/01/26
 */
public class MyProtocolMessageToByteEncoder extends MessageToByteEncoder<ProtocolMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ProtocolMessage protocolMessage, ByteBuf byteBuf) throws Exception {
        System.out.println("MyProtocolMessageToByteEncoder encode 被调用");
        byteBuf.writeInt(protocolMessage.getLen());
        byteBuf.writeBytes(protocolMessage.getBytes());
    }
}
