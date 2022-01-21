package cn.mingyu.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author yimingyu
 * @date 2022/01/21
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        // 床架socketChannel，并设置为非阻塞的
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        // 连接服务器的ip和短空
        if (!socketChannel.connect(new InetSocketAddress("127.0.0.1", 6666))){
            while (!socketChannel.finishConnect()){
                System.out.println("unfinish connect");
            }
        }
        String str = "hello 熊猫！";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        socketChannel.write(byteBuffer);
        while (true){}
    }
}
