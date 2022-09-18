import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Fang Pengbo
 * @Date: 2022/09/18/20:28
 * @Description:
 */
public class Join {


    public static void main(String[] args) throws InterruptedException {

        AtomicInteger a = new AtomicInteger(5);
        AtomicInteger b = new AtomicInteger(5);
        AtomicInteger c = new AtomicInteger(5);
        AtomicInteger d = new AtomicInteger(0);

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            a.set(a.get() + 5);
        });
        Thread thread1 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            b.set(b.get() + 5);
        });
        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            c.set(c.get() + 5);
        });
        Thread thread3 = new Thread(() -> {
            try {
                thread.join();
                thread1.join();
                thread2.join();
                d.set(a.get() + b.get() + c.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(a.get());
        System.out.println(b.get());
        System.out.println(c.get());
        System.out.println(d.get());

        thread.start();
        thread1.start();
        thread2.start();
        thread3.start();


        thread3.join();

        System.out.println(a.get());
        System.out.println(b.get());
        System.out.println(c.get());
        System.out.println(d.get());
    }


}
