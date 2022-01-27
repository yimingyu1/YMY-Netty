package cn.mingyu.netty.example.dubborpc.provider;

import cn.mingyu.netty.example.dubborpc.publicinterface.HelloService;

/**
 * @author yimingyu
 * @date 2022/01/27
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
        System.out.println("收到来自客户端的消息：" + msg);
        if (msg != null){
            return "你好客户端， 我已经收到你的消息 【" + msg + "】";
        } else {
            return "你好客户端， 我已经收到你的消息 【"  + "】";
        }
    }
}
