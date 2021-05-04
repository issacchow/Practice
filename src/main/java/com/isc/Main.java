package com.isc;

import java.io.IOException;

public class Main {

    public static void main( String[] args ) throws IOException, InterruptedException {
        final ZeroCopy instance = new ZeroCopy();

        final String readFilePath = args[0];
        final String saveFilePath = args[1];

        instance.ReadFileByDMA(readFilePath);


        Thread receThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    instance.ReceiveFileByDMA(saveFilePath,9999);
                } catch (IOException e) {
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
