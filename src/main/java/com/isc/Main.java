package com.isc;

import java.util.LinkedList;
import java.util.List;

public class Main {


    public static void main(String[] args) {

        List<Thread> listThread = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            AcquireLockThread thread = new AcquireLockThread();
            thread.setName(String.format("Thread-%s", i));
            listThread.add(thread);
            thread.start();
        }




    }


}
