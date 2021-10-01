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

public class BolusReadings extends Reading {

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
