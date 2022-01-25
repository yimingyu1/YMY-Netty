package cn.mingyu.netty.example.codec2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

/**
 * ClassName: NettyClinetHandler
 * Description:
 * date: 2022/1/22 下午5:57
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    // 当通道就绪是就触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client " + ctx);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 服务器~", CharsetUtil.UTF_8));
        int random  = new Random().nextInt(2);
        MyDataInfo.MyMessage myMessage = null;
        if (random == 0){
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.studentType)
                    .setStudent(MyDataInfo.Student.newBuilder().setId(1).setName("我是学生").build()).build();
        } else {
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.workerType)
                    .setWorker(MyDataInfo.Worker.newBuilder().setId(2).setName("我是工人").build()).build();
        }
        ctx.writeAndFlush(myMessage);

    }

    // 当通道有读取事件时出发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务器回复：" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器地址：" + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
