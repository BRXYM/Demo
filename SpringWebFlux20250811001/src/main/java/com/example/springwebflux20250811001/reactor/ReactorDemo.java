package com.example.springwebflux20250811001.reactor;

import reactor.core.publisher.Flux;

public class ReactorDemo {
    public static void main(String[] args) {
        Flux.generate(() ->
                1, (i, sink) -> {
                    sink.next("2*" + i + "=" + 2 * i);
                    if (i == 9) {
                        sink.complete();
                    }
                    return i + 1;
                }
        ).subscribe(System.out::println);

        String s = "hello world";
        Flux.fromArray(s.split(" "))
                .flatMap(i -> Flux.fromArray(i.split("")))
                .distinct()
                .sort()
                .subscribe(System.out::println);
    }

}
