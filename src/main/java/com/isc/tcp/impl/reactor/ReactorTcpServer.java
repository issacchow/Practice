package com.isc.tcp.impl.reactor;

import com.isc.tcp.AbstractTcpServer;
import com.isc.tcp.IRequestHandler;

import java.io.IOException;

/**
 * 反应器tcp服务,来源于 作者Doug Lea写的nio.pdf
 */
public class ReactorTcpServer<H extends AbstractHandler> extends AbstractTcpServer {

    private Reactor reactor;
    private Class<? extends AbstractHandler> handlerType;


    public ReactorTcpServer(Class<? extends AbstractHandler> handlerType) {
        this.handlerType = handlerType;
    }

    @Override
    public void listen(int port, IRequestHandler requestHandler) {
        try {

            reactor = new Reactor(port, requestHandler, handlerType);

            Thread t = new Thread(reactor);
            t.run();
            t.join();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
