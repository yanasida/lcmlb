package com.nasida.core.zhello;

import com.nasida.core.manager.ConnectorManager;
import com.nasida.core.manager.HeartCheckManager;
import com.nasida.core.manager.LimitConnectorManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;


/**
 * @author yanasida
 * @date 2023/11/23 16:38
 */
public class Server {

    public static void main(String[] args) throws InterruptedException {
        HeartCheckManager<String> manager = new LimitConnectorManager<>(5);
        EventLoopGroup boss = new NioEventLoopGroup(2);
        EventLoopGroup worker = new NioEventLoopGroup(2);
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel socketChannel) {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("decode", new DefaultCodec());
                        pipeline.addLast("heartBeat", new HeartHandler(manager));
                    }
                });//给workerGroup的EventLoop对应的管道设置处理器
        bootstrap.bind("127.0.0.1", 8080)
                .sync();
        manager.startCheck();
    }
}
