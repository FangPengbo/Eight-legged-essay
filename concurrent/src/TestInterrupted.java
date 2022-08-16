/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Fang Pengbo
 * @Date: 2022/08/16/22:47
 * @Description:
 */
public class TestInterrupted {

    public static final Object LOCK = new Object();

    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            synchronized (LOCK) {
                try {
                    System.out.println("hihihi");
                    LOCK.wait();
                    System.out.println("ohu");
                } catch (InterruptedException e) {
                    System.out.println("aya");
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        thread.interrupt();
    }



}
