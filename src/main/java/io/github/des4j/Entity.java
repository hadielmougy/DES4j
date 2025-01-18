package io.github.des4j;


import java.util.Objects;

public class Entity {
    public final String name;
    public final int delay;

    public Entity(String name, int delay) {
        this.name = Objects.requireNonNull(name);
        this.delay = Util.validateNotNegative(delay);
    }
}
