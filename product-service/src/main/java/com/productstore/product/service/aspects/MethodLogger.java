package com.productstore.product.service.aspects;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Log4j2
public class MethodLogger {

    @Around("@annotation(com.productstore.product.service.aspects.Loggable)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        String methodName = signature.getMethod().getName();

        long start = System.currentTimeMillis();
//        Thread.sleep(1000);
        Object result = point.proceed();
        long end = System.currentTimeMillis();

        log.info("Method {} successfully executed in {} ms.", methodName, (end - start));
        return result;
    }
}