package com.lab2.parser;

import com.lab2.model.Device;
import com.lab2.util.DeviceParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

public abstract class AbstractDeviceParser implements DeviceParser {
    private final Logger logger = LogManager.getLogger(getClass());
    private final String name;

    protected AbstractDeviceParser(String name) {
        this.name = name;
    }

    @Override
    public final List<Device> parse(String xmlPath) throws DeviceParseException {
        try {
            List<Device> devices = readDevices(xmlPath);
            logger.info("{} parsed {} devices from {}", name, devices.size(), xmlPath);
            return devices;
        } catch (ParserConfigurationException | SAXException | XMLStreamException | IOException e) {
            throw new DeviceParseException(name + ": failed to parse " + xmlPath, e);
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new DeviceParseException(name + ": invalid device data in " + xmlPath, e);
        }
    }

    protected abstract List<Device> readDevices(String xmlPath)
            throws ParserConfigurationException, SAXException, XMLStreamException, IOException;
}