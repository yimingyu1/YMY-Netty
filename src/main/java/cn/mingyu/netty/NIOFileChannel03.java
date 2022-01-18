package cn.mingyu.netty;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ClassName: NIOFileChannel03
 * Description:
 * date: 2022/1/18 下午10:16
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("1.txt");
        FileChannel fileChannel = fis.getChannel();
        FileOutputStream fos = new FileOutputStream("2.txt");
        FileChannel fileChannel1 = fos.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        int len = 0;
        int count = 1;
        while ((len = fileChannel.read(byteBuffer)) != -1){
            byteBuffer.flip();
            fileChannel1.write(byteBuffer);
            System.out.println(++count);
//            byteBuffer.flip();
            byteBuffer.clear();
        }
        fis.close();
        fos.close();

    }
}
