package com.lifescan.dummy.data.model;

public class BgReading extends Reading{

    private AttributeValue extendedAttributeValue;
    private String mealTag;
    private BgValue bgValue;

    public AttributeValue getExtendedAttributeValue() {
        return extendedAttributeValue;
    }

    public void setExtendedAttributeValue(AttributeValue extendedAttributeValue) {
        this.extendedAttributeValue = extendedAttributeValue;
    }

    public String getMealTag() {
        return mealTag;
    }

    public void setMealTag(String mealTag) {
        this.mealTag = mealTag;
    }

    public BgValue getBgValue() {
        return bgValue;
    }

    public void setBgValue(BgValue bgValue) {
        this.bgValue = bgValue;
    }
}
