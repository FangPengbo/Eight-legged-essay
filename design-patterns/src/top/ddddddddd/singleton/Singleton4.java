package top.ddddddddd.singleton;

import java.io.Serializable;

/**
 * 懒汉式单例-DCL
 *
 * @Author: Fang Pengbo
 * @Date: 2022/08/15/22:42
 * @Description:
 */
public class Singleton4 implements Serializable {
    private Singleton4(){
        if (INSTANCE != null){
            //防止反射创建单例
            throw new RuntimeException("对象已被初始化");
        }
        System.out.println("初始化对象");
    }

    private static volatile Singleton4 INSTANCE = null;

    public static Singleton4 getInstance(){
        if (INSTANCE == null){
            synchronized (Singleton4.class){
                if (INSTANCE == null){
                    INSTANCE = new Singleton4();
                }
            }
        }
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
