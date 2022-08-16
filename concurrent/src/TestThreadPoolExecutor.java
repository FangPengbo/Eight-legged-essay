import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Fang Pengbo
 * @Date: 2022/08/16/22:17
 * @Description:
 */
public class TestThreadPoolExecutor {


    public static void main(String[] args) {
        AtomicInteger integer = new AtomicInteger(1);
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(2);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                //核心线程数
                2,
                //最大线程数 = 核心+救急
                3,
                //救急线程执行完任务之后存活的时间
                0,
                //keepAliveTime的单位
                TimeUnit.MILLISECONDS,
                //线程池满了之后，任务提交的缓存队列
                queue,
                //创建线程池的一些自定义操作 例如设置线程的名称
                r -> new Thread(r,"myThread" + integer.getAndIncrement()),
                //线程池的拒绝策略
                //直接抛异常
                //new ThreadPoolExecutor.AbortPolicy()
                //让提交任务的线程执行
                //new ThreadPoolExecutor.CallerRunsPolicy()
                //丢弃队列中等待最久的任务
                //new ThreadPoolExecutor.DiscardOldestPolicy()
                //丢弃排不上号的任务
                new ThreadPoolExecutor.DiscardPolicy()
        );
        pool.submit(new MyTask("A"));
        pool.submit(new MyTask("B"));
        pool.submit(new MyTask("C"));
        pool.submit(new MyTask("D"));
        pool.submit(new MyTask("E"));
        pool.submit(new MyTask("F"));
        pool.submit(new MyTask("G"));
        pool.submit(new MyTask("H"));
    }


    static class MyTask implements Runnable{
        String name;

        public MyTask(String name){
            this.name = name;
        }
        @Override
        public void run() {
            try {
                System.out.println(this.name + " start....");
                Thread.sleep(10000);
                System.out.println(this.name + " end....");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
