package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Hw16Executors {
    public static final Logger logger = LoggerFactory.getLogger(Hw16Executors.class);
    private String last = "second";
    private final static int number = 10;
    private boolean isUp = true;

    public static void main(String[] args) {
        new Hw16Executors().go();
    }

    private void go() {

        var thread1 = new Thread(() -> printNumber("first"));
        thread1.setName("Thread-1");
        thread1.start();

        var thread2 = new Thread(() -> printNumber("second"));
        thread2.setName("Thread-2");
        thread2.start();
    }


    private synchronized void printNumber(String nameTread) {
        int count = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (last.equals(nameTread)) {
                    this.wait();
                }
                last = nameTread;
                count = getNumber(count);
                logger.info("{} : number-{}", Thread.currentThread().getName(), count);

                sleep();
                notifyAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public int getNumber(int inc) {
        if (inc == number) {
            isUp = false;
        } else if (inc == 1) {
            isUp = true;
        }
        return isUp ? (inc+1) : (inc-1);
    }

    private static void sleep() {
        try {
            Thread.sleep(TimeUnit.MILLISECONDS.toMillis(500));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}