import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Fang Pengbo
 * @Date: 2022/08/20/15:24
 * @Description:
 */
public class HelloWorld {
    public static void main(String[] args) {
        Executors.newFixedThreadPool(1);
        Executors.newCachedThreadPool();
    }


}
