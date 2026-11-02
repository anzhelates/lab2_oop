package com.lab2.model;

import java.util.List;
import java.util.Objects;

public class Characteristics {
    private final boolean peripheral;
    private final int powerConsumption;
    private final boolean cooler;
    private final Group group;
    private final List<Port> ports;

    public Characteristics(boolean peripheral, int powerConsumption, boolean cooler,
                           Group group, List<Port> ports) {
        this.peripheral = peripheral;
        this.powerConsumption = powerConsumption;
        this.cooler = cooler;
        this.group = group;
        this.ports = List.copyOf(ports);
    }

    public boolean isPeripheral() { return peripheral; }
    public int getPowerConsumption() { return powerConsumption; }
    public boolean hasCooler() { return cooler; }
    public Group getGroup() { return group; }
    public List<Port> getPorts() { return ports; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Characteristics c)) return false;
        return peripheral == c.peripheral && powerConsumption == c.powerConsumption
                && cooler == c.cooler && group == c.group && ports.equals(c.ports);
    }

    @Override
    public int hashCode() {
        return Objects.hash(peripheral, powerConsumption, cooler, group, ports);
    }

    @Override
    public String toString() {
        return "Characteristics{peripheral=" + peripheral + ", power=" + powerConsumption
                + ", cooler=" + cooler + ", group=" + group + ", ports=" + ports + '}';
    }
}