package com.isc;

import com.isc.tcp.ITcpServer;
import com.isc.tcp.impl.reactor.ReactorTcpServer;

public class Main {

    public static void main( String[] args )
    {


//       ITcpServer server = new BIOTcpServer();
//       ITcpServer server = new MultiplexIOTcpServer();
       ITcpServer server = new ReactorTcpServer();

        server.listen(7777);
    }




}
