package cn.mingyu.netty.example.inboundhandlerAndoutboundHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * ClassName: MyClient
 * Description:
 * date: 2022/1/26 上午8:24
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class MyClient {
    public static void main(String[] args) {
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer());
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 7001).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()){
                    System.out.println("客户端启动成功！");
                } else {
                    System.out.println("客户端启动失败，请检查配置！");
                }
            });
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            clientGroup.shutdownGracefully();
        }
    }
}
