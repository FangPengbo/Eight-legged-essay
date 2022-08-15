package top.ddddddddd.singleton;

import java.io.Serializable;

/**
 * 懒汉式-静态内部类
 *
 * @Author: Fang Pengbo
 * @Date: 2022/08/15/22:42
 * @Description:
 */
public class Singleton5 implements Serializable {
    private Singleton5(){
        System.out.println("初始化对象");
    }

    private static class Holder{
        static Singleton5 INSTANCE = new Singleton5();
    }

    public static Singleton5 getInstance(){
        return Holder.INSTANCE;
    }



}
