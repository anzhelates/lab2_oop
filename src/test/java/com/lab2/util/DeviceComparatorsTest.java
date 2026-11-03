package com.lab2.util;

import com.lab2.builder.DeviceBuilder;
import com.lab2.model.Device;
import com.lab2.model.Group;
import com.lab2.model.Port;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeviceComparatorsTest {

    private final Device a = device("dev-001", "Monitor", "USA", "500", 30);
    private final Device b = device("dev-002", "keyboard", "China", "100", 5);
    private final Device c = device("dev-003", "Router", "Taiwan", "300", 10);

    private static Device device(String id, String name, String origin, String price, int power) {
        return new DeviceBuilder()
                .id(id)
                .name(name)
                .origin(origin)
                .price(new BigDecimal(price))
                .critical(false)
                .peripheral(false)
                .powerConsumption(power)
                .cooler(false)
                .group(Group.IO)
                .addPort(Port.USB)
                .build();
    }

    private List<String> sortedIds(Comparator<Device> comparator) {
        List<Device> devices = new ArrayList<>(List.of(a, b, c));
        devices.sort(comparator);
        return devices.stream().map(Device::getId).toList();
    }

    @Test
    void byName_sortsAlphabeticallyIgnoringCase() {
        assertEquals(List.of("dev-002", "dev-001", "dev-003"), sortedIds(DeviceComparators.BY_NAME));
    }

    @Test
    void byOrigin_sortsAlphabetically() {
        assertEquals(List.of("dev-002", "dev-003", "dev-001"), sortedIds(DeviceComparators.BY_ORIGIN));
    }

    @Test
    void byPrice_sortsAscending() {
        assertEquals(List.of("dev-002", "dev-003", "dev-001"), sortedIds(DeviceComparators.BY_PRICE));
    }

    @Test
    void byPowerConsumption_sortsAscending() {
        assertEquals(List.of("dev-002", "dev-003", "dev-001"),
                sortedIds(DeviceComparators.BY_POWER_CONSUMPTION));
    }
}