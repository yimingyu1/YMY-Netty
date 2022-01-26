package cn.mingyu.netty.example.codec2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * ClassName: NettyServerHandler
 * Description:
 * date: 2022/1/22 下午5:32
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyDataInfo.MyMessage myMessage) throws Exception {
        MyDataInfo.MyMessage.DataType dataType = myMessage.getDataType();
        if (dataType == MyDataInfo.MyMessage.DataType.studentType){
            System.out.println("客户端发送来一个学生信息： id = " + myMessage.getStudent().getId() + ", name  = " + myMessage.getStudent().getName());
        } else if (dataType == MyDataInfo.MyMessage.DataType.workerType){
            System.out.println("客户端发送来一个工人信息： id = " + myMessage.getWorker().getId() + ", name = " + myMessage.getWorker().getName());
        } else {
            System.out.println("类型错误");
        }
    }
}
