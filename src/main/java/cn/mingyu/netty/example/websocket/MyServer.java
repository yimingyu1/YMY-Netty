package cn.mingyu.netty.example.websocket;

import cn.mingyu.netty.example.heartbeat.MyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author yimingyu
 * @date 2022/01/24
 */
public class MyServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler())
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    // 因为是基于http协议的，所以使用http的编码和解码器
                                    .addLast(new HttpServerCodec())
                                    // 以块的方式写，添加chunkedwriteHandler处理
                                    .addLast(new ChunkedWriteHandler())
                                    /**
                                     * http协议中数据传输是分段，HttpObjectAggregator可以将分段的数据聚合起来
                                     * 这就是为什么，浏览器发送大量数据时会发送多个http请求
                                     */
                                    .addLast(new HttpObjectAggregator(8192))
                                    /**
                                     * 对应websocket，它的数据以帧的形式传输
                                     * WebSocketServerProtocolHandler的核心功能是审计http协议升级为ws，保持长链接
                                     */
                                    .addLast(new WebSocketServerProtocolHandler("/hello"))
                                    .addLast(new MyTextWebSocketFrameHandler());

                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(7001).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()){
                    System.out.println("服务器启动成功！");
                } else {
                    System.out.println("服务器启动失败，请检查配置！");
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
