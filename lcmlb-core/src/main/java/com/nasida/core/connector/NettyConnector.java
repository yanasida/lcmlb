package com.nasida.core.connector;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;

/**
 * @author yanasida
 * @date 2023/11/23 15:30
 */
public class NettyConnector extends HeartCheckConnector implements Connector {
    private final ChannelHandlerContext ctx;

    private boolean active;

    public NettyConnector(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        this.active = true;
    }


    @Override
    public boolean connect(String host, int port) throws Exception {
        ChannelFuture future = ctx.channel().connect(new InetSocketAddress(host, port));
        future.sync();
        return future.isSuccess();
    }

    @Override
    public boolean close() throws Exception {
        this.active = false;
        return true;
    }

    @Override
    public boolean isActive() {
        return active && isExpired() && ctx.channel().isActive();
    }

    @Override
    public boolean isExpired() {
        return super.isExpired();
    }

    public ChannelHandlerContext getCtx() {
        return this.ctx;
    }
}
