import jdk.internal.misc.Unsafe;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Fang Pengbo
 * @Date: 2022/08/17/21:42
 * @Description:
 */
public class SyncVsCas {

    static final Unsafe U = Unsafe.getUnsafe();
    static final long BALANCE = U.objectFieldOffset(Account.class,"balance");

    static class Account{
        volatile int balance = 10;
    }


    public static void main(String[] args) {
        cas();
        sync();
    }


    /**
     * 悲观锁，抢占失败的就会放弃进入阻塞状态，等待锁释放，通知抢占，成功则执行，失败则继续进入阻塞状态
     */
    public static void sync(){
        Account account = new Account();
        new Thread(()->{
            synchronized (account){
                int old = account.balance;
                int n = old + 5;
                account.balance = n;
            }
        }).start();
        new Thread(()->{
            synchronized (account){
                int old = account.balance;
                int n = old - 5;
                account.balance = n;
            }
        }).start();

        System.out.println(account.balance);
    }


    /**
     * 乐观锁，使用volatile + cas 来实现
     */
    public static void cas(){
        Account account = new Account();
        int o = account.balance;
        int n = o + 5;
        //不断去尝试，获取共享变量的old值，与最新值比较，如果相等，就做写入操作，不相等就继续获取
        while (true){
            if(U.compareAndSetInt(account,BALANCE,o,n)){
                break;
            }
        }
        System.out.println(account.balance);
    }


}
