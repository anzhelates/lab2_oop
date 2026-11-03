package com.lab2.parser;

import com.lab2.model.Device;
import com.lab2.util.DeviceParseException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserEquivalenceTest {
    private static final String VALID_XML = "src/test/resources/valid-devices.xml";

    @Test
    void allParsers_returnSameDevices() throws DeviceParseException {
        List<Device> dom = new DomDeviceParser().parse(VALID_XML);
        List<Device> sax = new SaxDeviceParser().parse(VALID_XML);
        List<Device> stax = new StaxDeviceParser().parse(VALID_XML);

        assertEquals(dom, sax);
        assertEquals(dom, stax);
    }
}