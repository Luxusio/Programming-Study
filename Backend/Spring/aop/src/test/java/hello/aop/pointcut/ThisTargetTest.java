package hello.aop.pointcut;

import hello.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Target;

@Slf4j
@Import(ThisTargetTest.ThisTargetAspect.class)
//@SpringBootTest(properties = "spring.aop.proxy-target-class=true") // CGLIB
@SpringBootTest(properties = "spring.aop.proxy-target-class=false") // JDK Dynamic Proxy
public class ThisTargetTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ThisTargetAspect {

        // 부모 타입 허용
        @Before("this(hello.aop.member.MemberService)")
        public void doThisInterface(JoinPoint joinPoint) {
            log.info("[this-interface] {}", joinPoint.getSignature());
        }

        // 부모 타입 허용
        @Before("target(hello.aop.member.MemberService)")
        public void doTargetInterface(JoinPoint joinPoint) {
            log.info("[target-interface] {}", joinPoint.getSignature());
        }

        // 부모 타입 허용
        @Before("this(hello.aop.member.MemberServiceImpl)")
        public void doThisClass(JoinPoint joinPoint) {
            log.info("[this-class] {}", joinPoint.getSignature());
        }

        // 부모 타입 허용
        @Before("target(hello.aop.member.MemberServiceImpl)")
        public void doTargetClass(JoinPoint joinPoint) {
            log.info("[target-class] {}", joinPoint.getSignature());
        }

    }

}
