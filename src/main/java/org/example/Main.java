package org.example;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService= Executors.newFixedThreadPool(10);

        Timer   timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                TokenBucket.addToken();
            }
        },0,5);
        for (int i = 0; i < 1000; i++) {

            executorService.submit(() -> {
                TokenBucket.rateLimit("message "+Thread.currentThread().getId());
            });
        }
        executorService.shutdown();
    }
}