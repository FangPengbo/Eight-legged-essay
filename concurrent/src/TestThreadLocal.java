/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Fang Pengbo
 * @Date: 2022/08/17/23:19
 * @Description:
 */
public class TestThreadLocal {

    static ThreadLocal<User> local = new ThreadLocal<>();

    public static void main(String[] args) {

        new Thread(() ->{
            if (local.get() == null){
                local.set(new User());
            }
            System.out.println(Thread.currentThread().getName() + ":" + local.get().age);
            local.get().age = 100;
            System.out.println(Thread.currentThread().getName() + ":" + local.get().age);
        }).start();

        new Thread(() ->{
            if (local.get() == null){
                local.set(new User());
            }
            System.out.println(Thread.currentThread().getName() + ":" + local.get().age);
            local.get().age = 200;
            System.out.println(Thread.currentThread().getName() + ":" + local.get().age);
        }).start();

        new Thread(() ->{
            if (local.get() == null){
                local.set(new User());
            }
            System.out.println(Thread.currentThread().getName() + ":" + local.get().age);
            local.get().age = 50;
            System.out.println(Thread.currentThread().getName() + ":" + local.get().age);
        }).start();
    }

    static class User{
        int age = 10;
    }

}
