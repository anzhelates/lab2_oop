package com.lab2.model;

public enum Port {
    COM, USB, LPT;

    public static Port fromXml(String value) {
        return valueOf(value);
    }
}