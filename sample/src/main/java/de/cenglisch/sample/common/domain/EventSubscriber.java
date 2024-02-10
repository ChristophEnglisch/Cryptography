package de.cenglisch.sample.common.domain;

import java.util.function.Consumer;

public interface EventSubscriber {
    <T> void subscribe(Class<T> eventType, Consumer<T> action);

}
