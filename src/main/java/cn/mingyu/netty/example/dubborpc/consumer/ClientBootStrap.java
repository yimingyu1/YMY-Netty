package cn.mingyu.netty.example.dubborpc.consumer;

import cn.mingyu.netty.example.dubborpc.netty.NettyClient;
import cn.mingyu.netty.example.dubborpc.publicinterface.HelloService;

/**
 * @author yimingyu
 * @date 2022/01/27
 */
public class ClientBootStrap {

    private static final String providerName = "HelloService#hello#";

    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient();
        HelloService helloService = (HelloService) nettyClient.getBean(HelloService.class, providerName);
        String result = helloService.hello("nihao, dubbo!");
        System.out.println(result);
    }
}
