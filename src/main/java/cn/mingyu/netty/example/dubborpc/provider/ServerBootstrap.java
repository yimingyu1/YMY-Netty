package cn.mingyu.netty.example.dubborpc.provider;

import cn.mingyu.netty.example.dubborpc.netty.NettyServer;

/**
 * @author yimingyu
 * @date 2022/01/27
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer(7005);
    }
}
