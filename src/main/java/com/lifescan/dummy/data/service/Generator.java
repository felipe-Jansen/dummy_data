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
package com.lifescan.dummy.data.service;

import com.lifescan.dummy.data.enums.IntensityAttribute;
import com.lifescan.dummy.data.model.Annotation;
import com.lifescan.dummy.data.model.ArgsParameter;
import com.lifescan.dummy.data.model.Attribute;
import com.lifescan.dummy.data.model.AttributeValue;
import com.lifescan.dummy.data.model.xml.AnnotationFromXml;
import com.lifescan.dummy.data.model.xml.AnnotationsFromXml;
import com.lifescan.dummy.data.model.xml.AttributeFromXml;
import com.lifescan.dummy.data.model.xml.ExtendedAttributesFromXml;
import com.lifescan.dummy.data.service.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Generator {

  /**
   * Method responsible for setting the attributes values.
   *
   * @return A single attribute value.
   * @param extendedAttributes Concerns to the list of data that comes from xml file
   */
  public AttributeValue generatingAttributeValue(ExtendedAttributesFromXml extendedAttributes) {
    return (extendedAttributes == null)
        ? null
        : AttributeValue.builder().value(generatingAttributes(extendedAttributes)).build();
  }

  /**
   * Method responsible for generating attributes.
   *
   * @return A list of attributes.
   * @param extendedAttributes Concerns to the list of data that comes from xml file
   */
  private List<Attribute> generatingAttributes(ExtendedAttributesFromXml extendedAttributes) {
    if (ArgsParameter.getInstance().getPreset() == null) {
      return generateNewAttributes();
    } else {
      return getAttributesFromXmlFile(extendedAttributes);
    }
  }

  private List<Attribute> generateNewAttributes() {
    List<Attribute> attributes = new ArrayList<>();
    attributes.add(Attribute.builder()
        .value(getRandomIntensityAttribute())
        .type("string")
        .name("dataLogs_healthAttributes_excersizeIntensity")
        .build());
    return attributes;
  }

  private String getRandomIntensityAttribute() {
    return IntensityAttribute.randomIntensityAttribute().name();
  }

  private List<Attribute> getAttributesFromXmlFile(ExtendedAttributesFromXml extendedAttributes) {
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
   * Method responsible for returning a new list of annotations.
   *
   * @return A list of annotations.
   * @param annotationsFromXml Concerns to the list of data that comes from xml file
   */
  public List<Annotation> generatingAnnotations(AnnotationsFromXml annotationsFromXml) {
    if (annotationsFromXml == null) {
      return Collections.emptyList();
    } else {
      List<Annotation> annotations = new ArrayList<>();
      for (AnnotationFromXml annotationFromXml : annotationsFromXml.getAnnotation()) {
        annotations.add(generatingAnnotation(annotationFromXml));
      }
      return annotations;
    }
  }

  /**
   * Method responsible for generating a single annotationFromXml
   *
   * @return A single annotationFromXml.
   * @param annotationFromXml Concerns to the data that comes from xml file
   */
  private Annotation generatingAnnotation(AnnotationFromXml annotationFromXml) {
    return Annotation.builder().value(annotationFromXml.getAnnotation()).build();
  }

  /**
   * Method resonsible for generating new UUID's for the events.
   *
   * @return A new UUID
   */
  public String generatingId() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
