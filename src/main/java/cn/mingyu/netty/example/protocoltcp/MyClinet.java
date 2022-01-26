package cn.mingyu.netty.example.protocoltcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author yimingyu
 * @date 2022/01/26
 */
public class MyClinet {
    public static void main(String[] args) {
        EventLoopGroup clinetGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clinetGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer());
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 7002).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()){
                    System.out.println("客户端启动成功");
                } else {
                    System.out.println("客户端启动失败，请检查配置");
                }
            });
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            clinetGroup.shutdownGracefully();
        }
    }
}
