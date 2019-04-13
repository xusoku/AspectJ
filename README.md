## AspectJ

AspectJ是一个面向切面的框架，它扩展了Java语言。AspectJ定义了AOP语法，它有一个专门的编译器用来生成遵守Java字节编码规范的Class文件.利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。

## 主要功能

数据埋点,日志记录,性能统计,安全控制,事务处理,异常处理等等

## 目的（为什么要用AspectJ）

将日志记录，性能统计，安全控制，事务处理，异常处理等代码从业务逻辑代码中划分出来，通过对这些行为的分离，我们希望可以将它们独立到非指导业务逻辑的方法中，进而改变这些行为的时候不影响业务逻辑的代码。

## 原理

AspectJ在代码的编译期间扫描目标程序，根据切点（PointCut）匹配,将开发者编写的Aspect程序编织（Weave）到目标程序的.class文件中，对目标程序作了重构（重构单位是JoinPoint），目的就是建立目标程序与Aspect程序的连接（获得执行的对象、方法、参数等上下文信息），从而达到AOP的目的。

## Gradle 配置示例

要引入AspectJ到Android工程中，最重要的就是两个包：

//在buildscript中添加该编织器，gradle构建时就会对class文件进行编织
//在buildscript中添加该工具包，在构建工程的时候执行一些任务：打日志等
```Java
classpath 'org.aspectj:aspectjtools:1.8.11'
classpath 'org.aspectj:aspectjweaver:1.8.9'
```
//在dependencies中添加该依赖，提供@AspectJ语法
```Java
compile 'org.aspectj:aspectjrt:1.8.9'
```
//在app中的gradle

```Java
import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main
```
//打印gradle日志
```Java
android.applicationVariants.all { variant ->
    JavaCompile javaCompile = variant.javaCompile
    javaCompile.doLast {
        String[] args = ["-showWeaveInfo",
                         "-1.5",
                         "-inpath", javaCompile.destinationDir.toString(),
                         "-aspectpath", javaCompile.classpath.asPath,
                         "-d", javaCompile.destinationDir.toString(),
                         "-classpath", javaCompile.classpath.asPath,
                         "-bootclasspath", project.android.bootClasspath.join(
                File.pathSeparator)]

        MessageHandler handler = new MessageHandler(true);
        new Main().run(args, handler)

        def log = project.logger
        for (IMessage message : handler.getMessages(null, true)) {
            switch (message.getKind()) {
                case IMessage.ABORT:
                case IMessage.ERROR:
                case IMessage.FAIL:
                    log.error message.message, message.thrown
                    break;
                case IMessage.WARNING:
                case IMessage.INFO:
                    log.info message.message, message.thrown
                    break;
                case IMessage.DEBUG:
                    log.debug message.message, message.thrown
                    break;
            }
        }
    }
}
```

## AspectJ官网配置
http://fernandocejas.com/2014/08/03/aspect-oriented-programming-in-android/

## 语法

### 切面——Aspect
  在AOP中切面是标记执行被切点标记方法的逻辑处理。就是切点要处理的具体逻辑在切面这个模块。

### 连接点——JoinPoint

它是切面插入执行应用程序的地方，他能被方法调用，也能新增方法，JoinPoint是一个执行链，每一个JoinPoint是一个单独的闭包，在执行的时候将上下文环境赋予闭包执行方法体逻辑。 

AspectJ的joinpoint有如下：

	method call 函数调用
	method execution 函数执行
	constructor call 构造函数调用
	constructor execution 构造函数执行
	field get 获取某个变量
	field set 设置某个变量
	pre-initialization Object在构造函数中做得一些工作。
	initialization Object在构造函数中做得工作
	static initialization 类初始化
	handler 异常处理，比如try catch(xxx)中，对应catch内的执行
	advice execution 里面有3个标记（Around Before After）

### 切点——PointCut

切点的声明决定需要切割的JoinPoint的集合，Pointcut可以控制你把哪些advice应用于JoinPoint上去，通常通过正则表达式来进行匹配应用，决定了那个jointpoint会获得通知。分为call、execution、target、this、within等关键字等。

	within(TypePattem) TypePattern标示package或者类。TypePatter可以使用通配符
	withincode(ConstructorSignaturelMethodSignature) 表示某个构造函数或其他函数执行过程中涉及到的JPoint
	cflow(pointcuts) cflow是call flow的意思,cflow的条件是一个pointcut
	cflowbelow(pointcuts)  表示调用pointcuts函数时所包含的JPoint,不包括pointcuts这个JPoint本身
	this(Type)  JPoint的this对象是Type类型
	target(Type) JPoint的target对象是Type类型
	args(TypeSignature) 用来对JPoint的参数进行条件搜索的

## 匹配规则 
（1）类型匹配语法 
 	类型匹配的通配符：
	*：匹配任何数量字符； 
	..：匹配任何数量字符的重复，如在类型模式中匹配任何数量子包；而在方法参数模式中匹配任何数量参数。 
	+：匹配指定类型的子类型；仅能作为后缀放在类型模式后边。 
	AspectJ使用 且（&&）、或（||）、非（！）来组合切入点表达式。
    
（2）匹配模式 
	call(<注解？> <修饰符?> <返回值类型> <类型声明?>.<方法名>(参数列表) <异常列表>？)

	精确匹配
	//表示匹配 com.sunmi.MainActivity类中所有被@Describe注解的public void方法。
	@Pointcut("call(@Describe public void com.sunmi.MainActivity.init(Context))")
	public void pointCut(){}

	单一模糊匹配
	//表示匹配 com.sunmi.MainActivity类中所有被@Describe注解的public void方法。
	@Pointcut("call(@Describe public void com.sunmi.MainActivity.*(..)) ")
	public void pointCut(){}

	//表示匹配调用Toast及其子类调用的show方法，不论返回类型以及参数列表，并且该子类在以com.sunmi开头的包名内
	@Pointcut("call(* android.widget.Toast+.show(..)) && (within(com.sunmi..*))")
	public void toastShow() {
	}

	组合模糊匹配
	//表示匹配任意Activity或者其子类的onStart方法执行，不论返回类型以及参数列表，且该类在com.sunmi包名内
	@Pointcut("execution(* *..Activity+.onStart(..))&& within(com.sunmi.*)")
	public void onStart(){}

 ## 示例代码
 
 ### 有注解

声明一个注解 AopPoint

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface AopPoint {
        String value();
        int type() default 0;
    }

在Activity 中，定义了2个按钮的点击事件，在方法上都标记注解，指定了value 和type。

    @AopPoint(value = "首页点击",type = 1)
    public void doFunc1(View view) {
        SystemClock.sleep(1000);
    }

    @AopPoint("分类点击")
    public void doFunc2(View view) {
        SystemClock.sleep(1000);
    }

编写一个切面类

    @Aspect
    public class AopAspect {
    @Pointcut("execution(@com.example.davis.aspectdemo.AopPoint * *(..))")
    public void aopCut(){

    }
    @Around("aopCut()")
    public Object dealMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature= (MethodSignature) joinPoint.getSignature();
        AopPoint aopPoint=signature.getMethod().getAnnotation(AopPoint.class);

        String value=aopPoint.value();
        int type=aopPoint.type();

        TimeTool timeTool=new TimeTool();
        timeTool.start();

        Object result=joinPoint.proceed();

        timeTool.stop();
        Log.e("aopCut",value+" 执行时间="+timeTool.getTotalTimeMillis() +" type类型是"+type);

        return result;
    }
    }
结果
![png](https://github.com/xusoku/AspectJ/blob/master/result.jpg)



### 无注解

1、这个是扫描Activity 中onCreate方法的调用

    @Aspect
    public class FuncTAspect {

    @Before("execution(* android.app.Activity.onCreate(..))")
    public void onActivityMethodBefore(JoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getSignature().toString();

        Log.e("FuncTAspect", "onActivityMethodBefore: " + key+"\n"+joinPoint.getThis());
    }
    }

如果把onCreate改成通配符* 
android.app.Activity.onCreate(..) 改成android.app.Activity.*(..)

结果
![png](https://github.com/xusoku/AspectJ/blob/master/saomiao.png)

2、捕获catch异常 

    @Aspect
    public class ExceptionHandleAspect {

    private static final String TAG = "ExceptionHandleAspect";

    /**
     * 截获空指针异常
     *
     * @param e
     */
    @Pointcut("handler(java.lang.Exception)&&args(e)")
    public void handle(Exception e) {
    }

    /**
     * 在catch代码执行之前做一些处理
     *
     * @param joinPoint
     * @param e         异常参数
     */
    @Before(value = "handle(e)", argNames = "e")
    public void handleBefore(JoinPoint joinPoint, Exception e) {
        Log.e(TAG, joinPoint.getSignature().toLongString() + " handleBefore() :" + e.toString())；
    }
    }

在方法里制造一个Null指针

    public void doFunc1(View view) {
        try {
            AppItem appItem=null;
            appItem.number=2;
        }catch (Exception e){}

    }


 结果
  ![png](https://github.com/xusoku/AspectJ/blob/master/exception.jpg)

 

