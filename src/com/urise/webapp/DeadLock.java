package com.urise.webapp;

public class DeadLock {
    private static final String LOCK_1 = "lock 1";
    private static final String LOCK_2 = "lock 2";

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("Hello");
            lock(LOCK_1, LOCK_2);
        }).start();
        new Thread(() -> {
            System.out.println("Say: hello!!");
            lock(LOCK_2, LOCK_1);
        }).start();
    }

    private static void lock(String lock_1, String lock_2) {
        synchronized (lock_1) {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock_2) {
                System.out.println("Hi " + Thread.currentThread().getName());
            }
        }
    }
}
