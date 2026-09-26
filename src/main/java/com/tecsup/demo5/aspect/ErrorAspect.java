package com.tecsup.demo5.aspect;

import com.tecsup.demo5.service.AuditoriaService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ErrorAspect {

    @AfterThrowing(
            pointcut = "execution(* com.tecsup.demo5.service.*.*(..))",
            throwing = "ex"
    )
    public void capturarError(Exception ex) {
        System.out.println("❌ ERROR AOP: " + ex.getMessage());
    }
}


