package com.urise.webapp;

import com.urise.webapp.util.LazySingleton;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private int counter;
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread = new Thread(() -> System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState()));
        thread.start();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
//                throw new IllegalStateException();
            }

            private void inc() {
                double a = Math.sin(130.15);

                synchronized (this) {
//                    counter++;
                }
            }
        });
        thread1.start();
        System.out.println(thread.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threadList = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread2 = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread2.start();
            threadList.add(thread2);
        }
        threadList.forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(mainConcurrency.counter);
        LazySingleton.getInstance();
    }

    private synchronized void inc() {
//        synchronized (this) {
        counter++;
//        }
    }
}
