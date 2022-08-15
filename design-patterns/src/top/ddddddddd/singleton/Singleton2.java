package top.ddddddddd.singleton;

/**
 * 枚举饿汉式单例
 *
 * @Author: Fang Pengbo
 * @Date: 2022/08/15/22:56
 * @Description:
 */
public enum Singleton2 {
    INSTANCE;

    public static Singleton2 getInstance(){
        return INSTANCE;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }
}
