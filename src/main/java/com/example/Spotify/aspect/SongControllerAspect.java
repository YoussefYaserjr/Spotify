package com.example.Spotify.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class SongControllerAspect {

    /**
     * Pointcut for all SongController methods
     */
    @Pointcut("execution(* com.example.Spotify.controller.SongController.*(..))")
    public void songControllerMethods() {}

    /**
     * Before advice - logs method name and arguments before execution
     */
    // @Before("songControllerMethods()")
    // public void logBefore(JoinPoint joinPoint) {
    //     log.info("=== BEFORE: Method {} called with arguments: {}", 
    //         joinPoint.getSignature().getName(), 
    //         joinPoint.getArgs());
    // }

    // /**
    //  * After returning advice - logs when method completes successfully
    //  */
    // @AfterReturning(pointcut = "songControllerMethods()", returning = "result")
    // public void logAfterReturning(JoinPoint joinPoint, Object result) {
    //     log.info("=== AFTER RETURNING: Method {} completed successfully. Result: {}", 
    //         joinPoint.getSignature().getName(), 
    //         result);
    // }

    // /**
    //  * After throwing advice - logs when method throws an exception
    //  */
    // @AfterThrowing(pointcut = "songControllerMethods()", throwing = "exception")
    // public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
    //     log.error("=== AFTER THROWING: Method {} threw exception: {}", 
    //         joinPoint.getSignature().getName(), 
    //         exception.getMessage());
    // }

    /**
     * Around advice - logs execution time and complete method flow
     */
    @Around("songControllerMethods()")
    public Object logAroundMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        log.info(">>> START: Method {} execution started at {}", 
            joinPoint.getSignature().getName(), 
            startTime);

        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            log.info("<<< END: Method {} completed in {} ms", 
                joinPoint.getSignature().getName(), 
                executionTime);
            
            return result;
        } catch (Throwable throwable) {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            log.error("!!! ERROR: Method {} failed after {} ms with error: {}", 
                joinPoint.getSignature().getName(), 
                executionTime, 
                throwable.getMessage());
            
            throw throwable;
        }
    }
}
