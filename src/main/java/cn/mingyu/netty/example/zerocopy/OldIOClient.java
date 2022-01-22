package cn.mingyu.netty.example.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * ClassName: OldIOClient
 * Description:
 * date: 2022/1/22 上午9:57
 *
 * @author yimingyu
 * @version 1.0
 * @since JDK 1.8
 */
public class OldIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 7001);
        FileInputStream fileInputStream = new FileInputStream("coverage-admin11.zip");
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] bytes = new byte[2048];
        int len = 0;
        long total = 0;
        long startTime = System.currentTimeMillis();
        while ((len = fileInputStream.read(bytes)) != -1){
            dataOutputStream.write(bytes, 0, len);
            total += len;
        }
        System.out.println("发送的总字节数： " + total + ", 耗时： " + (System.currentTimeMillis() - startTime));
        dataOutputStream.close();
        fileInputStream.close();
        socket.close();
    }
}
