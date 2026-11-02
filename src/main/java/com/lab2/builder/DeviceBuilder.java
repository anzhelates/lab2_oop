package com.lab2.builder;

import com.lab2.model.Characteristics;
import com.lab2.model.Device;
import com.lab2.model.Group;
import com.lab2.model.Port;
import com.lab2.util.XmlTags;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DeviceBuilder {
    private String id;
    private String name;
    private String origin;
    private BigDecimal price;
    private Boolean critical;
    private Boolean peripheral;
    private Integer powerConsumption;
    private Boolean cooler;
    private Group group;
    private final List<Port> ports = new ArrayList<>();

    public DeviceBuilder id(String id) {
        this.id = id;
        return this;
    }

    public DeviceBuilder name(String name) {
        this.name = name;
        return this;
    }

    public DeviceBuilder origin(String origin) {
        this.origin = origin;
        return this;
    }

    public DeviceBuilder price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public DeviceBuilder critical(boolean critical) {
        this.critical = critical;
        return this;
    }

    public DeviceBuilder peripheral(boolean peripheral) {
        this.peripheral = peripheral;
        return this;
    }

    public DeviceBuilder powerConsumption(int powerConsumption) {
        this.powerConsumption = powerConsumption;
        return this;
    }

    public DeviceBuilder cooler(boolean cooler) {
        this.cooler = cooler;
        return this;
    }

    public DeviceBuilder group(Group group) {
        this.group = group;
        return this;
    }

    public DeviceBuilder addPort(Port port) {
        this.ports.add(port);
        return this;
    }

    public Device build() {
        requireNonNull(id, "id");
        requireNonNull(name, "name");
        requireNonNull(origin, "origin");
        requireNonNull(price, "price");
        requireNonNull(critical, "critical");
        requireNonNull(peripheral, "peripheral");
        requireNonNull(powerConsumption, "powerConsumption");
        requireNonNull(cooler, "cooler");
        requireNonNull(group, "group");
        if (ports.isEmpty()) {
            throw new IllegalStateException("Device must have at least one port");
        }

        Characteristics characteristics =
                new Characteristics(peripheral, powerConsumption, cooler, group, ports);
        return new Device(id, name, origin, price, characteristics, critical);
    }

    private static void requireNonNull(Object value, String field) {
        if (value == null) {
            throw new IllegalStateException("Field is not set: " + field);
        }
    }

    public DeviceBuilder setField(String tag, String value) {
        switch (tag) {
            case XmlTags.NAME -> name(value);
            case XmlTags.ORIGIN -> origin(value);
            case XmlTags.PRICE -> price(new BigDecimal(value));
            case XmlTags.CRITICAL -> critical(Boolean.parseBoolean(value));
            case XmlTags.PERIPHERAL -> peripheral(Boolean.parseBoolean(value));
            case XmlTags.POWER_CONSUMPTION -> powerConsumption(Integer.parseInt(value));
            case XmlTags.COOLER -> cooler(Boolean.parseBoolean(value));
            case XmlTags.GROUP -> group(Group.fromXml(value));
            case XmlTags.PORT -> addPort(Port.fromXml(value));
            default -> { }
        }
        return this;
    }
}