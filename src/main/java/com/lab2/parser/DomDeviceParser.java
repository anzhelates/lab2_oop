package com.lab2.parser;

import com.lab2.builder.DeviceBuilder;
import com.lab2.model.Device;
import com.lab2.model.Group;
import com.lab2.model.Port;
import com.lab2.util.DeviceParseException;
import com.lab2.util.XmlTags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DomDeviceParser implements DeviceParser {
    private static final Logger logger = LogManager.getLogger(DomDeviceParser.class);

    @Override
    public List<Device> parse(String xmlPath) throws DeviceParseException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Document document = factory.newDocumentBuilder().parse(new File(xmlPath));

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
        DeviceBuilder builder = new DeviceBuilder()
                .id(element.getAttribute(XmlTags.ID))
                .name(text(element, XmlTags.NAME))
                .origin(text(element, XmlTags.ORIGIN))
                .price(new BigDecimal(text(element, XmlTags.PRICE)))
                .critical(Boolean.parseBoolean(text(element, XmlTags.CRITICAL)))
                .peripheral(Boolean.parseBoolean(text(element, XmlTags.PERIPHERAL)))
                .powerConsumption(Integer.parseInt(text(element, XmlTags.POWER_CONSUMPTION)))
                .cooler(Boolean.parseBoolean(text(element, XmlTags.COOLER)))
                .group(Group.fromXml(text(element, XmlTags.GROUP)));

        NodeList ports = element.getElementsByTagName(XmlTags.PORT);
        for (int i = 0; i < ports.getLength(); i++) {
            builder.addPort(Port.fromXml(ports.item(i).getTextContent().trim()));
        }
        return builder.build();
    }

    private String text(Element parent, String tag) {
        return parent.getElementsByTagName(tag).item(0).getTextContent().trim();
    }
}