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
import com.lifescan.dummy.data.model.xml.AnnotationFromXml;
import com.lifescan.dummy.data.model.xml.AnnotationsFromXml;
import com.lifescan.dummy.data.model.xml.AttributeFromXml;
import com.lifescan.dummy.data.model.xml.BgValueFromXml;
import com.lifescan.dummy.data.model.xml.BolusDeliveredFromXml;
import com.lifescan.dummy.data.model.xml.ExtendedAttributesFromXml;
import java.util.ArrayList;
import java.util.List;

public abstract class Generator {

  /**
   * Method responcible for returning a new list of annotations.
   *
   * @return A list of annotations.
   * @param annotationsFromXml
   */
  protected static List<Annotation> generatingAnnotations(AnnotationsFromXml annotationsFromXml) {
    if (annotationsFromXml != null) {
      List<Annotation> annotations = new ArrayList<>();
      for (AnnotationFromXml annotationFromXml : annotationsFromXml.getAnnotation()) {
        annotations.add(generatingAnnotation(annotationFromXml));
      }
      return annotations;
    } else {
      return null;
    }
  }

  /**
   * Method responsible for generating a single annotationFromXml
   *
   * @return A single annotationFromXml.
   * @param annotationFromXml
   */
  protected static Annotation generatingAnnotation(AnnotationFromXml annotationFromXml) {
    return Annotation.builder().annotation(annotationFromXml.getAnnotation()).build();
  }

  /**
   * Method responsible for setting the attributes values.
   *
   * @return A single attribute value.
   * @param extendedAttributes
   */
  protected static AttributeValue generatingAttributeValue(
      ExtendedAttributesFromXml extendedAttributes) {
    if (extendedAttributes != null)
      return AttributeValue.builder()
          .attributeValue(generatingAttributes(extendedAttributes))
          .build();
    else return null;
  }

  /**
   * Method responsible for generating attributes.
   *
   * @return A list of attributes.
   * @param extendedAttributes
   */
  private static List<Attribute> generatingAttributes(
      ExtendedAttributesFromXml extendedAttributes) {
    List<Attribute> attributes = new ArrayList<>();
    for (AttributeFromXml attributeFromXml : extendedAttributes.getAttributeValue()) {
      attributes.add(
          Attribute.builder()
              .value(attributeFromXml.getValue())
              .type(attributeFromXml.getType())
              .name(attributeFromXml.getName())
              .build());
    }
    return attributes;
  }

  /**
   * Method responsible for ganerating a single carbohydrate.
   *
   * @return A single carbohydrate.
   * @param carbohydrates
   */
  protected static Carbohydrate generatingCarbohydrates(Carbohydrate carbohydrates) {
    return Carbohydrate.builder()
        .value(carbohydrates.getValue())
        .units(carbohydrates.getUnits())
        .build();
  }

  /**
   * Method responsible for ganerating a single bolusFromXmls delivered.
   *
   * @return A single bolusFromXmls delivered.
   * @param bolusDelivered
   */
  protected static BolusDelivered generatingBolusDelivered(BolusDeliveredFromXml bolusDelivered) {
    return BolusDelivered.builder()
        .value(bolusDelivered.getValue())
        .units(bolusDelivered.getUnits())
        .build();
  }

  /**
   * Method responsible for generating a single bg value.
   *
   * @return A single bolusFromXmls reading.
   * @param bgValue
   */
  protected static BgValue generatingBgValue(BgValueFromXml bgValue) {
    return BgValue.builder().value(bgValue.getValue()).units(bgValue.getUnits()).build();
  }
}
