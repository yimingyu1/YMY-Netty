package cn.mingyu.netty;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * ClassName: NIOFileChannel04
 * Description:
 * date: 2022/1/18 下午10:39
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("a.jpeg");
        FileChannel fileChannel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("b.jpeg");
        FileChannel fileChannel1 = fileOutputStream.getChannel();
        fileChannel1.transferFrom(fileChannel, 0, fileChannel.size());
        fileInputStream.close();
        fileOutputStream.close();

    }
}
