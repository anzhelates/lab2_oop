package com.lab2.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public final class XmlValidator {
    private static final Logger logger = LogManager.getLogger(XmlValidator.class);

    private XmlValidator() {
    }

    public static void validate(String xmlPath, String xsdPath) throws DeviceParseException {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
            logger.info("XML is valid: {}", xmlPath);
        } catch (SAXException | IOException e) {
            throw new DeviceParseException("Validation failed: " + xmlPath + " (" + e.getMessage() + ")", e);
        }
    }
}