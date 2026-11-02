package com.lab2.parser;

import com.lab2.builder.DeviceBuilder;
import com.lab2.model.Device;
import com.lab2.util.DeviceParseException;
import com.lab2.util.XmlTags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StaxDeviceParser implements DeviceParser {
    private static final Logger logger = LogManager.getLogger(StaxDeviceParser.class);

    @Override
    public List<Device> parse(String xmlPath) throws DeviceParseException {
        XMLInputFactory factory = XMLInputFactory.newInstance();

        try (InputStream in = new FileInputStream(xmlPath)) {
            XMLStreamReader reader = factory.createXMLStreamReader(in);
            try {
                List<Device> devices = readDevices(reader);
                logger.info("StAX parsed {} devices from {}", devices.size(), xmlPath);
                return devices;
            } finally {
                reader.close();
            }
        } catch (XMLStreamException | IOException e) {
            throw new DeviceParseException("StAX: failed to parse " + xmlPath, e);
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new DeviceParseException("StAX: invalid device data in " + xmlPath, e);
        }
    }

    private List<Device> readDevices(XMLStreamReader reader) throws XMLStreamException {
        List<Device> devices = new ArrayList<>();
        StringBuilder text = new StringBuilder();
        DeviceBuilder builder = null;

        while (reader.hasNext()) {
            switch (reader.next()) {
                case XMLStreamConstants.START_ELEMENT -> {
                    text.setLength(0);
                    if (XmlTags.DEVICE.equals(reader.getLocalName())) {
                        builder = new DeviceBuilder().id(reader.getAttributeValue(null, XmlTags.ID));
                    }
                }
                case XMLStreamConstants.CHARACTERS -> text.append(reader.getText());
                case XMLStreamConstants.END_ELEMENT -> {
                    String name = reader.getLocalName();
                    if (XmlTags.DEVICE.equals(name)) {
                        devices.add(builder.build());
                        builder = null;
                    } else if (builder != null) {
                        builder.setField(name, text.toString().trim());
                    }
                }
                default -> { }
            }
        }
        return devices;
    }
}