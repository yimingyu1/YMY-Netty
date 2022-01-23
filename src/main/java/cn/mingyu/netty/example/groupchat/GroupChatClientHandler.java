package cn.mingyu.netty.example.groupchat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * ClassName: GroupChatClientHandle
 * Description:
 * date: 2022/1/23 下午10:21
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class GroupChatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s);
    }

}
