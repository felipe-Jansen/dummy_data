/*
 * @author fjansen@lifescan.com
 * @version 1
 * Copyright: Copyright (c) 2021
 * Company: LifeScan IP Holdings, LLC
 * This file contains trade secrets of LifeScan IP Holdings, LLC.
 * No part may be reproduced or transmitted in any
 * form by any means or for any purpose without the express written
 * permission of LifeScan IP Holdings, LLC.
 */
package com.lifescan.dummy.data.model;

public class HealthAttributes extends Reading {

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
