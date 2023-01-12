package com.isc;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.*;

/**
 * 纯文本编解码过器
 */
public class PlainTextCodecFactory implements ProtocolCodecFactory {

    PlainTextEncoder encoder = new PlainTextEncoder();
    PlainTextDecoder decoder = new PlainTextDecoder();

    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }


}
