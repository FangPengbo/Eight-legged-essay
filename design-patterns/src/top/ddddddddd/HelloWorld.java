package top.ddddddddd;

import top.ddddddddd.singleton.*;

import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Fang Pengbo
 * @Date: 2022/08/15/22:43
 * @Description:
 */
public class HelloWorld {

    public static void main(String[] args) {

        System.out.println("---------------饿汉式单例-----------------");
        Singleton1 instance1 = Singleton1.getInstance();
        Singleton1 instance2 = Singleton1.getInstance();
        System.out.println(instance1);
        System.out.println(instance2);

        System.out.println("---------------枚举饿汉式单例-----------------");
        Singleton2 instance3 = Singleton2.getInstance();
        Singleton2 instance4 = Singleton2.getInstance();
        Singleton2 instance5 = Singleton2.INSTANCE;
        System.out.println(instance3);
        System.out.println(instance4);
        System.out.println(instance5);

        System.out.println("---------------懒汉式单例-----------------");
        Singleton3 instance6 = Singleton3.getInstance();
        Singleton3 instance7 = Singleton3.getInstance();
        System.out.println(instance6);
        System.out.println(instance7);

        System.out.println("---------------懒汉式单例DCL-----------------");
        Singleton4 instance8 = Singleton4.getInstance();
        Singleton4 instance9 = Singleton4.getInstance();
        System.out.println(instance8);
        System.out.println(instance9);

        System.out.println("---------------懒汉式单例-内部类-----------------");
        Singleton5 instance10 = Singleton5.getInstance();
        Singleton5 instance11 = Singleton5.getInstance();
        System.out.println(instance10);
        System.out.println(instance11);

    }


}
