package com.lab2.util;

import com.lab2.model.Device;

import java.util.Comparator;

public final class DeviceComparators {
    public static final Comparator<Device> BY_NAME =
            Comparator.comparing(Device::getName, String.CASE_INSENSITIVE_ORDER);

    public static final Comparator<Device> BY_ORIGIN =
            Comparator.comparing(Device::getOrigin, String.CASE_INSENSITIVE_ORDER);

    public static final Comparator<Device> BY_PRICE =
            Comparator.comparing(Device::getPrice);

    public static final Comparator<Device> BY_POWER_CONSUMPTION =
            Comparator.comparingInt(d -> d.getCharacteristics().getPowerConsumption());

    private DeviceComparators() {
    }
}