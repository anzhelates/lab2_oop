package com.lab2.parser;

class StaxDeviceParserTest extends DeviceParserTestBase {

    @Override
    DeviceParser createParser() {
        return new StaxDeviceParser();
    }
}