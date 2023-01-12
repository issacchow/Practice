package com.isc;

import org.apache.mina.core.service.SimpleIoProcessorPool;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioProcessor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {


    public static IoSession currentSession = null;

    public static void main(String[] args) throws IOException {


        /** Processor Executor **/

        MyThreadPoolExecutor processorExecutor = new MyThreadPoolExecutor(
                4, 4,
                5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4)
        );
        processorExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        processorExecutor.setThreadFactory(new CustomizableThreadFactory("ProcessorThread-"));
        processorExecutor.setName("Processor-Executor");


        /** Acceptor的启动线程池 **/

        MyThreadPoolExecutor acceptorExecutor = new MyThreadPoolExecutor(
                1, 1,
                0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1)
        );
        acceptorExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        acceptorExecutor.setThreadFactory(new CustomizableThreadFactory("IoThread-"));
        acceptorExecutor.setName("Acceptor-Executor");


        /** Acceptor  **/
        NioSocketAcceptor acceptor = new NioSocketAcceptor(
                acceptorExecutor,
                new SimpleIoProcessorPool<>(NioProcessor.class, Executors.newCachedThreadPool())
        );

        MyHandler handler = new MyHandler();
        acceptor.setHandler(handler);
        acceptor.setReuseAddress(true);
        acceptor.getFilterChain().addLast("plainCodecFilter", new ProtocolCodecFilter(new PlainTextCodecFactory()));


        // session 读写缓冲去设置
        //acceptor.getSessionConfig().setReceiveBufferSize();


        // 监听

        acceptor.bind(new InetSocketAddress("127.0.0.1", 9999));


        System.out.println("start to listen on 9999...");
        System.out.println("you can input something to sent");


        /** 读取并输出 **/

        Scanner scan = new Scanner(System.in);
        // 从键盘接收数据

        // 判断是否还有输入
        while (true) {

            if (scan.hasNextLine()) {
                String str2 = scan.nextLine();
                if ("stop".compareToIgnoreCase(str2) == 0) {
                    break;
                }

                // 忽略回车
                if("\n".compareTo(str2)==0 || str2.length()==0){
                    continue;
                }

                if(str2.startsWith("set sleep ")){
                    String[] s = str2.split(" ");
                    int sleep = Integer.valueOf(s[2]);
                    handler.setSleep(sleep);
                    continue;
                }

                if (currentSession != null) {
                    MyMessage msg = new MyMessage(str2);
                    currentSession.write(msg);
                }
            }
        }
        scan.close();
        acceptor.dispose();


    }


}
