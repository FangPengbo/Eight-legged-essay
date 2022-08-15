package top.ddddddddd.singleton;

import java.io.Serializable;

/**
 * 懒汉式单例
 *
 * @Author: Fang Pengbo
 * @Date: 2022/08/15/22:42
 * @Description:
 */
public class Singleton3 implements Serializable {
    private Singleton3(){
        if (INSTANCE != null){
            //防止反射创建单例
            throw new RuntimeException("对象已被初始化");
        }
        System.out.println("初始化对象");
    }

    private static Singleton3 INSTANCE = null;

    public static synchronized Singleton3 getInstance(){
        if (INSTANCE == null){
            INSTANCE = new Singleton3();
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
