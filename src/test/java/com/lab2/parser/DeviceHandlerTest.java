package com.lab2.parser;

import com.lab2.model.Device;
import com.lab2.model.Group;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeviceHandlerTest {

    @Test
    void setField_withXmlTags_buildsDevice() {
        DeviceHandler handler = new DeviceHandler();
        handler.startDevice("dev-001");
        handler.setField("Name", "Mouse");
        handler.setField("Origin", "China");
        handler.setField("Price", "749.50");
        handler.setField("Critical", "false");
        handler.setField("Peripheral", "true");
        handler.setField("PowerConsumption", "2");
        handler.setField("Cooler", "false");
        handler.setField("Group", "Multimedia");
        handler.setField("Port", "USB");
        handler.setField("Port", "COM");
        handler.endDevice();

        List<Device> devices = handler.getDevices();

        assertEquals(1, devices.size());
        assertEquals("Mouse", devices.get(0).getName());
        assertEquals(Group.MULTIMEDIA, devices.get(0).getCharacteristics().getGroup());
        assertEquals(2, devices.get(0).getCharacteristics().getPorts().size());
    }

    @Test
    void setField_outsideDevice_isIgnored() {
        DeviceHandler handler = new DeviceHandler();

        handler.setField("Name", "Mouse");

        assertTrue(handler.getDevices().isEmpty());
    }

    @Test
    void setField_withInvalidPort_throwsIllegalArgument() {
        DeviceHandler handler = new DeviceHandler();
        handler.startDevice("dev-001");

        assertThrows(IllegalArgumentException.class, () -> handler.setField("Port", "XYZ"));
    }
}