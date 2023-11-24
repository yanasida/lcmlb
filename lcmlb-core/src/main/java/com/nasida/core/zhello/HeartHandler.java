package com.nasida.core.zhello;

import com.nasida.core.connector.HeartCheckConnector;
import com.nasida.core.connector.NettyConnector;
import com.nasida.core.manager.HeartCheckManager;
import com.nasida.core.msg.DefaultMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author nasida
 * @date 2023/11/24 15:32
 */
public class HeartHandler extends ChannelInboundHandlerAdapter {

    HeartCheckManager<String> manager;

    public HeartHandler(HeartCheckManager<String> manager) {
        this.manager = manager;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof DefaultMessage) {
            //维护心跳
            if (Cmd.HEART_CMD == ((DefaultMessage<?>) msg).getCmdType()) {
                String key = ctx.channel().id().asLongText();
                if (manager.contains(key)) {
                    HeartCheckConnector connector = (HeartCheckConnector) manager.get(key);
                    connector.setExpiredTime(System.currentTimeMillis() + manager.getCheckDuration() * 1000);
                    connector.setLastHbTime(System.currentTimeMillis());
                } else {
                    manager.put(key, new NettyConnector(ctx));
                }
            }
            System.out.println("心跳管理开始!");
        }
    }
}
