package com.isc;


/**
 * 测试线程释放CPU资源,主要是Thread.yield方法
  */
public class Main {

    public static void main(String[] args) throws InterruptedException {


        for(int i=0;i<4;i++){
            YieldThread yieldThread = new YieldThread();
            yieldThread.setName(String.format("YieldThead-%s",i+1));
            //为了避免死循环耗尽cpu资源,设置为守护线程，并且等待主线程结束后结束
            yieldThread.setDaemon(true);
            yieldThread.setPriority(Thread.MIN_PRIORITY);
            yieldThread.start();
        }

        for(int i=0;i<5;i++){
            SleepThread sleepThread = new SleepThread();
            sleepThread.setName(String.format("SleepThead-%s",i+1));
            //为了避免死循环耗尽cpu资源,设置为守护线程，并且等待主线程结束后结束
            sleepThread.setDaemon(true);
            sleepThread.setPriority(Thread.MAX_PRIORITY);
            sleepThread.start();
        }



        Thread.sleep(1000 * 10);


    }


    public static class YieldThread extends Thread {

        @Override
        public void run() {

            String name = this.getName();
            while (true) {

                try {
                    System.out.println();
                    System.out.printf("%s yeild", name);
                    System.out.println();
                    //释放CPU资源，将cpu使用交回给系统,如果这里不这样做，会导致整个系统很卡
                    Thread.yield();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static class SleepThread extends Thread {

        @Override
        public void run() {

            String name = this.getName();
            while (true) {

                try {
                    System.out.println();
                    System.out.printf("%s Sleep", name);
                    System.out.println();
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }


}
