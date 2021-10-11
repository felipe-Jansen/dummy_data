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
   *
   * @return A list of annotations.
   */
  protected static List<Annotation> generatingAnnotations() {
    List<Annotation> annotations = new ArrayList<>();
    annotations.add(generatingAnnotation());
    return annotations;
  }

  /**
   * Method responsible for generating a single annotation
   *
   * @return A single annotation.
   */
  protected static Annotation generatingAnnotation() {
    return Annotation.builder().annotation("Felipe Jansen").build();
  }

  /**
   * Method responsible for setting the attributes values.
   *
   * @return A single attribute value.
   */
  protected static AttributeValue generatingAttributeValue() {
    return AttributeValue.builder().attributeValue(generatingAttribute()).build();
  }

  /**
   * Method responsible for generating attributes.
   *
   * @return A list of attributes.
   */
  private static List<Attribute> generatingAttribute() {
    List<Attribute> attributes = new ArrayList<>();
    attributes.add(
        Attribute.builder()
            .name("dataLogs_glucose_lifestyletags")
            .type("string")
            .value("")
            .build());
    return attributes;
  }

  /**
   * Method responsible for ganerating a single carbohydrate.
   *
   * @return A single carbohydrate.
   */
  protected static Carbohydrate generatingCarbohydrates() {
    return Carbohydrate.builder().value(150).units("g").build();
  }

  /**
   * Method responsible for ganerating a single bolus delivered.
   *
   * @return A single bolus delivered.
   */
  protected static BolusDelivered generatingBolusDelivered() {
    return BolusDelivered.builder().value("55").units("u").build();
  }

  /**
   * Method responsible for generating a single bg value.
   *
   * @return A single bolus reading.
   */
  protected static BgValue generatingBgValue() {
    return BgValue.builder().value(111).units("mg/dL").build();
  }
}
