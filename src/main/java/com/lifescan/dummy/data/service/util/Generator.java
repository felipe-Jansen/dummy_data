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
package com.lifescan.dummy.data.service.util;

import com.lifescan.dummy.data.model.Annotation;
import com.lifescan.dummy.data.model.Attribute;
import com.lifescan.dummy.data.model.AttributeValue;
import com.lifescan.dummy.data.model.BgValue;
import com.lifescan.dummy.data.model.BolusDelivered;
import com.lifescan.dummy.data.model.Carbohydrate;
import java.util.ArrayList;
import java.util.List;

public abstract class Generator {

  /**
   * Method responcible for returning a new list of annotations.
   * @return
   */
  protected static List<Annotation> generatingAnnotations() {
    List<Annotation> annotations = new ArrayList<>();
    annotations.add(generatingAnnotation());
    return annotations;
  }

  /**
   * Method responsible for generating a single annotation
   * @return
   */
  private static Annotation generatingAnnotation() {
    Annotation annotation = new Annotation();
    annotation.setAnnotation("");
    return annotation;
  }

  /**
   * Method responsible for setting the attributes values.
   *
   * @return
   */
  protected static AttributeValue generatingAttributeValue() {
    AttributeValue attributeValue = new AttributeValue();
    attributeValue.setAttributeValue(generatingAttribute());
    return attributeValue;
  }

  /**
   * Method responsible for generating attributes.
   *
   * @return
   */
  private static List<Attribute> generatingAttribute() {
    List<Attribute> attributes = new ArrayList<>();
    Attribute attribute = new Attribute();
    attribute.setName("dataLogs_glucose_lifestyletags");
    attribute.setType("string");
    attribute.setValue("");
    attributes.add(attribute);
    return attributes;
  }

  /**
   * Method responsible for ganerating a single carbohydrate.
   * @return
   */
  protected static Carbohydrate generatingCarbohydrates() {
    Carbohydrate carbohydrate = new Carbohydrate();
    carbohydrate.setValue(150);
    carbohydrate.setUnits("g");
    return carbohydrate;
  }

  /**
   * Method responsible for ganerating a single bolus delivered.
   * @return
   */
  protected static BolusDelivered generatingBolusDelivered() {
    BolusDelivered bolusDelivered = new BolusDelivered();
    bolusDelivered.setValue("55");
    bolusDelivered.setUnits("u");
    return bolusDelivered;
  }

  /**
   * Method responsible for generating a single bg value.
   *
   * @return
   */
  protected static BgValue generatingBgValue() {
    BgValue bgValue = new BgValue();
    bgValue.setValue(111);
    bgValue.setUnits("mg/dL");
    return bgValue;
  }
}
