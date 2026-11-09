package com.lab2.parser;

import com.lab2.builder.DeviceBuilder;
import com.lab2.model.Device;
import com.lab2.model.Group;
import com.lab2.model.Port;
import com.lab2.util.XmlTags;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DeviceHandler extends DefaultHandler {
    private final List<Device> devices = new ArrayList<>();
    private final StringBuilder text = new StringBuilder();
    private DeviceBuilder builder;

    public List<Device> getDevices() {
        return devices;
    }

    public void startDevice(String id) {
        builder = new DeviceBuilder().id(id);
    }

    public void setField(String tag, String value) {
        if (builder == null) {
            return;
        }
        switch (tag) {
            case XmlTags.NAME -> builder.name(value);
            case XmlTags.ORIGIN -> builder.origin(value);
            case XmlTags.PRICE -> builder.price(new BigDecimal(value));
            case XmlTags.CRITICAL -> builder.critical(Boolean.parseBoolean(value));
            case XmlTags.PERIPHERAL -> builder.peripheral(Boolean.parseBoolean(value));
            case XmlTags.POWER_CONSUMPTION -> builder.powerConsumption(Integer.parseInt(value));
            case XmlTags.COOLER -> builder.cooler(Boolean.parseBoolean(value));
            case XmlTags.GROUP -> builder.group(Group.fromXml(value));
            case XmlTags.PORT -> builder.addPort(Port.fromXml(value));
            default -> { }
        }
    }

    public void endDevice() {
        devices.add(builder.build());
        builder = null;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        text.setLength(0);
        if (XmlTags.DEVICE.equals(qName)) {
            startDevice(attributes.getValue(XmlTags.ID));
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        text.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (XmlTags.DEVICE.equals(qName)) {
            endDevice();
        } else {
            setField(qName, text.toString().trim());
        }
    }
}