package com.lab2.parser;

import com.lab2.model.Device;
import com.lab2.util.DeviceParseException;

import java.util.List;

public interface DeviceParser {
    List<Device> parse(String xmlPath) throws DeviceParseException;
}