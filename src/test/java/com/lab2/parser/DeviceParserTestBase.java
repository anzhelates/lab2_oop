package com.lab2.parser;

import com.lab2.model.Device;
import com.lab2.model.Group;
import com.lab2.model.Port;
import com.lab2.util.DeviceParseException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class DeviceParserTestBase {
    static final String VALID_XML = "src/test/resources/valid-devices.xml";
    static final String INVALID_XML = "src/test/resources/invalid-devices.xml";

    abstract DeviceParser createParser();

    @Test
    void parse_validFile_returnsAllDevices() throws DeviceParseException {
        List<Device> devices = createParser().parse(VALID_XML);

        assertEquals(3, devices.size());
    }

    @Test
    void parse_validFile_mapsFieldsCorrectly() throws DeviceParseException {
        Device device = createParser().parse(VALID_XML).get(2);

        assertEquals("dev-003", device.getId());
        assertEquals("Dell P2422H Monitor", device.getName());
        assertEquals("China", device.getOrigin());
        assertEquals(0, new BigDecimal("7400").compareTo(device.getPrice()));
        assertFalse(device.isCritical());
        assertTrue(device.getCharacteristics().isPeripheral());
        assertEquals(20, device.getCharacteristics().getPowerConsumption());
        assertFalse(device.getCharacteristics().hasCooler());
        assertEquals(Group.MULTIMEDIA, device.getCharacteristics().getGroup());
        assertEquals(List.of(Port.USB, Port.COM), device.getCharacteristics().getPorts());
    }

    @Test
    void parse_invalidData_throwsParseException() {
        assertThrows(DeviceParseException.class, () -> createParser().parse(INVALID_XML));
    }

    @Test
    void parse_missingFile_throwsParseException() {
        assertThrows(DeviceParseException.class, () -> createParser().parse("no-such-file.xml"));
    }
}