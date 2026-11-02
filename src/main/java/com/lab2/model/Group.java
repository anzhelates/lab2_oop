package com.lab2.model;

public enum Group {
    IO("IO"),
    MULTIMEDIA("Multimedia");

    private final String xmlValue;

    Group(String xmlValue) { this.xmlValue = xmlValue; }

    public String getXmlValue() { return xmlValue; }

    public static Group fromXml(String value) {
        for (Group g : values()) {
            if (g.xmlValue.equals(value)) return g;
        }
        throw new IllegalArgumentException("Unknown group: " + value);
    }
}