package io.askcuix.oasis.core.util;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by Chris on 16/1/8.
 */
public class ThreadUtilTest {

    @Test
    public void gracefulShutdown() throws InterruptedException {

        // time enough to shutdown
        ExecutorService pool = Executors.newSingleThreadExecutor();
        Runnable task = new Task(500, 0);
        pool.execute(task);
        ThreadUtil.gracefulShutdown(pool, 1000, TimeUnit.MILLISECONDS);
        assertTrue(pool.isTerminated());

        // time not enough to shutdown,call shutdownNow
        pool = Executors.newSingleThreadExecutor();
        task = new Task(1000, 0);
        pool.execute(task);
        ThreadUtil.gracefulShutdown(pool, 500, TimeUnit.MILLISECONDS);
        assertTrue(pool.isTerminated());

        // self thread interrupt while calling gracefulShutdown
        final ExecutorService self = Executors.newSingleThreadExecutor();
        task = new Task(100000, 0);
        self.execute(task);

        final CountDownLatch lock = new CountDownLatch(1);
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                lock.countDown();
                ThreadUtil.gracefulShutdown(self, 200000, TimeUnit.MILLISECONDS);
            }
        });
        thread.start();
        lock.await();
        thread.interrupt();
        ThreadUtil.sleep(500);
    }

    static class Task implements Runnable {
        private int runTime = 0;

        private final int sleepTime;

        Task(int sleepTime, int runTime) {
            this.sleepTime = sleepTime;
            this.runTime = runTime;
        }

        @Override
        public void run() {
            System.out.println("start task");
            if (runTime > 0) {
                long start = System.currentTimeMillis();
                while ((System.currentTimeMillis() - start) < runTime) {
                }
            }

            try {
                Thread.sleep(sleepTime);
                System.out.println("end task");
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
            }
        }
    }
}
