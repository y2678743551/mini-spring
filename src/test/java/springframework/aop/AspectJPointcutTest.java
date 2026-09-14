package springframework.aop;

import org.junit.Test;
import org.springframework.aop.aspectJ.AspectJExpressionPointcut;
import springframework.common.Hello;

public class AspectJPointcutTest {
    @Test
    public void test() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* springframework.common.Hello.sayHello())");

        System.out.println(pointcut.getClassFilter().matches(Hello.class));
        System.out.println(pointcut.getMethodMatcher().matches(Hello.class.getMethod("sayHello"), Hello.class));

    }
}
