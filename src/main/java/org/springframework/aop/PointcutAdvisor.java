package org.springframework.aop;

import org.springframework.aop.Pointcut;
/**
 * @date 2026/9/15
 */
public interface PointcutAdvisor extends Advisor {
    Pointcut getPointcut();
}
