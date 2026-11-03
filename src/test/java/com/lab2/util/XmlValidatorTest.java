package com.lab2.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class XmlValidatorTest {
    private static final String XSD = "src/main/resources/data/devices.xsd";

    @Test
    void validate_validXml_passes() {
        assertDoesNotThrow(() -> XmlValidator.validate("src/test/resources/valid-devices.xml", XSD));
    }

    @Test
    void validate_invalidXml_throwsParseException() {
        assertThrows(DeviceParseException.class,
                () -> XmlValidator.validate("src/test/resources/invalid-devices.xml", XSD));
    }

    @Test
    void validate_missingFile_throwsParseException() {
        assertThrows(DeviceParseException.class, () -> XmlValidator.validate("no-such-file.xml", XSD));
    }
}