package cn.mingyu.netty;

import java.nio.ByteBuffer;

/**
 * ClassName: BufferPutGet
 * Description:
 * date: 2022/1/18 下午10:47
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class BufferPutGet {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        for (int i = 0; i < byteBuffer.capacity(); i++){
            byteBuffer.put((byte) i);
        }
        byteBuffer.flip();
        ByteBuffer byteBuffer1 = byteBuffer.asReadOnlyBuffer();
        while (byteBuffer1.hasRemaining()){
            System.out.println(byteBuffer1.get());
        }
        byteBuffer1.put((byte)123);


    }
}
