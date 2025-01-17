package io.github.des4j.des4j;

class Event implements Comparable<Event> {
    int time;
    Entity entity;
    Runnable action;

    public Event(int time, Entity entity, Runnable action) {
        this.time = time;
        this.entity = entity;
        this.action = action;
    }

    @Override
    public int compareTo(Event other) {
        return Integer.compare(this.time, other.time);
    }
}
