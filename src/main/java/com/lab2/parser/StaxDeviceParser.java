package com.lab2.parser;

import com.lab2.model.Device;
import com.lab2.util.XmlTags;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class StaxDeviceParser extends AbstractDeviceParser {

    public StaxDeviceParser() {
        super("StAX");
    }

    @Override
    protected List<Device> readDevices(String xmlPath) throws XMLStreamException, IOException {
        DeviceHandler handler = new DeviceHandler();
        try (InputStream in = new FileInputStream(xmlPath)) {
            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(in);
            try {
                readInto(reader, handler);
            } finally {
                reader.close();
            }
        }
        return handler.getDevices();
    }

    private void readInto(XMLStreamReader reader, DeviceHandler handler) throws XMLStreamException {
        StringBuilder text = new StringBuilder();

        while (reader.hasNext()) {
            switch (reader.next()) {
                case XMLStreamConstants.START_ELEMENT -> {
                    text.setLength(0);
                    if (XmlTags.DEVICE.equals(reader.getLocalName())) {
                        handler.startDevice(reader.getAttributeValue(null, XmlTags.ID));
                    }
                }
                case XMLStreamConstants.CHARACTERS -> text.append(reader.getText());
                case XMLStreamConstants.END_ELEMENT -> {
                    String name = reader.getLocalName();
                    if (XmlTags.DEVICE.equals(name)) {
                        handler.endDevice();
                    } else {
                        handler.setField(name, text.toString().trim());
                    }
                }
                default -> { }
            }
        }
    }
}