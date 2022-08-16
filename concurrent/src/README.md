# 并发

## 线程

- 线程有哪些状态？
    - NEW-新建状态
    - RUNNABLE-可运行
    - BLOCKED-阻塞
    - WATING-等待
    - TIMED_WAITING-有时限的等待
    - TERMINATED-终结
- 线程状态之间的转换过程？
    - 线程创建好之后是**新建状态**
    - 调用线程的**start**方法 变成**可运行状态**
    - 在**可运行状态**时，获取锁失败后会变成**阻塞状态**
    - 在**阻塞状态**时，获取到锁成功则变成**可运行状态**
    - 在**可运行状态**时，获得锁但是不满足继续运行，调用**wait()**方法，变为**等待状态**
    - 在**等待状态**时，调用**notify()**方法，则先变为**阻塞**，再变为**可运行状态**
    - 在**可运行状态**时，调用**sleep**或者带有时长的**wait**方法，则变为**有时限的等待状态**
    - 当时间到或者调用**notify**时则先变为**阻塞**，再变为**可运行状态**
    - 当线程完成工作时，由可运行状态变为**终结状态**

- 为什么会有5种状态的说法？
    - 在操作系统层面，线程分为5种状态
    - 分为 新建、就绪、运行、阻塞、和终结
    - 当线程有机会分到CPU时间时，线程为**就绪**状态
    - 当线程分到CPU时间时，线程为**运行**状态
    - 当线程分不到CPU时间时，线程为**阻塞**状态
    - 阻塞状态又分为 IO阻塞、BLOCKED、WAITING、TIMED WAITING

## 线程池

**线程池的核心参数**

- corePoolSize：核心线程数目，最多保留的线程数
- maximumPoolSize：最大线程数目，核心线程+救急线程
- keepAliveTime：生存时间，针对救急线程执行完任务之后来说
- unit：时间单位，针对keepAliveTime参数
- workQueue：阻塞队列，核心线程满了之后，任务都提交到缓存队列中
- threadFactory：线程工厂，可以为线程创建时设置一些参数，例如线程名的规则
- handler：拒绝策略，当线程池满了，缓存队列也满了，针对新任务的一些策略
    - AbortPolicy：直接抛出异常让主线程感知
    - CallerRunsPolicy：让提交任务的线程自己执行
    - DiscardOldestPolicy：丢弃队列中等待最久的任务
    - DiscardPolicy：直接丢弃后边的任务

## sleep和wait的异同

- 共同点：wait(),wait(long)和sleep(long)的效果都是让当前线程暂时放弃CPU的使用权，进入阻塞状态
- 方法归属不同
    - sleep(long)是Thread的静态方法
    - wait()、wait(long)都是Object的成员方法，每个对象都有
- 醒来时机不同
    - 执行sleep(long) 和 wait(long) 的线程都会在等待相应毫秒后醒来
    - wait(long) 和 wait() 还可以被 notify唤醒，wait() 如果不唤醒就一直等下去
    - 他们都可以被**打断** 唤醒
- 锁特性不同
    - wait方法的调用必须先获取wait对象的锁，而sleep 则无此限制
    - wait方法执行后会释放对象锁，允许其它线程获得该对象锁（我放弃，你们可以用）
    - sleep 如果在 synchronized 代码块中执行，则不会释放对象锁（我放弃，你们也用不了）

## lock和synchronized的异同

- 语法层面
    - synchronized 是关键字，源码在jvm中，是用c++语言实现的
    - Lock是接口，源码由JDK提供，用java语言实现的
    - 使用 synchoronized 时，退出同步代码块锁会自动释放，而使用Lock时，需要手动调用 unlock方法来解锁
- 功能层面
    - 二者均属于悲观锁，都具备基本的互斥、同步、锁重入功能
    - Lock提供了许多 synchoronized 不具备的功能，例如获取等待状态、公平锁、可打断、可超时、多条件变量
    - Lock有适合不同场景的实现，如 ReentrantLock、ReentrantReadWriteLock
- 性能层面
    - 在没有竞争时，synchronized做了很多优化，如偏向锁、轻量级锁、性能是OK的
    - 在竞争激烈时，Lock的实现通常会提供更好的性能