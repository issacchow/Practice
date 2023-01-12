package com.isc;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import java.net.SocketAddress;

public class MyHandler extends IoHandlerAdapter {

    private int sleep = 5000;

    public void setSleep(int sleep) {
        this.sleep = sleep;
        System.out.printf("\nset sleep:" + sleep);
    }

    public String threadInfo() {
        Thread t = Thread.currentThread();
        return String.format("(thread id:%s , thread name:%s)", t.getId(), t.getName());
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        Main.currentSession = session;
        SocketAddress remoteAddress = session.getRemoteAddress();
        System.out.printf("\n %s session connected - remote address:%s", threadInfo(), remoteAddress.toString());
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        System.out.println();
        System.out.printf("\n %s recevied message: %s",  threadInfo(),((MyMessage)message).getContent());

        // 模拟耗时
        Thread.sleep(sleep);

        // 为了配合JMeter测试，自动回复
        session.write(new MyMessage("ok\n"));
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println();
        System.out.printf("\n %s sent message: %s",  threadInfo(),((MyMessage)message).getContent());
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println();
        System.out.printf("error:%s",cause.getMessage());
        cause.printStackTrace();
    }
}
