# ArrayList

## 构造函数

- ArrayList()：无参构造
  - 初始化长度为0的数组
- ArrayList(int initialCapacity)：指定容量构造
  - 初始化指定容量大小的数组
- ArrayList(Collection c)：指定集合构造
  - 初始化c的大小数组，并copy过去

## 扩容机制

- add(Object o)
  - 无参构造时：第一次add，会初始化大小为**10** 的数组
  - 数组容量满时：当数组已经存满，再add的时候，会扩容当前数组数量的**1.5** 倍。(n + n>>1)
- addAll(Collection c)
  - 指定集合构造时：会在10和c的大小中取最大的一个数来扩容（Math.max(**10,c.size()**)）
  - 已经存在大小的数组时：会取原容量的**1.5**倍和实际元素大小来取最大的一个数来扩容（Math.max(**原容量 1.5倍，c.size()**)）

## fail-fast 与 fail-safe

- **fail-fast**：一旦发现遍历的同时其他人来修改，则立刻抛异常
  - **ArrayList** 是 fail-fast的典型代表，遍历的同时不能修改，抛出并发修改的异常

- **fail-safe**：发现遍历的同时其它人来修改，应当能有应对策略，例如牺牲一致性来完成整个遍历运行完成
  - **CopyOnWriteArrayList** 是 fail-safe 的典型代表，遍历的同时可以修改，原理是读写分离

# LinkedList

### ArrayList与LinkedList比较

- ArrayList
  - 基于数组，需要连续内存
  - 随机访问快（指根据下标访问）
  - 尾部插入、删除性能可以，其它部分插入、删除都会移动数据，因此性能会低
  - 可以利用cpu缓存，局部性原理
- LinkedList
  - 基于双向链表，无需连续内存
  - 随机访问慢（要沿着链表遍历）
  - 头尾插入删除性能高
  - 占用内存多

















