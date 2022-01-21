package cn.mingyu.netty.example.case1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author yimingyu
 * @date 2022/01/21
 */
public class Case1NIOClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 7788;
    private SocketChannel socketChannel;
    private Selector selector;
    private String userName;

    public Case1NIOClient(){
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            socketChannel.configureBlocking(false);
            selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(256));
            userName = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(userName + " is ok ...");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void write(String message){
        message = userName + " 说：" + message;
        try {
            socketChannel.write(ByteBuffer.wrap(message.getBytes()));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void readInfo(){
        try {
            if (selector.select() > 0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                        channel.read(byteBuffer);
                        System.out.println(new String(byteBuffer.array(),0,  byteBuffer.position()));
                        byteBuffer.clear();
                    }
                    iterator.remove();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws IOException {
        Case1NIOClient case1NIOClient = new Case1NIOClient();
        new Thread(){
            @Override
            public void run() {
                while (true){
                    case1NIOClient.readInfo();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            case1NIOClient.write(s);
        }
    }
}
