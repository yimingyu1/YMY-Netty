package cn.mingyu.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author yimingyu
 * @date 2022/01/19
 */
public class ScatteringAndGathering {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel open = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7788);
        SocketChannel accept = open.bind(inetSocketAddress).accept();
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);
        int messageLength = 8;
        while (true){
            int byteread = 0;
            while (byteread < messageLength){
                long read = accept.read(byteBuffers);
                byteread += read;
                System.out.println(read);
                Arrays.asList(byteBuffers).stream().map(byteBuffer -> "position = " + byteBuffer.position()
                     + ", limit = " + byteBuffer.limit()).forEach(System.out::println);
            }
            Arrays.asList(byteBuffers).forEach(ByteBuffer::flip);
            int byteWrite = 0;
            while (byteWrite < messageLength){
                long write = accept.write(byteBuffers);
                byteWrite += write;
            }
            Arrays.asList(byteBuffers).forEach(ByteBuffer::clear);
        }
    }
}
