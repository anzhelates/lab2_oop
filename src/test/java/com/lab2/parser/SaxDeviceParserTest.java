package com.lab2.parser;

class SaxDeviceParserTest extends DeviceParserTestBase {

    @Override
    DeviceParser createParser() {
        return new SaxDeviceParser();
    }
}