package com.yn;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class StaticMethodTest {

    private static Map<String, List<String>> bosMap = new ConcurrentHashMap<>();

    private static Map<String,Integer> isDoneMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        try {
            new Thread(() -> test("1","Thread-1")).start();
            Thread.sleep(1000);
            new Thread(() -> test("1","Thread-2")).start();
            Thread.sleep(1000);
            new Thread(() -> test("1","Thread-3")).start();
            Thread.sleep(1000);
            new Thread(() -> test("1","Thread-4")).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void test(String key,String value){
        if(bosMap.get(key) == null){
            CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
            list.add(value);
            bosMap.put(key,list);
        }else {
            bosMap.get(key).add(value);
        }

        Integer num = 1;
        if (!num.equals(isDoneMap.get(key))){
            new StaticMethodTest().new ThreadOutputStream(key).run();
        }
        while (num.equals(isDoneMap.get(key))){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
    }
    class ThreadOutputStream implements Runnable{

        private String key;

        public ThreadOutputStream(String key) {
            this.key = key;
        }

        @Override
        public void run() {
            System.out.println("run-------"+key);
            isDoneMap.put(key,1);
            while (true) {
                for (String value : bosMap.get(key)) {
                    System.out.println(value);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
