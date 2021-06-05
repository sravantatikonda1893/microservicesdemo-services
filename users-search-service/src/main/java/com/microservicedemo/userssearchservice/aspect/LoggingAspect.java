package com.microservicedemo.userssearchservice.aspect;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author sravantatikonda
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {

  @Autowired
  private HttpServletRequest request;

  @Around("execution(* com.addicts.dating.recommendations.controller..*(..))" +
          "&& !execution(* com.addicts.dating.recommendations.controller.UserProfileController..*(..))")
  public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
    String className = methodSignature.getDeclaringType().getSimpleName();
    String methodName = methodSignature.getName();
    final StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    Object result = proceedingJoinPoint.proceed();
    stopWatch.stop();
    log.info(
        "Execution time of {}.{}:: {} ms from user: {} with IPAddress: {}, hostname: {}, and request URL: {}",
        className, methodName, stopWatch.getTotalTimeMillis(), request.getRemoteUser(),
        request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURL());
    return result;
  }
} 