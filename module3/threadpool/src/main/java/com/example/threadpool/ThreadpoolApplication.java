package com.example.threadpool;

import java.util.List;

public class ThreadpoolApplication {

    public static void main(String[] args) throws InterruptedException {
        MyThreadPool threadPool = new MyThreadPool(5);

        for (int i=0; i<20; i++) {
            System.out.println("Add task " + i);
            threadPool.execute(new MyTask("Task_"+i));
        }

        //threadPool.awaitTermination();
        threadPool.awaitTerminationCAS();
        threadPool.shutdown();
        //List<Runnable> runnables = threadPool.shutdownNow();
        //System.out.println("COUNT = " + runnables.size());

    }

}
