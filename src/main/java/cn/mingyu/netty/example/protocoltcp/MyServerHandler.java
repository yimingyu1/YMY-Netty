package cn.mingyu.netty.example.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @author yimingyu
 * @date 2022/01/26
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ProtocolMessage> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ProtocolMessage protocolMessage) throws Exception {
        System.out.println("MyServerHandler channelRead0 被调用");
        System.out.println("服务器收到的字节数：" + protocolMessage.getLen());
        System.out.println("服务器收到的内容：" + new String(protocolMessage.getBytes(), CharsetUtil.UTF_8));
        System.out.println("服务器收到信息的次数：" + ++this.count);

        String uuid = UUID.randomUUID().toString();
        byte[] bytes = uuid.getBytes();
        ProtocolMessage protocolMessage1 = new ProtocolMessage();
        protocolMessage1.setLen(bytes.length);
        protocolMessage1.setBytes(bytes);
        channelHandlerContext.writeAndFlush(protocolMessage1);
    }
}
