
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
