package cn.mingyu.netty;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * ClassName: MappedByteBuffer
 * Description:
 * date: 2022/1/18 下午11:04
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class MappedByteBuffer {
    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile("hello", "rw");
        FileChannel channel = file.getChannel();
        java.nio.MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 8);
        map.put(0, (byte)1);
        map.put(5, "啊".getBytes()[1]);
        file.close();
        System.out.println("success");
    }
}
