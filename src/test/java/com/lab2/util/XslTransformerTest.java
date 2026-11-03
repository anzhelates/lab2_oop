package com.lab2.util;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class XslTransformerTest {
    private static final String XML = "src/test/resources/valid-devices.xml";
    private static final String XSL = "src/main/resources/xsl/group-by-origin.xsl";

    @Test
    void transform_validXml_groupsDevicesByOrigin() throws Exception {
        Path output = Files.createTempFile("grouped", ".xml");
        try {
            XslTransformer.transform(XML, XSL, output.toString());

            Element root = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new File(output.toString())).getDocumentElement();
            List<Element> origins = childElements(root);

            assertEquals("Origins", root.getTagName());
            assertEquals(2, origins.size());

            assertEquals("China", origins.get(0).getAttribute("name"));
            assertEquals(List.of("Dell P2422H Monitor", "Logitech G102"), deviceNames(origins.get(0)));

            assertEquals("USA", origins.get(1).getAttribute("name"));
            assertEquals(List.of("Intel Core i5-12400"), deviceNames(origins.get(1)));
        } finally {
            Files.deleteIfExists(output);
        }
    }

    @Test
    void transform_missingXml_throwsParseException() throws Exception {
        Path output = Files.createTempFile("grouped", ".xml");
        try {
            assertThrows(DeviceParseException.class,
                    () -> XslTransformer.transform("no-such-file.xml", XSL, output.toString()));
        } finally {
            Files.deleteIfExists(output);
        }
    }

    private static List<Element> childElements(Element parent) {
        List<Element> result = new ArrayList<>();
        NodeList nodes = parent.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                result.add((Element) nodes.item(i));
            }
        }
        return result;
    }

    private static List<String> deviceNames(Element origin) {
        List<String> names = new ArrayList<>();
        for (Element device : childElements(origin)) {
            names.add(device.getElementsByTagName("Name").item(0).getTextContent());
        }
        return names;
    }
}