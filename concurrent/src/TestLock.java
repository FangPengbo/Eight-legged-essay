import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Fang Pengbo
 * @Date: 2022/08/16/23:17
 * @Description:
 */
public class TestLock {

    //构造函数声明锁是否公平
    public static Lock lock = new ReentrantLock(false);

    static volatile boolean flag = false;

    public static void main(String[] args) throws InterruptedException {

        //测试Lock基本用法
        //testLock();
        //测试Lock的非公平性
        testFair();




    }

    private static void testFair() throws InterruptedException{
        new Thread(() ->{
            lock.lock();
            System.out.println("A获得了锁...");
            sleep1s();
            lock.unlock();
            System.out.println("A释放了锁...");
        }).start();
        Thread.sleep(100);

        new Thread(() ->{
            lock.lock();
            System.out.println("B获得了锁...");
            sleep1s();
            lock.unlock();
            System.out.println("B释放了锁...");
        }).start();
        Thread.sleep(100);

        new Thread(() ->{
            lock.lock();
            System.out.println("C获得了锁...");
            sleep1s();
            lock.unlock();
            System.out.println("C释放了锁...");
        }).start();
        Thread.sleep(100);

        new Thread(() ->{
            lock.lock();
            System.out.println("D获得了锁...");
            sleep1s();
            lock.unlock();
            System.out.println("D释放了锁...");
        }).start();
        Thread.sleep(100);

        while (!flag){
            new Thread(() ->{
                try {
                    boolean res = lock.tryLock(10, TimeUnit.MILLISECONDS);
                    if(res){
                        System.out.println("我抢到锁了...");
                        flag = true;
                        sleep1s();
                        lock.unlock();
                        System.out.println("我释放了锁...");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }


    }

    private static void testLock() throws InterruptedException{
        new Thread(() ->{
            lock.lock();
            System.out.println("A获得了锁...");
            sleep1s();
            lock.unlock();
            System.out.println("A释放了锁...");
        }).start();
        Thread.sleep(100);

        new Thread(() ->{
            lock.lock();
            System.out.println("B获得了锁...");
            sleep1s();
            lock.unlock();
            System.out.println("B释放了锁...");
        }).start();
        Thread.sleep(100);

        new Thread(() ->{
            lock.lock();
            System.out.println("C获得了锁...");
            sleep1s();
            lock.unlock();
            System.out.println("C释放了锁...");
        }).start();
        Thread.sleep(100);

        new Thread(() ->{
            lock.lock();
            System.out.println("D获得了锁...");
            sleep1s();
            lock.unlock();
            System.out.println("D释放了锁...");
        }).start();
        Thread.sleep(100);

    }

    public  static void sleep1s(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
