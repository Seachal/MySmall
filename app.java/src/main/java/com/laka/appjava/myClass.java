package com.laka.appjava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Collections;

public class myClass {

    public static void main(String args[]) {
        //System.out.println("hello world");
        //静态工厂方法
        //Collections.EMPTY_LIST;
        //Calendar.getInstance();
        //Integer[] instance = (Integer[]) Array.newInstance(Integer.class, 10);
        //Files.newBufferedReader()


        //try-with-resources 语句，try() 括号内部放置一个 AutoCloseable/Closeable
        //这样做，不管这段代码有没有发生异常，相应的对象都会被自动关闭掉，无需手动关闭这么麻烦
//        try (BufferedReader readerSugar = new BufferedReader(new FileReader("src/testRead.txt"))) {
//            readerSugar.readLine();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //clone() 方法测试
        PhoneNumber phoneNumber1 = new PhoneNumber();
        phoneNumber1.setNumber("15812345678");
        System.out.println("phoneNumber1=" + phoneNumber1.getNumber());
        PhoneNumber phoneNumber2 = phoneNumber1.clone();
        System.out.println("phoneNumber2=" + phoneNumber2.getNumber());
        phoneNumber2.getList().add("3");
        System.out.println("phoneNumber1.list" + phoneNumber1.getList() + ",phoneNumber2.list=" + phoneNumber2.getList());

        System.out.println("phoneNumber1=" + phoneNumber1 + ",phoneNumber2=" + phoneNumber2);
        System.out.println("phoneNumber11.number=" + phoneNumber1.getNumber() + ",phoneNumber2.number=" + phoneNumber2.getNumber());
        System.out.println("phoneNumber11.class=" + phoneNumber1.getClass() + ",phoneNumber2.class=" + phoneNumber2.getClass());

    }


    public static void handleTest() {
        int[] array = new int[5];
        for (int i = 0; i < array.length; i++) {
            System.out.println("item=" + array[i]);
        }
    }


}
