package cn.mingyu.netty.example.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author yimingyu
 * @date 2022/01/26
 */
public class MyClientHandler extends SimpleChannelInboundHandler<ProtocolMessage> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ProtocolMessage protocolMessage) throws Exception {
        System.out.println("客户端收到的字节数：" + protocolMessage.getLen());
        System.out.println("客户端收到内容：" + new String(protocolMessage.getBytes(), CharsetUtil.UTF_8));
        System.out.println("客户端收到的信息次数：" + ++this.count);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 5; i++){
            String msg = "见天asfasdfa阿斯顿发送到";
            byte[] bytes = msg.getBytes();
            ProtocolMessage protocolMessage = new ProtocolMessage();
            protocolMessage.setLen(bytes.length);
            protocolMessage.setBytes(bytes);
            ctx.writeAndFlush(protocolMessage);
        }
    }
}
