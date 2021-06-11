package com.isc;

import com.isc.tcp.MultiplexIOTcpServer;
import com.isc.tcp.TcpServer;

public class Main {

    public static void main( String[] args )
    {
//       TcpServer server = new BIOTcpServer();
       TcpServer server = new MultiplexIOTcpServer();

        server.listen(7777);
    }




}
