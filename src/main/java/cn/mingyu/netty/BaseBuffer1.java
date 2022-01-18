package cn.mingyu.netty;

import java.nio.IntBuffer;

/**
 * ClassName: BaseBuffer1
 * Description:
 * date: 2022/1/18 上午8:28
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class BaseBuffer1 {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(5);
        for (int i = 0; i < buffer.capacity(); i++){
            buffer.put(i * 2);
        }
        buffer.flip();
        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }
}
