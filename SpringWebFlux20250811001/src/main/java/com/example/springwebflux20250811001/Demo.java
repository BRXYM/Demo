package com.example.springwebflux20250811001;

import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        while (true) {
            executorService.submit(Demo::handle1);
            executorService.submit(Demo::handle2);
            executorService.submit(Demo::handle3);
            executorService.submit(Demo::handle4);
        }
    }

    @SneakyThrows
    private static void handle4() {
        Thread.sleep(1000);
    }

    @SneakyThrows
    private static void handle2() {
        Thread.sleep(50);
    }

    @SneakyThrows
    private static void handle3() {
        Thread.sleep(100);
    }

    @SneakyThrows
    private static void handle1() {
        Thread.sleep(50);
    }
}