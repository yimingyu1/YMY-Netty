package cn.mingyu.netty.example.zerocopy;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ClassName: OldIOServer
 * Description:
 * date: 2022/1/22 上午9:58
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class OldIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7001);
        while (true){
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            byte[] byteArray = new byte[2048];
            int len = 0;
            while ((len = dataInputStream.read(byteArray)) != -1){
                FileOutputStream fileOutputStream = new FileOutputStream("aaa.zip");
                fileOutputStream.write(byteArray, 0, len);
            }
        }
    }
}
