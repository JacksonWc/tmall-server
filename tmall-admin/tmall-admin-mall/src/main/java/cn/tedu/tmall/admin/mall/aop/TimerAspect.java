package cn.tedu.tmall.admin.mall.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect // 标记当前类是一个切面类
@Component // 标记当前类是一个组件类，使得Spring管理此类的对象
public class TimerAspect {

    // 连接点（JoinPoint）：程序执行过程中的某个节点，可能是方法的调用，或抛出了异常
    // 切入点（Pointcut）：用于选择1个或多个连接点的表达式
    // --------------------------------------------------------------
    // 通知（Advice）注解
    // - @Around：环绕，可以包裹连接点的执行，你可以在执行连接点之前和之后编写你的代码
    // - @Before：在执行连接点之前
    // - @After：在执行连接点之后
    // - @AfterReturning：在连接点方法返回之后
    // - @AfterThrowing：在连接点方法抛出异常之后
    // --------------------------------------------------------------
    // 各通知的执行时间大致是以下这样：
    // @Around开始
    // try {
    //      @Before
    //      执行连接点
    //      @AfterReturning
    // } catch (Throwable e) {
    //      @AfterThrowing
    // } finally {
    //      @After
    // }
    // @Around结束
    // --------------------------------------------------------------
    // 【切入点表达式】
    // 切入点表达式的语法：execution([修饰符] 返回值类型 [包名]类名.方法名(参数列表))
    // 在切入点表达式中，可以使用通配符：
    // - 星号（*）：任意1次匹配
    // - 两个连续的小数点（..）：任意n（最少0次）次匹配，只允许用于参数列表或包名
    //       execution 是当前固定写法
    //                 ↓ 返回值类型
    //                   ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 包名
    //                                                    ↓ 类名
    //                                                      ↓ 方法名
    //                                                        ↓↓ 参数列表
    @Around("execution(* cn.tedu.tmall.admin.mall.service.*.*(..))")
    //     ↓↓↓↓↓↓ 必须使用Object类型
    //            ↓↓↓ 自定义的方法名
    //                ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 必须是ProceedingJoinPoint类型的参数（仅当@Around）
    //                                         ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 必须抛出异常
    public Object xxx(ProceedingJoinPoint pjp) throws Throwable {
        // 获取连接点的相关信息
        String targetClassName = pjp.getTarget().getClass().getName(); // 类型的名称
        String methodName = pjp.getSignature().getName();// 方法名
        Object[] args = pjp.getArgs(); // 参数列表
        System.out.println("连接点（方法）所属的类的名称：" + targetClassName);
        System.out.println("连接点（方法）的名称：" + methodName);
        System.out.println("连接点（方法）的参数列表：" + Arrays.toString(args));

        // 获取起始时间
        long start = System.currentTimeMillis();

        // 处理（调用）连接点，即：调用了切入点表达式所匹配的方法
        // 注意-1：调用proceed()方法的异常，必须抛出，不可以简单的使用try...catch捕获并处理（使用try...catch时，在catch内部再抛出异常也是可取的做法）
        // 注意-2：调用proceed()方法的返回值，表示的就是连接点方法的返回值，必须作为当前切面方法的返回值，否则，相当于执行了连接点方法，但是没有获取返回值
        Object result = pjp.proceed();

        // 获取结束时间
        long end = System.currentTimeMillis();

        // 输出
        System.out.println("执行耗时：" + (end - start) + "ms");

        // 返回
        return result;
    }

}
