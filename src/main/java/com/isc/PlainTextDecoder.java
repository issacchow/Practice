package com.isc;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * 读数据时解码
 */
public class PlainTextDecoder extends CumulativeProtocolDecoder {



    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
//        System.out.printf("\ndecode in buffer,size:%s",1);
        int size = in.limit();
        byte[] dst = new byte[size];
        in.get(dst);
        String s = new String(dst);
        MyMessage msg = new MyMessage(s);
        out.write(msg);

        return false;
    }

}
