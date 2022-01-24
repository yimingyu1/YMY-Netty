package cn.mingyu.netty.example.websocket;

import com.sun.media.sound.SoftTuning;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author yimingyu
 * @date 2022/01/24
 * TextWebSocketFrame代表的时候文本帧类型
 */
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        System.out.println("服务器收到的消息：" + textWebSocketFrame.text());
        channelHandlerContext.channel().writeAndFlush("服务器时间：" + LocalDateTime.now().format(dateTimeFormatter) + textWebSocketFrame.text());

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded被调用 " + ctx.channel().id().asLongText());
        System.out.println("handlerAdded被调用 " + ctx.channel().id().asShortText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved被调用 " + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
