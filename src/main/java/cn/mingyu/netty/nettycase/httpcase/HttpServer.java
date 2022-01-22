package cn.mingyu.netty.nettycase.httpcase;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * ClassName: HttpServer
 * Description:
 * date: 2022/1/22 下午9:03
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class HttpServer {
    public static void main(String[] args) {
        // 创建事务循环组 bossgroup 和workgroup
        /**
         * 1. bossGroup用于处理连接事件
         * 2. workerGroup用于处理具体业务事件
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建服务器启动对象并配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast("MyHttpServerCodec", new HttpServerCodec())
                                    .addLast("HttpServerHandler", new HttpServerHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(8001).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()){
                    System.out.println("NIO Server boot is success, prot is 6668");
                } else {
                    System.out.println("NIO Server boot is fail");
                }
            });
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
