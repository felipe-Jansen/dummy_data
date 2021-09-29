package com.lifescan.dummy.data.model;

public class HealthAttributes extends Reading{

    private String editable;
    private String healthAtributesLookup;
    private Annotation[] annotation;
    private int healthAttributesValue;
    private AttributeValue[] extendedAttributeValue;

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getHealthAtributesLookup() {
        return healthAtributesLookup;
    }

    public void setHealthAtributesLookup(String healthAtributesLookup) {
        this.healthAtributesLookup = healthAtributesLookup;
    }

    public Annotation[] getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation[] annotation) {
        this.annotation = annotation;
    }

    public int getHealthAttributesValue() {
        return healthAttributesValue;
    }

    public void setHealthAttributesValue(int healthAttributesValue) {
        this.healthAttributesValue = healthAttributesValue;
    }

    public AttributeValue[] getExtendedAttributeValue() {
        return extendedAttributeValue;
    }

    public void setExtendedAttributeValue(AttributeValue[] extendedAttributeValue) {
        this.extendedAttributeValue = extendedAttributeValue;
    }
}
