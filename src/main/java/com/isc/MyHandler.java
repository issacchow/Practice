package com.isc;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import java.net.SocketAddress;
import java.time.LocalDateTime;

public class MyHandler extends IoHandlerAdapter {

    private int sleep = 1000;
    private boolean showRecMsgLog = true;
    private boolean showSentMsgLog = true;


    public void setShowRecMsgLog(boolean showRecMsgLog) {
        this.showRecMsgLog = showRecMsgLog;
    }

    public void setShowSentMsgLog(boolean showSentMsgLog) {
        this.showSentMsgLog = showSentMsgLog;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
        System.out.printf("\nset sleep:" + sleep);
    }










    public String threadInfo() {
        Thread t = Thread.currentThread();
        return String.format("[%s] (thread id:%s , thread name:%s)", LocalDateTime.now().toString(), t.getId(), t.getName());
    }

    public String sessionInfo(IoSession session) {
        return String.format(" (session id:%s , write bytes:%s, read bytes:%s , write queue size:%s ) ",
                session.getId(), session.getWrittenBytes(), session.getReadBytes(),session.getWriteRequestQueue().size());
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        Main.currentSession = session;
        SocketAddress remoteAddress = session.getRemoteAddress();
        System.out.printf("\n %s session connected - %s", threadInfo(),  sessionInfo(session) );
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        if(showRecMsgLog) {
            System.out.println();
            System.out.printf("\n %s %s received message: %s", threadInfo(), sessionInfo(session), ((MyMessage) message).getContent());
        }

        // 模拟耗时
        System.out.println();
        System.out.printf("\n%s %s - sleep %s ms",threadInfo(),sessionInfo(session),sleep);
        Thread.sleep(sleep);
        System.out.printf("\n%s %s - wakup",threadInfo(),sessionInfo(session));

        // 为了配合JMeter测试，自动回复
        session.write(new MyMessage("ok\n"));
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        if(showSentMsgLog) {
            System.out.println();
            System.out.printf("\n %s %s sent message: %s", threadInfo(), sessionInfo(session), ((MyMessage) message).getContent());
        }
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println();
        System.out.printf("%s error:%s",  sessionInfo(session) , cause.getMessage());
        cause.printStackTrace();
    }
}
