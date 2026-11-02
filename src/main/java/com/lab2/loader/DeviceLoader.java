package com.lab2.loader;

import com.lab2.model.Device;
import com.lab2.parser.DeviceParser;
import com.lab2.util.DeviceParseException;
import com.lab2.util.XmlValidator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DeviceLoader {
    private final DeviceParser parser;

    public DeviceLoader(DeviceParser parser) {
        this.parser = parser;
    }

    public List<Device> load(String xmlPath, String xsdPath, Comparator<Device> comparator)
            throws DeviceParseException {
        XmlValidator.validate(xmlPath, xsdPath);
        List<Device> devices = new ArrayList<>(parser.parse(xmlPath));
        devices.sort(comparator);
        return devices;
    }
}