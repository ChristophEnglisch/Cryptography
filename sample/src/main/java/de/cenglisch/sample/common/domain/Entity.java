package de.cenglisch.sample.common.domain;

import java.util.LinkedList;
import java.util.Queue;

public abstract class Entity {

    @SuppressWarnings("checkstyle:VisibilityModifier")
    protected final Queue<DomainEvent> domainEvents = new LinkedList<>();

    public final Queue<DomainEvent> domainEvents() {
        return domainEvents;
    }
}
