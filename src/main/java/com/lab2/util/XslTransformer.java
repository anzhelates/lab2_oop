package com.lab2.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public final class XslTransformer {
    private static final Logger logger = LogManager.getLogger(XslTransformer.class);

    private XslTransformer() {
    }

    public static void transform(String xmlPath, String xslPath, String outputPath)
            throws DeviceParseException {
        try {
            Transformer transformer = TransformerFactory.newInstance()
                    .newTransformer(new StreamSource(new File(xslPath)));
            transformer.transform(new StreamSource(new File(xmlPath)),
                    new StreamResult(new File(outputPath)));
            logger.info("XSL transformed {} -> {}", xmlPath, outputPath);
        } catch (TransformerException e) {
            throw new DeviceParseException("XSL: failed to transform " + xmlPath, e);
        }
    }
}