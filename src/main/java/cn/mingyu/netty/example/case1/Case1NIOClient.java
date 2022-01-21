package cn.mingyu.netty.example.case1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @author yimingyu
 * @date 2022/01/21
 */
public class Case1NIOClient {
    public static void main(String[] args) throws IOException {
        //创建SocketChannel
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        if (!socketChannel.connect(new InetSocketAddress("127.0.0.1", 7788))){
            while (!socketChannel.finishConnect()){
                System.out.println("unfinish connect");
            }
        }
        String "hello pan"
    }
}
