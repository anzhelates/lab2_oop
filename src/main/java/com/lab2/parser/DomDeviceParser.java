package com.lab2.parser;

import com.lab2.model.Device;
import com.lab2.util.XmlTags;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DomDeviceParser extends AbstractDeviceParser {

    public DomDeviceParser() {
        super("DOM");
    }

    @Override
    protected List<Device> readDevices(String xmlPath)
            throws ParserConfigurationException, SAXException, IOException {
        Document document = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new File(xmlPath));

        DeviceHandler handler = new DeviceHandler();
        NodeList nodes = document.getElementsByTagName(XmlTags.DEVICE);
        for (int i = 0; i < nodes.getLength(); i++) {
            readDevice((Element) nodes.item(i), handler);
        }
        return handler.getDevices();
    }

    private void readDevice(Element element, DeviceHandler handler) {
        handler.startDevice(element.getAttribute(XmlTags.ID));
        NodeList all = element.getElementsByTagName("*");
        for (int i = 0; i < all.getLength(); i++) {
            Node node = all.item(i);
            handler.setField(node.getNodeName(), node.getTextContent().trim());
        }
        handler.endDevice();
    }
}