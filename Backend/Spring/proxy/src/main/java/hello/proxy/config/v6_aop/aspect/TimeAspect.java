package hello.proxy.config.v6_aop.aspect;

import hello.proxy.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class TimeAspect {

    @Around("execution(* hello.proxy.app..*(..))")
    public Object measureMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String message = joinPoint.getSignature().toShortString();
        long startTime = System.currentTimeMillis();

        // 로직 호출
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        log.info("method={} exec {}ms", message, duration);

        return result;
    }

}
