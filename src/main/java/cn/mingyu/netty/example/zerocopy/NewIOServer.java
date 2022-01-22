package cn.mingyu.netty.example.zerocopy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * ClassName: NewIOServer
 * Description:
 * date: 2022/1/22 上午10:43
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class NewIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(7001));
        ByteBuffer byteBuffer =  ByteBuffer.allocate(2048);
        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            FileOutputStream fileOutputStream = new FileOutputStream("bbb.zip");
            int len = 0;
            while ((len = socketChannel.read(byteBuffer)) != -1){
                fileOutputStream.write(byteBuffer.array(), 0, len);
                byteBuffer.clear();
            }
        }
    }
}
