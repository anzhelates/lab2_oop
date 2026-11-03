package com.lab2.builder;

import com.lab2.model.Device;
import com.lab2.model.Group;
import com.lab2.model.Port;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeviceBuilderTest {

    private DeviceBuilder fullBuilder() {
        return new DeviceBuilder()
                .id("dev-001")
                .name("Mouse")
                .origin("China")
                .price(new BigDecimal("749.50"))
                .critical(false)
                .peripheral(true)
                .powerConsumption(2)
                .cooler(false)
                .group(Group.IO)
                .addPort(Port.USB);
    }

    @Test
    void build_withAllFields_createsDevice() {
        Device device = fullBuilder().build();

        assertEquals("dev-001", device.getId());
        assertEquals("Mouse", device.getName());
        assertEquals("China", device.getOrigin());
        assertEquals(0, new BigDecimal("749.50").compareTo(device.getPrice()));
        assertFalse(device.isCritical());
        assertTrue(device.getCharacteristics().isPeripheral());
        assertEquals(2, device.getCharacteristics().getPowerConsumption());
        assertFalse(device.getCharacteristics().hasCooler());
        assertEquals(Group.IO, device.getCharacteristics().getGroup());
        assertEquals(1, device.getCharacteristics().getPorts().size());
    }

    @Test
    void build_withoutName_throwsIllegalState() {
        DeviceBuilder builder = new DeviceBuilder()
                .id("dev-001")
                .origin("China")
                .price(BigDecimal.TEN)
                .critical(false)
                .peripheral(true)
                .powerConsumption(2)
                .cooler(false)
                .group(Group.IO)
                .addPort(Port.USB);

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void build_withoutPorts_throwsIllegalState() {
        DeviceBuilder builder = new DeviceBuilder()
                .id("dev-001")
                .name("Mouse")
                .origin("China")
                .price(BigDecimal.TEN)
                .critical(false)
                .peripheral(true)
                .powerConsumption(2)
                .cooler(false)
                .group(Group.IO);

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void setField_withXmlTags_fillsBuilder() {
        DeviceBuilder builder = new DeviceBuilder().id("dev-001");
        builder.setField("Name", "Mouse")
                .setField("Origin", "China")
                .setField("Price", "749.50")
                .setField("Critical", "false")
                .setField("Peripheral", "true")
                .setField("PowerConsumption", "2")
                .setField("Cooler", "false")
                .setField("Group", "Multimedia")
                .setField("Port", "USB")
                .setField("Port", "COM");

        Device device = builder.build();

        assertEquals("Mouse", device.getName());
        assertEquals(Group.MULTIMEDIA, device.getCharacteristics().getGroup());
        assertEquals(2, device.getCharacteristics().getPorts().size());
    }

    @Test
    void setField_withInvalidPort_throwsIllegalArgument() {
        DeviceBuilder builder = new DeviceBuilder();

        assertThrows(IllegalArgumentException.class, () -> builder.setField("Port", "XYZ"));
    }
}