package cn.mingyu.netty;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yimingyu
 * @date 2022/01/17
 */
public class BIOServer {
    public static void main(String[] args) throws IOException {
        // 创建线程池
        ExecutorService service = Executors.newCachedThreadPool();
        // 创建serverSocket
        ServerSocket serverSocket = new ServerSocket(8881);
        // 开启socket监听，等待客户端连接,客户端连接后开启新的线程处理
        System.out.println("启动服务器：8881");
        while (true){
            Socket accept = null;
            try {
                accept = serverSocket.accept();
                System.out.println(accept.getInetAddress() + "已连接");
                final Socket finalAccept = accept;
                service.execute(new Runnable() {
                    public void run() {
                        // 读取客户端输入
                        handle(finalAccept);
                    }
                });
                // 处理完客户端socket，关闭资源
            }catch (Exception e){
                e.printStackTrace();
            }


        }



    }

    public static void handle(Socket socket){
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            int byteRead = 0;
            byte[] bytes = new byte[1024];
            while ((byteRead = inputStream.read(bytes)) != -1){
                System.out.println(Thread.currentThread().getId() + Thread.currentThread().getName() +  new String(bytes, 0, byteRead));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
