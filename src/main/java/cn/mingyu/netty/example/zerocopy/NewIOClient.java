package cn.mingyu.netty.example.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * ClassName: NewIOClient
 * Description:
 * date: 2022/1/22 上午10:50
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 7001));
        String filename = "coverage-admin11.zip";
        FileChannel fileChannel = new FileInputStream(filename).getChannel();
        long startTime = System.currentTimeMillis();
        long l = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送的总字节数：" + l + ", 耗时：" + (System.currentTimeMillis() - startTime));
        fileChannel.close();
        socketChannel.close();
    }
}
