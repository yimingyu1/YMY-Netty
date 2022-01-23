package cn.mingyu.netty.example.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * ClassName: MyServer
 * Description:
 * date: 2022/1/23 下午11:26
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class MyServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new IdleStateHandler(3,5, 7, TimeUnit.SECONDS))
                                    .addLast(new MyServerHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(7001).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()){
                    System.out.println("服务器启动成功");
                } else {
                    System.out.println("服务器启动失败， 请检查配置");
                }
            });
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
