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

## volatile 能否保证线程安全

- 线程安全要考虑三个方面：可见性、有序性、原子性
  - 可见性指 ，一个线程对共享变量修改，另一个线程能看到最新的结果
  - 有序性指，一个线程代码按编写顺序执行
  - 原子性指，一个代码多行代码以一个整体运行，期间不能有其它线程的代码插队
- volatile能够保证共享变量的可见性与有序性，但不能保证原子性
- volatile原理是使用**内存屏障**来控制指令的重排序问题

- 可见性的问题：一个共享变量是加载在主存中，每一个线程在运行任务时会copy一份到自己的工作线程中，完成写操作之后，会写回到主存，因为JIT的存在，可能会导致某个热点线程中的共享变量直接被替换成常量，所以导致无法实时读取主存中的变量值，会导致一些可见性的问题
- volatile放置的位置是会影响代码的执行结果，可以详细看下**内存屏障**

## java中的悲观锁和乐观锁

- 悲观锁的代表是 synchronized 和 Lock锁
  - 其核心思想是【线程只有占有了锁，才会去操作共享变量，每次只有一个线程占锁成功，获取锁失败的线程，都得停下来等待】
  - 线程从运行到阻塞、再从阻塞到唤醒，涉及线程上下文切换，如果频繁发生，会影响性能
  - 实际上，线程在获取 synchronized 和 Lock锁时，如果锁已经被占用，都会做几次重试操作，减少阻塞的机会
- 乐观锁的代表是 AtomicInteger，使用cas(比较并替换) 来保证原子性
  - 其核心思想是【无需加锁，每次只有一个线程能成功修改共享变量，其它失败的线程不需要停止，不断重试直至成功】
  - 由于线程一直运行，不需要阻塞，因此不涉及线程上下文切换
  - 它需要多核CPU支持，且线程数不应超过cpu核数

## Hashtable vs ConcurrentHashMap

- Hashtable与ConcurrentHashMap都是线程安全的Map集合
- Hashtable并发度低，整个HashMap对应一把锁，同一时刻，只能由一个线程操作它
- 1.8之前，ConcurrentHashMap 使用了 Segment+数组+链表的结构，每个Segment对应一把锁，如果多个线程访问不同的Segment，则不会冲突
- 1.8开始，ConcurrentHashMap 将数组的每个头节点作为锁，如果多个线程访问的头节点不同，则不会冲突

## 谈一谈对ThreadLocal的理解

- ThreadLocal 可以实现【资源对象】的线程隔离，让每个线程各用各的【资源对象】，避免争用引发的线程安全问题
- ThreadLocal 同时实现了线程内的资源共享
- 其原理是，每个线程内有一个 ThreadLocalMap 类型的成员变量，用来存储资源对象
  - 调用 set 方法，就是以 ThreadLocal 自己作为 key，资源对象作为 value，放入当前线程的 ThreadLocalMap 集合中
  - 调用 get 方法，就是以 ThreadLocal 自己作为 key，到当前线程中查找关联的资源值
  - 调用 remove 方法，就是以 ThreadLocal 自己作为 key，移除当前线程关联的资源值
- 为什么 ThreadLocalMap 中的 key （即 ThreadLocal）要设计为弱引用？
  - Thread 可能需要长时间运行，如果 key 不再引用，需要在内存不足 GC 时释放其占用的内容
  - 但是 GC 仅是让 key 的内存释放，后续还要根据 key  是否为 null 来进一步释放值的内存，释放时机有
    - get key 发现 null key
    - set key ，会使用启发式扫描，清楚临近的 null key，启发次数与元素个数，是否发现 null key 有关
    - remove ，一般使用 ThreadLocal 时 都会把它作为静态变量，因此 GC 无法回收
- ThreadLocal使用场景
  - 用作用户信息的保存，request进来，拦截器执行保存在当前线程内用户的信息，在业务执行的时候只需要调用get方法即可获取到用户的信息
  - 保存一些线程不安全的工具类，每个线程各用自己的工具类
  - spring中dao层，连接数据库，为每个线程保存一个数据库连接来进行读写服务


## D线程依赖ABC线程工作，如何实现？

- 使用Join方法
  - 在D线程中执行ABC线程的join方法，等待三个方法都执行完毕，才执行D线程中的逻辑
- 使用CountDownLatch
  - 初始化值为3的CountDownLatch
  - 在每个线程执行完业务方法的时候对CountDownLatch执行减一操作
  - 在主线程中调用awit方法等待countDownLatch值为0再执行主线程的业务方法

