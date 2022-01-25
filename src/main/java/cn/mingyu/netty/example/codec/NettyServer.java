package cn.mingyu.netty.example.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * ClassName: NettyServer
 * Description:
 * date: 2022/1/22 下午5:12
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        // 创建事务循环组bossGroup和workerGroup
        // 1. bossGroup用来监听处理连接事件
        // 2. workerGroup用来监听处理具体时间
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(3);
        try {
            // 创建服务端启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            //使用链式编程来进行设置
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    // 设置protobuf解码器并指定解码实例
                                    .addLast(new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()))
                                    .addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("...服务器 is ready...");
            // 绑定端口并同步，生成一个ChannelFuture对象
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()){
                    System.out.println("server bind port 6668 success");
                } else {
                    System.out.println("server bind port 6668 fail");
                }
            });
            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
