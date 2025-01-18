package io.github.des4j;


import java.util.Arrays;

public class Des4jApplication {

    public static void main(String[] args) {
        Simulation sim = new Simulation();

        Resource resource1 = new Resource("Resource1", 2, TimeRange.of(3, 5));
        Resource resource2 = new Resource("Resource2", 1, TimeRange.of(7));
        sim.addResource(resource1);
        sim.addResource(resource2);

        Entity entity1 = new Entity("Entity1", 1);
        Entity entity2 = new Entity("Entity2", 2);
        Entity entity3 = new Entity("Entity3", 1);

        sim.processEntity(entity1, Arrays.asList("Resource1", "Resource2"));
        sim.processEntity(entity2, Arrays.asList("Resource1", "Resource2"));
        sim.processEntity(entity3, Arrays.asList("Resource2", "Resource1"));

        sim.run(20);
    }

}
