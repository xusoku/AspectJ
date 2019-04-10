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
