package de.cenglisch.sample.common.adapter.port.secondary.event;

import de.cenglisch.sample.common.domain.EventSubscriber;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class EventSubscriberAdapter implements EventSubscriber, ApplicationListener<ApplicationEvent> {

    private final Map<Class<?>, Consumer<?>> subscribers = new HashMap<>();

    @Override
    public <T> void subscribe(Class<T> eventType, Consumer<T> action) {
        subscribers.put(eventType, action);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        Consumer action = subscribers.get(event.getClass());
        if (action != null) {
            action.accept(event);
        }
    }
}
