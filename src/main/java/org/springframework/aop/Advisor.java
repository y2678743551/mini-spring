package org.springframework.aop;

import org.aopalliance.aop.Advice;
/*
* Advisor接口，AOP通知的访问者
* @date 2026/9/14
* */
public interface Advisor {
    Advice getAdvice();
}
