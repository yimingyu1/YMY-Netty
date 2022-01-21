package cn.mingyu.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author yimingyu
 * @date 2022/01/21
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        // 创建serversocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        // 绑定端口号
        serverSocketChannel.bind(new InetSocketAddress(6666));
        // 创建selector
        Selector selector = Selector.open();
        // 注册serverSocketChannel到selector并指定位注册时间
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 循环鉴定select方法，如果有事件就处理
        while (true){
            if (selector.select(1000) != 0){
                // 获取发生事件的selectKey集合
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                // 遍历集合，处理每个事件
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    // 如果是连接事件，则创建socketChannel并注册到selector中
                    if (selectionKey.isAcceptable()){
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        System.out.println("socketchannel hashcode = " + socketChannel.hashCode());
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    }
                    // 如果是其他事件（如读时间），则处理读取
                    if (selectionKey.isReadable()){
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                        channel.read(byteBuffer);
                        System.out.println("客户端输入：" + new String(byteBuffer.array()));
                    }
                    // 处理完事件，移除对应的selectKey
                    iterator.remove();
                }
            } else {
                System.out.println("服务器等待1秒，暂无连接");
            }
        }
    }
}
