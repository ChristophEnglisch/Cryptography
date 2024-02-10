package de.cenglisch.sample.common.adapter.port.secondary.database.cryptography;

import de.cenglisch.sample.common.adapter.port.secondary.database.cryptography.encryption.EncryptionService;
import de.cenglisch.sample.common.adapter.port.secondary.database.cryptography.pseudonymization.DecryptionService;
import de.cenglisch.sample.common.adapter.port.secondary.database.cryptography.pseudonymization.DissolvePseudonimizationService;
import de.cenglisch.sample.common.adapter.port.secondary.database.cryptography.pseudonymization.PseudonymizationService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class CryptographyAspect {

    private final PseudonymizationService pseudonymizationService;
    private final DissolvePseudonimizationService dissolvePseudonimization;
    private final EncryptionService encryptionService;
    private final DecryptionService decryptionService;

    public CryptographyAspect(PseudonymizationService pseudonymizationService, DissolvePseudonimizationService dissolvePseudonimization, EncryptionService encryptionService, DecryptionService decryptionService) {
        this.pseudonymizationService = pseudonymizationService;
        this.dissolvePseudonimization = dissolvePseudonimization;
        this.encryptionService = encryptionService;
        this.decryptionService = decryptionService;
    }

    @Before("execution(* org.springframework.data.repository.CrudRepository.save(..)) && args(entity)")
    public void preSave(Object entity) {
        Arrays.stream(entity.getClass().getDeclaredFields())
                .forEach(field -> {
                    pseudonymizationService.processField(entity, field);
                    encryptionService.processField(entity, field);
                });
    }


    @Around("execution(* org.springframework.data.repository.Repository+.*(..)) && !execution(* org.springframework.data.repository.CrudRepository.save(..))")
    public Object preQueryReference(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return proceedingJoinPoint.proceed(pseudonymizationService.processQuery(
                proceedingJoinPoint.getArgs(),
                (MethodSignature) proceedingJoinPoint.getSignature()
        ));
    }

    @AfterReturning(pointcut = "execution(* org.springframework.data.repository.CrudRepository.save(..)) || execution(* org.springframework.data.repository.CrudRepository.findById(..))", returning = "result")
    public void postSingleObject(Object result) {
        dissolvePseudonimization.processEntity(result);
        decryptionService.processEntity(result);
    }

    @AfterReturning(pointcut = "execution(* org.springframework.data.repository.Repository+.*(..)) && !execution(* org.springframework.data.repository.CrudRepository.save(..)) && !execution(* org.springframework.data.repository.CrudRepository.findById(..))", returning = "result")
    public void postProcessIterable(Object result) {
        if (result instanceof Iterable<?>) {
            ((Iterable<?>) result).forEach(entity -> {
                dissolvePseudonimization.processEntity(entity);
                decryptionService.processEntity(entity);
            });
        }
    }
}
