package com.isc;

import java.io.IOException;

/**
 * 本示例主要用于调试java.nio包下关于channel的使用,尤其是【零拷贝】的应用。
 * 包含了一些使用 CPU 和 DMA 搬数据方式的例子
 */
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {


        String mode = args[0];



        if("output".equals(mode)){
            System.out.println("hello");
            return;
        }

        // 直接读取文件
        if (mode.equals("readFileByCPU")) {
            System.out.println("readFileByCPU");
            readFileByCPU(args);
            return;
        }


        if (mode.equals("readFileByDMA")) {
            System.out.println("readFileByDMA");
            readFileByDMA(args);
            return;
        }

        if ("transferfile".equals(mode)) {
            System.out.println("transferfile");
            transferFile(args);
            return;
        }

    }

    public static void readFileByCPU(String[] args) throws IOException, InterruptedException {



        final String readFilePath = args[1];
        Integer sleep = Integer.valueOf(args[2]);

        final ZeroCopy instance = new ZeroCopy();
        instance.readFileByCPU(readFilePath);
        Thread.sleep(sleep);
    }

    public static void readFileByDMA(String[] args) throws IOException, InterruptedException {

        final String readFilePath = args[1];
        Integer sleep = Integer.valueOf(args[2]);

        final ZeroCopy instance = new ZeroCopy();
        instance.ReadFileByDMA(readFilePath);
        Thread.sleep(sleep);
    }

    public static void transferFile(String[] args) throws InterruptedException {
        final ZeroCopy instance = new ZeroCopy();


        final String readFilePath = args[1];
        final String saveFilePath = args[2];
        final Integer sleep = Integer.valueOf(args[3]);


        Thread receThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    instance.ReceiveFileByDMA(saveFilePath, 9999);

                    Thread.sleep(sleep);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        Thread sendThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    instance.SendFileByDMA(readFilePath, "localhost", 9999);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        receThread.start();
        Thread.sleep(5000);
        sendThread.start();
        receThread.join();


    }


}
