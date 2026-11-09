package com.lab2.parser;

import com.lab2.model.Device;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class SaxDeviceParser extends AbstractDeviceParser {

    public SaxDeviceParser() {
        super("SAX");
    }

    @Override
    protected List<Device> readDevices(String xmlPath)
            throws ParserConfigurationException, SAXException, IOException {
        DeviceHandler handler = new DeviceHandler();
        SAXParserFactory.newInstance().newSAXParser().parse(new File(xmlPath), handler);
        return handler.getDevices();
    }
}