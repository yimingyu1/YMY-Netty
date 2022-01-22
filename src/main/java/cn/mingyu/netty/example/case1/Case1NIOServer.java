package cn.mingyu.netty.example.case1;

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
public class Case1NIOServer {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private static final int PORT = 7788;

    public Case1NIOServer(){
        try {
            // 创建ServerSocketChannel
            serverSocketChannel = ServerSocketChannel.open();
            // 绑定端口号
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            // 设置为为阻塞式
            serverSocketChannel.configureBlocking(false);
            //创建Selector
            selector = Selector.open();
            // 注册ServerSocketChannel到Selector，指定accept事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listen(){
        System.out.println(Thread.currentThread().getName());
        while (true){
            try {
                if (selector.select(1000) != 0){
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey selectionKey = iterator.next();
                        if (selectionKey.isAcceptable()){
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(256));
                            System.out.println(socketChannel.getRemoteAddress() + "上线了");
                        }
                        if (selectionKey.isReadable()){
                            read(selectionKey);
                        }
                        iterator.remove();
                    }

                }
            } catch (Exception e){
                e.printStackTrace();
            }


        }
    }

    public void read(SelectionKey selectionKey){
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) selectionKey.channel();
            ByteBuffer buffer = (ByteBuffer)selectionKey.attachment();
            int messageLen = channel.read(buffer);
            if (messageLen > 0){
                sendInfoToOther(new String(buffer.array(), 0, buffer.position()), selectionKey);
            }
            buffer.clear();
        }catch (Exception e){
            try {
                if (channel != null) {
                    System.out.println(channel.getRemoteAddress() + "离线了...");
                    selectionKey.cancel();
                    channel.close();
                }
            }catch (Exception e1){
                e1.printStackTrace();
            }


        }
    }

    public void sendInfoToOther(String message, SelectionKey selectionKey){
        System.out.println(Thread.currentThread().getName());
        for(SelectionKey next: selector.keys()) {
            if (!next.equals(selectionKey) && !next.isAcceptable()) {
                SocketChannel socketChannel = (SocketChannel) next.channel();
                try {
                    socketChannel.write(ByteBuffer.wrap(message.getBytes()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }


    public static void main(String[] args) {
        Case1NIOServer case1NIOServer = new Case1NIOServer();
        case1NIOServer.listen();
    }
}
