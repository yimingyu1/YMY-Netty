package cn.mingyu.netty.nettycase.httpcase;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * ClassName: HttpServerHandler
 * Description:
 * date: 2022/1/22 下午9:03
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class HttpServerHandler extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if (o instanceof HttpRequest){
            System.out.println("msg类型：" + o.getClass());
            System.out.println("客户端地址：" + channelHandlerContext.channel().remoteAddress());
            HttpRequest request = (HttpRequest) o;
            String uri = request.uri();
            System.out.println(uri);
            if ("/favicon.ico".equalsIgnoreCase(uri)){
                System.out.println("/favicon.ico 请求，不做处理");
                return;
            }
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello, 我是服务器", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());
            channelHandlerContext.writeAndFlush(response);
        }
    }
}
