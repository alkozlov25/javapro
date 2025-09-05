package com.example.threadpool;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadPool {
    private final Object lock = new Object();
    //private final Lock lock2 = new ReentrantLock();
    //private final Condition termination;
    private final List<MyThread> threadList = new ArrayList<>();
    protected final LinkedList<Runnable> taskList = new LinkedList<>();
    private boolean isShutdown = false;
    private CountDownLatch latch = new CountDownLatch(0);

    private final AtomicInteger countTask = new AtomicInteger(0);

    public MyThreadPool(int size) {

        //this.termination = this.lock2.newCondition();

        System.out.println(String.format("ThreadPool initialization size = %s", size));

        for (int i=0; i<size; i++) {
            MyThread newThread = new MyThread("TaskThread_" + i, taskList);
            System.out.println(String.format("Create thread %s - %s", i, newThread.getName()));
            threadList.add(newThread);
            newThread.start();
        }
    }

    public void execute(Runnable task) {
        if(isShutdown) {
            throw new IllegalStateException("MyThreadPool is shutdown");
        }

        synchronized (lock) {

            taskList.add(task);
            lock.notify();

        }

        latch = new CountDownLatch((int) (latch.getCount() + 1));
        countTask.incrementAndGet();
    }

    public void shutdown() {

        isShutdown = true;

        for (MyThread myThread : threadList) {
            if (myThread.getState() != Thread.State.RUNNABLE)  myThread.interrupt();
        }
    }

    public List<Runnable> shutdownNow() {
        isShutdown = true;

        List<Runnable> result;
        synchronized (lock) {
            result = new ArrayList<>(taskList);
            taskList.clear();
        }

        shutdown();

        return result;
    }
    public boolean isTerminated() {
        boolean result;

        synchronized (lock) {
            result = this.isShutdown && this.taskList.isEmpty();
        }

        return result;
    }

    public void awaitTermination() throws InterruptedException {
        System.out.println("awaitTermination count = " + latch.toString());
        latch.await();
    }

    public void awaitTerminationCAS() {
        System.out.println("awaitTerminationCAS count = " + countTask.get());
        while (countTask.get() != 0) {
        }
    }

    class MyThread extends Thread {
        private final LinkedList<Runnable> myTaskList;

        public MyThread(String name, LinkedList<Runnable> myTaskList) {
            super(name);
            this.myTaskList = myTaskList;
        }

        @Override
        public void run() {

            while (true) {

                if(isShutdown && taskList.isEmpty()) {
                    System.out.println(String.format("Thread %s shutdown...", this.getName()));
                    break;
                }

                Runnable currTask;

                synchronized (lock) {

                    if (myTaskList.isEmpty()) {
                        try {
                            System.out.println(String.format("Thread %s wait task", this.getName()));
                            lock.wait();
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                    currTask = myTaskList.removeFirst();
                    System.out.println("latch.countDown() = " + latch);

                }

                try {
                    currTask.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                latch.countDown();//уменьшаем защелку после завершения задачи, т.к. иначе задача падает на sleep, если сделать shutdown
                countTask.decrementAndGet();
            }
        }
    }
}
