package io.github.des4j.des4j;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Resource {
    String name;
    int capacity;
    Queue<Entity> queue = new LinkedList<>();
    List<Entity> activeEntities = new ArrayList<>();

    public Resource(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public boolean allocate(Entity entity) {
        if (activeEntities.size() < capacity) {
            activeEntities.add(entity);
            return true;
        } else {
            queue.add(entity);
            return false;
        }
    }

    public Entity release() {
        if (!activeEntities.isEmpty()) {
            Entity releasedEntity = activeEntities.remove(0);
            if (!queue.isEmpty()) {
                Entity nextEntity = queue.poll();
                activeEntities.add(nextEntity);
            }
            return releasedEntity;
        }
        return null;
    }
}
