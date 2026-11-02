package com.lab2;

import com.lab2.model.Device;
import com.lab2.parser.DeviceParser;
import com.lab2.parser.DomDeviceParser;
import com.lab2.parser.SaxDeviceParser;
import com.lab2.parser.StaxDeviceParser;
import com.lab2.service.DeviceService;
import com.lab2.util.DeviceComparators;
import com.lab2.util.DeviceParseException;
import com.lab2.util.XslTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    private static final String XML_PATH = "src/main/resources/data/devices.xml";
    private static final String XSD_PATH = "src/main/resources/data/devices.xsd";
    private static final String XSL_PATH = "src/main/resources/xsl/group-by-origin.xsl";
    private static final String OUTPUT_PATH = "output/grouped-by-origin.xml";

    public static void main(String[] args) {
        List<DeviceParser> parsers =
                List.of(new SaxDeviceParser(), new DomDeviceParser(), new StaxDeviceParser());

        try {
            List<Device> reference = null;
            for (DeviceParser parser : parsers) {
                String name = parser.getClass().getSimpleName();
                List<Device> devices = new DeviceService(parser)
                        .load(XML_PATH, XSD_PATH, DeviceComparators.BY_PRICE);
                logger.info("{}: {} devices", name, devices.size());

                if (reference == null) {
                    reference = devices;
                } else {
                    logger.info("{}: same result as first parser: {}", name, devices.equals(reference));
                }
            }

            reference.forEach(device -> logger.info("{}", device));

            Files.createDirectories(Path.of(OUTPUT_PATH).getParent());
            XslTransformer.transform(XML_PATH, XSL_PATH, OUTPUT_PATH);
        } catch (DeviceParseException | IOException e) {
            logger.error("Application failed", e);
        }
    }
}