package com.lifescan.dummy.data.model;

public class Carbohydrate {

    private int value;
    private String units;
    private Annotation[] annotation;

    public Annotation[] getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation[] annotation) {
        this.annotation = annotation;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
