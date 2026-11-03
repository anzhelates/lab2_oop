package com.lab2.loader;

import com.lab2.model.Device;
import com.lab2.parser.DomDeviceParser;
import com.lab2.util.DeviceComparators;
import com.lab2.util.DeviceParseException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeviceLoaderTest {
    private static final String VALID_XML = "src/test/resources/valid-devices.xml";
    private static final String INVALID_XML = "src/test/resources/invalid-devices.xml";
    private static final String XSD = "src/main/resources/data/devices.xsd";

    private final DeviceLoader loader = new DeviceLoader(new DomDeviceParser());

    @Test
    void load_validXml_sortsByPrice() throws DeviceParseException {
        List<Device> devices = loader.load(VALID_XML, XSD, DeviceComparators.BY_PRICE);

        assertEquals(List.of("dev-002", "dev-003", "dev-001"), ids(devices));
    }

    @Test
    void load_validXml_sortsByName() throws DeviceParseException {
        List<Device> devices = loader.load(VALID_XML, XSD, DeviceComparators.BY_NAME);

        assertEquals(List.of("dev-003", "dev-001", "dev-002"), ids(devices));
    }

    @Test
    void load_invalidXml_throwsParseException() {
        assertThrows(DeviceParseException.class,
                () -> loader.load(INVALID_XML, XSD, DeviceComparators.BY_PRICE));
    }

    private static List<String> ids(List<Device> devices) {
        return devices.stream().map(Device::getId).toList();
    }
}