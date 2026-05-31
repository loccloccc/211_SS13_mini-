package org.example.miniproject.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* org.example.miniproject.controller.*.*(..))")
    public void logMethodName(JoinPoint joinPoint) {
        log.info("Calling method: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(
            pointcut = "execution(* org.example.miniproject.service.*.*(..))",
            returning = "result"
    )
    public void logMethodResult(JoinPoint joinPoint, Object result) {
        log.info("Method {} returned: {}", joinPoint.getSignature().getName(), result);
    }

    @Around("execution(* org.example.miniproject.controller.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        log.info("Method {} executed in {} ms", joinPoint.getSignature().getName(), duration);
        return result;
    }
}
