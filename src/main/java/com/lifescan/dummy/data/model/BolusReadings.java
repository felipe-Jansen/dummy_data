package com.lifescan.dummy.data.model;

public class BolusReadings extends Reading{

    private String editable;
    private String injectedInsulinType;
    private Annotation[] annotation;
    private BolusDelivered bolusDelivered;

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getInjectedInsulinType() {
        return injectedInsulinType;
    }

    public void setInjectedInsulinType(String injectedInsulinType) {
        this.injectedInsulinType = injectedInsulinType;
    }

    public Annotation[] getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation[] annotation) {
        this.annotation = annotation;
    }

    public BolusDelivered getBolusDelivered() {
        return bolusDelivered;
    }

    public void setBolusDelivered(BolusDelivered bolusDelivered) {
        this.bolusDelivered = bolusDelivered;
    }
}
