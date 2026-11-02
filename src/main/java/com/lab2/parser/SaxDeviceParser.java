package com.lab2.parser;

import com.lab2.builder.DeviceBuilder;
import com.lab2.model.Device;
import com.lab2.util.DeviceParseException;
import com.lab2.util.XmlTags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaxDeviceParser implements DeviceParser {
    private static final Logger logger = LogManager.getLogger(SaxDeviceParser.class);

    @Override
    public List<Device> parse(String xmlPath) throws DeviceParseException {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();

            DeviceHandler handler = new DeviceHandler();
            factory.newSAXParser().parse(new File(xmlPath), handler);

            List<Device> devices = handler.getDevices();
            logger.info("SAX parsed {} devices from {}", devices.size(), xmlPath);
            return devices;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new DeviceParseException("SAX: failed to parse " + xmlPath, e);
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new DeviceParseException("SAX: invalid device data in " + xmlPath, e);
        }
    }

    private static class DeviceHandler extends DefaultHandler {
        private final List<Device> devices = new ArrayList<>();
        private final StringBuilder text = new StringBuilder();
        private DeviceBuilder builder;

        List<Device> getDevices() {
            return devices;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            text.setLength(0);
            if (XmlTags.DEVICE.equals(qName)) {
                builder = new DeviceBuilder().id(attributes.getValue(XmlTags.ID));
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            text.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if (XmlTags.DEVICE.equals(qName)) {
                devices.add(builder.build());
            } else if (builder != null) {
                builder.setField(qName, text.toString().trim());
            }
        }
    }