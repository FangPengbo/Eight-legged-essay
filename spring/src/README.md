
# Spring

## ApplicationContext refresh步骤

1. prepareRefresh - 做好准备工作
    1. 这一步创建和准备了 Environment对象，主要是准备好spring要用到的键值信息
2. obtainFreshBeanFactory - 创建或获取BeanFactory
3. prepareBeanFactory - 准备BeanFactory
    1. 完善BeanFactory
    2. 给各种成员变量赋值，一些类型转换器，内置的beanPostProcessor
4. postProcessBeanFactory - 子类扩展BeanFactory
    1. 这一步空实现，实现的子类可以实现，用到了模板模式，父类定义步骤，子类实现方法
5. invokeBeanFactoryPostProcessors - 后处理器扩展BeanFactory
    1. 这一步可以用来补充或者修改BeanDefinition信息，
    2. ConfigurationClassProcessor - 解析 @Configuration、@Bean、@Import、@PropertySource等注解
6. registerBeanPostProcessors - 准备Bean后处理器
    1. 注册bean后置处理器，可以工作在 bean的实例化、依赖注入、初始化阶段
    2. AutowiredAnnotationBeanPostProcessor 功能有：解析 @Autowired、@Value注解
    3. CommonAnnotationBeanPostProcessor 功能有：解析@Resource、@PostConstruce、@PreDestory注解
    4. AnnotationAwareAspectJAutoProxyCreator功能有：为符合切点的目标 bean自动创建代理
7. initMessageSource - 为 ApplicationContext 提供国际化功能
8. initApplicationEventMulticaster- 为ApplicationContext 提供事件发布器
    1. 用来发布事件的广播器
    2. 从容器中找到applicaitonEventMulticaster的bean作为事件广播器，若没有，也会新建默认的事件广播器
    3. 可以调用ApplicationContext.publishEvent来发布事件
9. onRefresh - 留给子类扩展
    1. springboot在这一步实现了内嵌webserver
10. registerListeners - 为ApplicationContext 准备监听器、
    1. 用来接收事件
    2. 一部分监听器是事先编程添加的，另一部分监听器来自容器中的bean、还有一部分来自@EventListener的解析
    3. 实现ApplicationListener接口，重写其中 onApplicationEvent(E e)方法即可
11. finishBeanFactoryInitialization - 初始化单例Bean，执行Bean后处理器扩展
    1. conversionService也是一套转换机制，作为对PropertyEditor补充
    2. 内嵌值解析器用来解析 @Value中的${}，借用的是Enviroment的功能
    3. 单例池用来缓存所有单例对象、对象的创建都分三个阶段，每一个阶段都有不同的bean后处理器参与进来扩展功能
12. finishRefresh - 准备生命周期管理器，发布ContextRefreshed事件
    1. 用来控制容器内需要生命周期管理的bean
    2. 容器中有名称为lifecycleProcessor的bean就用它，否则创建默认的生命周期容器管理器
    3. 调用context的start，即可触发所有实现LifeCycle接口 bean的start
    4. 调用context的stop，即可触发所有实现LifeCycle接口的bean的stop
## Spring bean的生命周期

1. 处理名称，检查缓存
    1. 先把别名解析为实际名称，再进行后续处理
    2. 若要FactoryBean本身，需要使用 & 名称获取
    3. singletonObjects 是一级缓存，存放单例成品对象
    4. singletonFactories 是三级缓存，存放单例工厂
    5. earlySingletonObjects是二级缓存，存放单例工程成品，可称为提前单例对象
2. 检查父工厂
    1. 优先找子容器的bean，找到了直接返回，找不到继续到父容器找
    2. 父子容器的bean名称可以重复
3. 检查DependsOn
    1. 要显示控制无依赖关系的bean生成顺序，可以使用 A DependsOn B 注解，在B生成之后再生成A
4. 按Scope创建bean
    1. scope 理解为从 xxx范围内找到这个bean更加贴切
    2. 创建singleton 表示从单例池范围内获取 bean，如果没有，则创建并放入单例池
    3. 创建prototype 表示从不缓存bean，每次都创建新的
    4. 创建其它scope  request scope 表示从request对象范围内获取bean，如果没有，则创建并放入request域中
5. 创建bean
    1. 创建bean实例
        1. AutowiredAnnotationBeanPostProcessor 选择构造
        2. 采用默认构造 反射
    2. 依赖注入
        1. AutowriedAnnotationBeanPostProcessor 注解匹配
        2. CommonAnnotationBeanPostProcessor 注解匹配
        3. resolveDependency
        4. AUTOWIRE_BY_NAME 根据名字匹配
        5. AUTOWIRE_BY_TYPE 根据类型匹配
        6. applyPropertyValues 精确指定
    3. 初始化
        1. 内置Aware接口的装配
        2. 扩展Aware接口的装配
        3. @PostConstruce执行
        4. 实现initializingBean接口执行
        5. initMethod
        6. 创建aop代理
    4. 登记可销毁bean
        1. 如果实现了DisposableBean 或 AutoCloseable接口，则为可销毁bean
        2. 如果自定义了destroyMethod，则为可销毁bean
        3. 如果采用了@Bean 没有指定 destroyMethod，则采用自动推断方式获取销毁方法名（close，shutdown）
        4. 如果有 @PreDestroy 标注的方法
        5. singleton scope的可销毁bean会存储在beanFactory的成员当中
6. 类型转换
    1. 如果getBean的 requiredType 参数与实际得到的对象类型不同，会尝试进行类型转换
7. 销毁bean
    1. singleton bean的销毁 在ApplicationContext.close 时，此时会找到所有DisposableBean的名字，逐一销毁
    2. 自定义scope bean的销毁在作用域对象周期结束时
    3. prototype bean的销毁可以通过自己手动调用 AutowireCapableBeanFactory.destroyBean 方法执行销毁
    4. 同一bean中不同形式销毁方法的调用次序
        1. 优先后处理器销毁 即@PreDestroy
        2. 其次 DisposableBean接口销毁
        3. 最后destroyMethod 销毁

## Spring 事物失效的场景及原因

- 抛出检查异常导致事物不能正常回滚

    - 原因：Spring默认指挥回滚非检查异常 RuntimeException、Error
    - 解法：配置 rollbackFor属性为 Exception

- 业务方法内自己 try-catch 异常导致事物不能正确回滚

    - 原因：事物通知只有捉到了目标抛出的异常，才能进行后续的回滚处理，如果目标自己处理掉异常，事物通知无法知悉
    - 解法1：异常原样抛出
    - 解法2：手动设置TransactionStatus.setRollbackOnly()

- aop切面顺序导致事物不能正常回滚

    - 原因：事物切面优先级最低，但如果自定义的切面优先级和他一样，则还是自定义切面在内层，这时若自定义切面没有正确抛出异常
    - 解法：异常原样抛出

- 非public 方法导致的事物失效

    - 原因：SPring为方法创建代理、添加事物通知、前提条件都是该方法是public的
    - 解法：改为public方法

- 父子容器导致事物失效

    - 原因：子容器扫描范围更大，把未加事物配置的service扫描进来
    - 解法1：各扫各的，不要图方便
    - 解法2：不要用父子容器，所有bean放在同一个容器中

- 调用本类方法导致传播行为失效

    - 原因：本类方法调用不经过代理，因此无法增强
    - 解法1：依赖注入自己来调用
    - 解法2：通过AopContext拿到代理对象，来调用
    - 解法3：通过CTW、LTW实现功能增强

- @Transactional方法导致的 synchronized失效

    - 原因：synchronized 保证的仅是目标方法的原子性，环绕目标方法的还有commit等操作，他们并未处于sync块内

    - 解法1：synchronized范围应扩大至代理方法调用

- @Transactional 没有保证原子行为

    - 解法：事务的原子性仅涵盖 insert、update、delete、select ... for update语句，select 方法并不阻塞
