package cn.mingyu.netty.example.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * ClassName: MyServerHandler
 * Description:
 * date: 2022/1/23 下午11:31
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            String type = null;
            switch (event.state()){
                case READER_IDLE:
                    type = "读空闲";
                    break;
                case WRITER_IDLE:
                    type = "写空闲";
                    break;
                case ALL_IDLE:
                    type = "读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + "-- 超时 -- " + type);
            ctx.channel().close();
        }
    }
}
