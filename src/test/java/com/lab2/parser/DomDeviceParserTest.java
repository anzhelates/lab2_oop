package com.lab2.parser;

class DomDeviceParserTest extends DeviceParserTestBase {

    @Override
    DeviceParser createParser() {
        return new DomDeviceParser();
    }
}