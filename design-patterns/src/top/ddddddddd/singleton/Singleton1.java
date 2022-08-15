package top.ddddddddd.singleton;

import java.io.Serializable;

/**
 * 饿汉式单例
 *
 * @Author: Fang Pengbo
 * @Date: 2022/08/15/22:42
 * @Description:
 */
public class Singleton1 implements Serializable {
    private Singleton1(){
        if (INSTANCE != null){
            //防止反射创建单例
            throw new RuntimeException("对象已被初始化");
        }
        System.out.println("初始化对象");
    }

    private static final Singleton1 INSTANCE = new Singleton1();

    public static Singleton1 getInstance(){
        return INSTANCE;
    }

    /**
     * 防止反序列化创建单例
     * @return INSTANCE
     */
    public Object readResolve(){
        return INSTANCE;
    }

}
