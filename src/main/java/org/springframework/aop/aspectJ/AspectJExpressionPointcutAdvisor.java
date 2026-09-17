package org.springframework.aop.aspectJ;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
/**
 * 支持AspectJ表达式的切点表达式顾问
 * @date 2026/9/17
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor{
    private AspectJExpressionPointcut pointcut;
    private Advice advice;
    private String expression;
    public void setExpression(String expression) {
        this.expression = expression;
    }
    @Override
    public Pointcut getPointcut() {
        //如果为空则不匹配
        if (expression==null)
            pointcut = new AspectJExpressionPointcut("execution(* *.*(..))&&!execution(* *.*(..))");
        if(pointcut==null)
            pointcut = new AspectJExpressionPointcut(expression);
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
