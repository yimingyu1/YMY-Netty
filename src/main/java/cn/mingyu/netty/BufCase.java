package cn.mingyu.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * ClassName: BufCase
 * Description:
 * date: 2022/1/23 下午9:05
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class BufCase {
    public static void main(String[] args) {
//        ByteBuf byteBuf = Unpooled.buffer(10);
//        for (int i = 0; i < byteBuf.capacity(); i++){
//            byteBuf.writeByte(i);
//        }
//        for (int i = 0; i < byteBuf.capacity(); i++){
//            System.out.println(byteBuf.readByte());
//        }
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello, world!", CharsetUtil.UTF_8);
        if (byteBuf.hasArray()){
            byte[] bytes = byteBuf.array();
            System.out.println(new String(bytes, 0, byteBuf.writerIndex(), CharsetUtil.UTF_8));
        }
        System.out.println(byteBuf.arrayOffset());
        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.writerIndex());

    }
}
