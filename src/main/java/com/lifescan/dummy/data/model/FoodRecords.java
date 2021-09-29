package com.lifescan.dummy.data.model;

public class FoodRecords extends Reading{

    private String editable;
    private Carbohydrate carbohydrate;
    private Annotation annotation;

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public Carbohydrate getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(Carbohydrate carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }
}
