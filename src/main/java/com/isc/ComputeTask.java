package com.isc;

import java.util.concurrent.RecursiveTask;

/**
 * 计算指定区间数值之和
 *
 */
public class ComputeTask extends RecursiveTask<Long> {

//    int start,end;
//    // 每个子任务处理的数据量
//    int sizePerSubTask;
//
//    public ComputeTask(int start,int end,int sizePerSubTask){
//        this.start = start;
//        this.end = end;
//        this.sizePerSubTask = sizePerSubTask;
//    }

    @Override
    protected Long compute() {


        try {
            LogUtil.log("processing...");
            Thread.sleep(5000);
            LogUtil.log("done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 0L;


    }
}
