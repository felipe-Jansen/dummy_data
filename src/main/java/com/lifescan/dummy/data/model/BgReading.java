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

public class BgReading extends Reading {

  private AttributeValue extendedAttributes;
  private String mealTag;
  private BgValue bgValue;

  public AttributeValue getExtendedAttributes() {
    return extendedAttributes;
  }

  public void setExtendedAttributes(AttributeValue extendedAttributes) {
    this.extendedAttributes = extendedAttributes;
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
