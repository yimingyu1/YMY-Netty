package cn.mingyu.netty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ClassName: NIOFileChannel01
 * Description:
 * date: 2022/1/18 上午9:12
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class NIOFileChannel01 {
    public static void main(String[] args) throws IOException {
        String str = "hello 熊猫";
        FileOutputStream fileOutputStream = new FileOutputStream(new File(str));
        FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        channel.write(byteBuffer);
        fileOutputStream.close();;
    }
}
