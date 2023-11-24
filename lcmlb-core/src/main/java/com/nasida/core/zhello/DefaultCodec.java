package com.nasida.core.zhello;

import com.nasida.core.msg.DefaultMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author yanasida
 * @date 2023/11/23 21:53
 */

public class DefaultCodec extends ByteToMessageCodec<DefaultMessage<String>> {

    @Override
    protected void encode(ChannelHandlerContext ctx, DefaultMessage<String> msg, ByteBuf out) throws Exception {
        //12位
        out.writeBytes(msg.getHeadMagic().getBytes(StandardCharsets.UTF_8));
        //3位
        out.writeBytes(msg.getVersion().getBytes(StandardCharsets.UTF_8));
        //1位
        out.writeShort(msg.getCmdType());
        byte[] bytes = msg.getData().getBytes(StandardCharsets.UTF_8);
        //date长度
        out.writeShort(bytes.length);
        out.writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < DefaultMessage.HEADER_LENGTH) {
            return;
        }
        // Mark the current reader index to support resetting if needed
        in.markReaderIndex();

        // Read magic and version
        String magic = in.readBytes(12).toString(StandardCharsets.UTF_8);
        String version = in.readBytes(3).toString(StandardCharsets.UTF_8);

        // Read command type
        short cmdType = in.readShort();

        // Read data length
        short dataLength = in.readShort();

        // Check if there's enough data in the buffer
        if (in.readableBytes() < dataLength) {
            // Reset the reader index to the marked position
            in.resetReaderIndex();
            return;  // Wait for more data
        }

        // Read data
        byte[] dataBytes = new byte[dataLength];
        in.readBytes(dataBytes);
        String data = new String(dataBytes, StandardCharsets.UTF_8);

        // Construct DefaultMessage object
        DefaultMessage<String> decodedMessage = new DefaultMessage<>(cmdType, data);

        // Add the decoded message to the output list
        out.add(decodedMessage);
    }
}
