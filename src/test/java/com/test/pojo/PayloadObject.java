package com.test.pojo;

public class PayloadObject {
    String key1;
    String key2;

    public PayloadObject(String key1, String key2) {
        this.key1 = key1;
        this.key2 = key2;
    }

    public PayloadObject() {
        this.key1 = key1;
        this.key2 = key2;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String kwy1) {
        this.key1 = kwy1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }
}
