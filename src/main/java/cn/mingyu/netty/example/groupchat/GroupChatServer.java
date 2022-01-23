package cn.mingyu.netty.example.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * ClassName: GroupChatServer
 * Description:
 * date: 2022/1/23 下午9:28
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class GroupChatServer {
    private static final int PORT = 7001;

    public void bootServer() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 创建服务端启动器对象
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 设置启动器参数
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("decoder", new StringDecoder())
                                    .addLast("encoder", new StringEncoder())
                                    .addLast("groupChatHander", new GroupChatServerHandler());
                        }
                    });
            // 绑定端口，启动服务
            ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()){
                    System.out.println("服务器启动成功，端口号为：" + PORT);
                } else {
                    System.out.println("服务器启动失败，请检查配置");
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

    public static void main(String[] args) {
        new GroupChatServer().bootServer();
    }
}
