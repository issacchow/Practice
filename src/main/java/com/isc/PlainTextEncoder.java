package com.isc;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * 写数据时编码
 */
public class PlainTextEncoder extends ProtocolEncoderAdapter {

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        System.out.printf("\nencode message:%s",message);
        MyMessage msg = (MyMessage)message;
        byte[] data = msg.getContent().getBytes();
        IoBuffer buffer = IoBuffer.allocate(data.length);
        buffer.setAutoExpand(true);
        buffer.put(data);
        buffer.flip();

        out.write(buffer);
    }


}
