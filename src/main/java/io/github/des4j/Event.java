package io.github.des4j;

import java.util.Objects;

public class Event implements Comparable<Event> {
    public final int time;
    public final Entity entity;
    public final Runnable action;

    public Event(int time, Entity entity, Runnable action) {
        this.time   = Util.validateNotNegative(time);
        this.entity = Objects.requireNonNull(entity);
        this.action = Objects.requireNonNull(action);
    }

    @Override
    public int compareTo(Event other) {
        return Integer.compare(this.time, other.time);
    }
}
