package com.lab2.parser;

import com.lab2.builder.DeviceBuilder;
import com.lab2.model.Device;
import com.lab2.util.DeviceParseException;
import com.lab2.util.XmlTags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DomDeviceParser implements DeviceParser {
    private static final Logger logger = LogManager.getLogger(DomDeviceParser.class);

    @Override
    public List<Device> parse(String xmlPath) throws DeviceParseException {
        try {
            Document document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new File(xmlPath));

            NodeList nodes = document.getElementsByTagName(XmlTags.DEVICE);
            List<Device> devices = new ArrayList<>();
            for (int i = 0; i < nodes.getLength(); i++) {
                devices.add(buildDevice((Element) nodes.item(i)));
            }
            logger.info("DOM parsed {} devices from {}", devices.size(), xmlPath);
            return devices;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new DeviceParseException("DOM: failed to parse " + xmlPath, e);
        }
    }

    private Device buildDevice(Element element) {
        DeviceBuilder builder = new DeviceBuilder().id(element.getAttribute(XmlTags.ID));
        NodeList all = element.getElementsByTagName("*");
        for (int i = 0; i < all.getLength(); i++) {
            Node node = all.item(i);
            builder.setField(node.getNodeName(), node.getTextContent().trim());
        }
        return builder.build();
    }
}