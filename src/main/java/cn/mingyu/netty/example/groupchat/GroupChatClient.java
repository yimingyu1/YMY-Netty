package cn.mingyu.netty.example.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * ClassName: GroupChatClient
 * Description:
 * date: 2022/1/23 下午10:20
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class GroupChatClient {
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 7001;

    public void bootClient(){
        EventLoopGroup clientLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast("decoder", new StringDecoder())
                                    .addLast("encoder", new StringEncoder())
                                    .addLast("groupChatClientHandler", new GroupChatClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(ADDRESS, PORT).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()){
                    System.out.println("客户端连接成功");
                } else {
                    System.out.println("客户端连接失败，请检查配置");
                }
            });
            channelFuture.channel().closeFuture();
            Channel currentChannel = channelFuture.channel();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                currentChannel.writeAndFlush(scanner.nextLine() + "\r\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            clientLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new GroupChatClient().bootClient();
    }
}
