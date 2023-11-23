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
 *
 * @author yanasida
 * @date 2023/11/23 23:06
 */

public class ErrorDemo {
    public static void main(String[] args) {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            //创建bootstrap对象，配置参数
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
            //对通道关闭进行监听,这里不应该阻塞,采用监听回调的方式来进行关闭后续处理,这里阻塞了finally不会执行
            //如果注释sync那么finally会关闭执行器。
            //真的是个人才网上抄的
//            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //关闭线程组
            eventExecutors.shutdownGracefully();
        }
    }
}
