package cn.mingyu.netty.example.dubborpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yimingyu
 * @date 2022/01/27
 */
public class NettyClient {
    private static NettyClientHandler nettyClientHandler= null;
    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    public Object getBean(final Class<?> serviceClass, String providerName){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serviceClass}, (proxy, method, args) ->{
                    if (nettyClientHandler == null){
                        init();
                    }
                    nettyClientHandler.setParam(providerName + args[0]);
                    return executorService.submit(nettyClientHandler).get();
                });
    }


    private static void init(){
        nettyClientHandler = new NettyClientHandler();
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new StringEncoder())
                                    .addLast(new StringDecoder())
                                    .addLast(nettyClientHandler);
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 7005).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()){
                    System.out.println("客户端启动成功");
                } else {
                    System.out.println("客户端启动失败，请检查配置");
                }
            });
//            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
