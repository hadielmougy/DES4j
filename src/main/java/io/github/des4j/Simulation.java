package io.github.des4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class Simulation {
    int globalClock = 0;
    PriorityQueue<Event> eventQueue = new PriorityQueue<>();
    Map<String, Resource> resources = new HashMap<>();

    public void addResource(Resource resource) {
        resources.put(resource.name, resource);
    }

    public void scheduleEvent(int time, Entity entity, Runnable action) {
        Event event = new Event(time, entity, action);
        eventQueue.add(event);
    }

    public void run(int maxTime) {
        while (!eventQueue.isEmpty() && globalClock <= maxTime) {
            Event event = eventQueue.poll();
            globalClock = event.time;
            event.action.run();
        }
    }

    public void processEntity(Entity entity, List<String> resourceNames) {
        if (resourceNames.isEmpty()) return; // No more resources to process

        String currentResourceName = resourceNames.get(0);
        Resource resource = resources.get(currentResourceName);

        Runnable action = () -> {
            if (resource.allocate(entity)) {
                System.out.println(globalClock + ": " + entity.name + " started using " + resource.name);
                scheduleEvent(globalClock + entity.interval, entity, () -> {
                    releaseResource(entity, currentResourceName);
                    // Move to the next resource in the list
                    processEntity(entity, resourceNames.subList(1, resourceNames.size()));
                });
            } else {
                System.out.println(globalClock + ": " + entity.name + " queued for " + resource.name);
            }
        };

        scheduleEvent(globalClock, entity, action);
    }

    public void releaseResource(Entity entity, String resourceName) {
        Resource resource = resources.get(resourceName);
        Entity releasedEntity = resource.release();
        if (releasedEntity != null) {
            System.out.println(globalClock + ": " + releasedEntity.name + " finished using " + resource.name);
        }
    }
}

