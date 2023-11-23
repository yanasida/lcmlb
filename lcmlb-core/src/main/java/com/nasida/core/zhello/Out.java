package com.nasida.core.zhello;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author yanasida
 * @date 2023/11/23 21:53
 */

public class Out extends MessageToMessageEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
        System.out.println(msg);
        out.add(Unpooled.copiedBuffer(new byte[]{0x66}));
    }

}
