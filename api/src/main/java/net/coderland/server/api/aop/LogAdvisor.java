package net.coderland.server.api.aop;

import net.coderland.server.api.model.response.StockResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by zhangxin on 16/3/4.
 */

@Aspect
@Component
public class LogAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(LogAdvisor.class);

    @Pointcut("execution(* net.coderland.server.api.aop.LogPointcut.stockPointcut(..))")
    public void stockPointcut() {
    }

    @AfterReturning("stockPointcut()")
    public void afterReturningActionAnnotation(JoinPoint joinPoint) {
        StockResponse response = (StockResponse)joinPoint.getArgs()[0];
        logger.info("size: " + response.getTotal());
        logger.info("###AOP###");
    }
}
