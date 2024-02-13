package de.cenglisch.cryptography;

import de.cenglisch.cryptography.processor.PostProcessor;
import de.cenglisch.cryptography.processor.PreProcessor;
import de.cenglisch.cryptography.processor.QueryProcessor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Aspect
@Component
public class CryptographyAspect {

    private final QueryProcessor queryProcessor;
    private final Collection<PreProcessor> preProcessors;
    private final Collection<PostProcessor> postProcessors;

    public CryptographyAspect(QueryProcessor queryProcessor, Collection<PreProcessor> preProcessors, Collection<PostProcessor> postProcessors) {
        this.queryProcessor = queryProcessor;
        this.preProcessors = preProcessors;
        this.postProcessors = postProcessors;
    }

    @Before("execution(* org.springframework.data.repository.CrudRepository.save(..)) && args(entity)")
    public void preSave(Object entity) {
        preProcessors.forEach(preProcessor -> preProcessor.processEntity(entity));
    }


    @Around("execution(* org.springframework.data.repository.Repository+.*(..)) && !execution(* org.springframework.data.repository.CrudRepository.save(..))")
    public Object preQueryReference(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return proceedingJoinPoint.proceed(queryProcessor.processQuery(
                proceedingJoinPoint.getArgs(),
                (MethodSignature) proceedingJoinPoint.getSignature()
        ));
    }

    @AfterReturning(pointcut = "execution(* org.springframework.data.repository.CrudRepository.save(..)) || execution(* org.springframework.data.repository.CrudRepository.findById(..))", returning = "result")
    public void postSingleObject(Object result) {
        postProcessors.forEach(postProcessor -> postProcessor.processEntity(result));
    }

    @AfterReturning(pointcut = "execution(* org.springframework.data.repository.Repository+.*(..)) && !execution(* org.springframework.data.repository.CrudRepository.save(..)) && !execution(* org.springframework.data.repository.CrudRepository.findById(..))", returning = "result")
    public void postProcessIterable(Object result) {
        if (result instanceof Iterable<?>) {
            ((Iterable<?>) result).forEach(entity -> postProcessors.forEach(postProcessor -> postProcessor.processEntity(entity)));
        }
    }
}
