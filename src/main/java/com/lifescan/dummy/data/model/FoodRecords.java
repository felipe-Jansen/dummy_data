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

public class FoodRecords extends Reading {

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
