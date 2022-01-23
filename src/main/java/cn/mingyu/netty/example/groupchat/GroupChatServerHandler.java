package cn.mingyu.netty.example.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ClassName: GroupChatServerHandler
 * Description:
 * date: 2022/1/23 下午9:34
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * 1. 定义一个channel组管理所有的channel，方便处理转发
     * 2. handlerAdded 表示建立连接
     * 3.
     */

    /**
     * 建立连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 提示其他用户当前用户已上线
        channelGroup.writeAndFlush("[用户]" + channel.remoteAddress() + " - " + LocalDateTime.now().format(dateTimeFormatter) + " 加入聊天~\n");
        // 再将当前channel加入到channel组汇总
        channelGroup.add(channel);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(("[用户]" + ctx.channel().remoteAddress() + " - " + LocalDateTime.now().format(dateTimeFormatter) + " 上线了~\n"));
    }

    /**
     * 断开连接， 当执行该方法时，会先将当前channel从channelgroup中剔除
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channelGroup.writeAndFlush("[用户]" + ctx.channel().remoteAddress() + " - " + LocalDateTime.now().format(dateTimeFormatter) + " 已离开聊天室~\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(("[用户]" + ctx.channel().remoteAddress() + " - " + LocalDateTime.now().format(dateTimeFormatter) + " 已下线~\n"));
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel currentChannel = channelHandlerContext.channel();
        channelGroup.forEach(channel -> {
            if (channel.equals(currentChannel)){
                channel.writeAndFlush("[自己]" + currentChannel.remoteAddress() + " - " + LocalDateTime.now().format(dateTimeFormatter) + " 说：" + s);
            } else {
                channel.writeAndFlush("[用户]" + currentChannel.remoteAddress() + " - " + LocalDateTime.now().format(dateTimeFormatter) + " 说：" + s);
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
