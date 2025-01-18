package io.github.des4j;

import java.util.*;

public class Simulation {
    private final PriorityQueue<Event> eventQueue = new PriorityQueue<>();
    private final Map<String, Resource> resources = new HashMap<>();
    private int globalClock = 0;

    public void addResource(Resource resource) {
        resources.put(resource.name, Objects.requireNonNull(resource));
    }

    private void scheduleEvent(int time, Entity entity, Runnable action) {
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
        if (resourceNames.isEmpty()) {
            return;
        }

        String currentResourceName = resourceNames.get(0);
        Resource resource = resources.get(currentResourceName);

        Runnable action = () -> {
            if (resource.allocate(entity)) {
                System.out.println(globalClock + ": " + entity.name + " started using " + resource.name);
                scheduleEvent(calculateTime(entity, resource), entity, () -> {
                    releaseResource(currentResourceName);
                    processEntity(entity, resourceNames.subList(1, resourceNames.size()));
                });
            } else {
                System.out.println(globalClock + ": " + entity.name + " queued for " + resource.name);
                resource.notifyCapacity(() -> scheduleEvent(globalClock, entity, () -> processEntity(entity, resourceNames)));
            }
        };

        scheduleEvent(globalClock, entity, action);
    }

    private int calculateTime(Entity entity, Resource resource) {
        return globalClock + entity.delay + resource.processingTime();
    }

    private void releaseResource(String resourceName) {
        Resource resource = resources.get(resourceName);
        Entity releasedEntity = resource.release();
        if (releasedEntity != null) {
            System.out.println(globalClock + ": " + releasedEntity.name + " finished using " + resource.name);
        }
    }
}

