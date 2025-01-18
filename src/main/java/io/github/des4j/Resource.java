package io.github.des4j;


import java.util.*;

public class Resource {
    public final String name;
    public final int capacity;
    private final List<Entity> activeEntities = new ArrayList<>();
    private final Queue<Runnable> waiting = new LinkedList<>();
    private final TimeRange timeRange;

    public Resource(String name, int capacity, TimeRange timeRange) {
        this.name       = Objects.requireNonNull(name);
        this.capacity   = Util.validateIsPositive(capacity);
        this.timeRange  = Objects.requireNonNull(timeRange);
    }

    public boolean allocate(Entity entity) {
        if (activeEntities.size() < capacity) {
            activeEntities.add(entity);
            return true;
        } else {
            return false;
        }
    }

    public Entity release() {
        if (!activeEntities.isEmpty()) {
            Entity entity = activeEntities.remove(0);
            if (!waiting.isEmpty()) {
                waiting.poll().run();
            }
            return entity;
        }
        return null;
    }

    public void notifyCapacity(Runnable runnable) {
        waiting.offer(runnable);
    }

    public int processingTime() {
        int start = timeRange.start;
        int end = timeRange.end;

        if (start == end) {
            return start;
        }
        return end + (int) (Math.random() * ((end - start) + 1));
    }
}
