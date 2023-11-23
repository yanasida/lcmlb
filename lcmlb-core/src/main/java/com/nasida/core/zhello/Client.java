package com.nasida.core.zhello;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @author yanasida
 * @date 2023/11/23 17:51
 */
public class Client {
    static NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
    static Bootstrap bootstrap = new Bootstrap();

    public static void main(String[] args) throws Exception {
        try {
            //设置线程组
            bootstrap.group(eventExecutors)
                    //设置客户端的通道实现类型
                    .channel(NioSocketChannel.class)
                    //使用匿名内部类初始化通道
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            //添加客户端通道的处理器
                            ch.pipeline().addLast(new StringEncoder());
                        }
                    });
            //连接服务端
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8080).sync();
            System.out.println(channelFuture.isSuccess());
            Channel channel = channelFuture.channel();
            new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String line = scanner.nextLine();
                    if ("exit".equals(line)) {
                        channel.close();
                        break;
                    }
                    channel.writeAndFlush(line);
                }
            }, "input").start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("wfesgrdhtf");
        }
        System.out.println("end line");
    }
}
