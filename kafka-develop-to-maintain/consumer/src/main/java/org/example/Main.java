package org.example;

public class Main {
    public static void main(String[] args) {
        Consumer consumer = new Consumer();
//        Map<String, String> consume = consumer.consume();
//        consume.forEach((key, value) -> System.out.println("consumetest" + key + " : " + value));
        consumer.consume1();
    }
}