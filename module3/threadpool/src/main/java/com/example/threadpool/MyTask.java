package com.example.threadpool;

public class MyTask implements Runnable {
    private String taskName;

    public MyTask(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        System.out.println(String.format("Run task - %s Thread - %s", taskName, Thread.currentThread().getName()));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
