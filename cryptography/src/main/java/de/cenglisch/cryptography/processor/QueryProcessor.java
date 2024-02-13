package de.cenglisch.cryptography.processor;

import org.aspectj.lang.reflect.MethodSignature;

public interface QueryProcessor {
    Object[] processQuery(Object[] args, MethodSignature methodSignature);
}
