package com.course.juc.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier 测试
 * 
 * @author qinlei
 * @date 2021/6/16 下午5:21
 */
public class CyclicBarrierTest {

	public static void main(String[] args) throws InterruptedException {
        int N = 6;  // 运动员数
        CyclicBarrier cb = new CyclicBarrier(N, new Runnable() {
            @Override
            public void run() {
                System.out.println("所有运动员已准备完毕，发令枪：跑！");
            }
        });
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            Thread t = new Thread(new PrepareWork(cb), "运动员[" + i + "]");
            list.add(t);
            t.start();
            if (i == 3) {
                t.interrupt();  // 运动员[3]置中断标志位
            }
        }

        Thread.sleep(2000);
        System.out.println("Barrier是否损坏：" + cb.isBroken());
	}

    private static class PrepareWork implements Runnable {
        private CyclicBarrier cb;

        PrepareWork(CyclicBarrier cb) {
            this.cb = cb;
        }

        @Override
        public void run() {

            try {
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName() + ": 准备完成");
                cb.await();          // 在栅栏等待
            }catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + ": 被中断");
            } catch (BrokenBarrierException e) {
                System.out.println(Thread.currentThread().getName() + ": 抛出BrokenBarrierException");
            }
        }
    }
}


