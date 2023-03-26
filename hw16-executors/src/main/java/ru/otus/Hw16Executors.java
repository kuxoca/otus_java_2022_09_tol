package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Hw16Executors {
    public static final Logger logger = LoggerFactory.getLogger(Hw16Executors.class);
    private String last = "second";
    private int number = 1;
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
        Hw16Executors hw16Executors = new Hw16Executors();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (last.equals(nameTread)) {
                    this.wait();
                }
                last = nameTread;
                logger.info("{} : number-{}", Thread.currentThread().getName(), hw16Executors.getNumber());
                sleep();
                notifyAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public int getNumber() {
        if (number == 10) {
            isUp = false;
        } else if (number == 1) {
            isUp = true;
        }
        return isUp ? number++ : number--;
    }

    private static void sleep() {
        try {
            Thread.sleep(TimeUnit.MILLISECONDS.toMillis(500));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}