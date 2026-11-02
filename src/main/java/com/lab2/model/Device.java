package com.lab2.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Device {
    private final String id;
    private final String name;
    private final String origin;
    private final BigDecimal price;
    private final Characteristics characteristics;
    private final boolean critical;

    public Device(String id, String name, String origin, BigDecimal price,
                  Characteristics characteristics, boolean critical) {
        this.id = id;
        this.name = name;
        this.origin = origin;
        this.price = price;
        this.characteristics = characteristics;
        this.critical = critical;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getOrigin() { return origin; }
    public BigDecimal getPrice() { return price; }
    public Characteristics getCharacteristics() { return characteristics; }
    public boolean isCritical() { return critical; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device d)) return false;
        return critical == d.critical && id.equals(d.id) && name.equals(d.name)
                && origin.equals(d.origin) && price.compareTo(d.price) == 0
                && characteristics.equals(d.characteristics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, origin, price.stripTrailingZeros(), characteristics, critical);
    }

    @Override
    public String toString() {
        return "Device{id=" + id + ", name=" + name + ", origin=" + origin + ", price=" + price
                + ", " + characteristics + ", critical=" + critical + '}';
    }
}