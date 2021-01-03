package com.core;

public enum Locator {

    ID("id"),XPATH("xpath");

    private String name;

    Locator(String name) {
        this.name = name;
    }

    String getValue() {
        return name;
    }

}
