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

import com.lifescan.dummy.data.constants.ConfigConstants;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Generator {

  private static int dateNumber;
  private static LocalDateTime localDateTime;
  private static String runningEvent;

  /**
   * Method that is responsible for generate the reading date
   *
   * @return A string that concerns to a new date
   */
  public static String generateReadingDateFormatted(String exerciseEvent, int numberEventsPerDay) {
    if (!exerciseEvent.equalsIgnoreCase(runningEvent)) {
      runningEvent = exerciseEvent;
      localDateTime =
          Util.convertFromStringtoLocalDateTime(ArgsParameter.getInstance().getStartDate());
      dateNumber = 0;
    } else {
      localDateTime =
          localDateTime
              .withHour(Util.getRandomNumberBetween(0, 23))
              .withMinute(Util.getRandomNumberBetween(0, 59));
    }
    if (dateNumber == numberEventsPerDay) {
      localDateTime = localDateTime.plusDays(1);
      dateNumber = 1;
    } else {
      dateNumber++;
    }
    return localDateTime.format(DateTimeFormatter.ofPattern(ConfigConstants.DATA_FORMAT_PATTERN));
  }

  /**
   * Method responsible for setting the attributes values.
   *
   * @return A single attribute value.
   * @param extendedAttributes Concerns to the list of data that comes from xml file
   */
  public AttributeValue generateAttributeValue(ExtendedAttributesFromXml extendedAttributes) {
    return (extendedAttributes == null)
        ? null
        : AttributeValue.builder().value(generateAttributes(extendedAttributes)).build();
  }

  /**
   * Method responsible for generating attributes.
   *
   * @return A list of attributes.
   * @param extendedAttributes Concerns to the list of data that comes from xml file
   */
  private List<Attribute> generateAttributes(ExtendedAttributesFromXml extendedAttributes) {
    if (ArgsParameter.getInstance().getPreset() == null) {
      return generateNewAttributes();
    } else {
      return getAttributesFromXmlFile(extendedAttributes);
    }
  }

  /**
   * Generate a list with randomized values
   *
   * @return A list of attributes
   */
  public List<Attribute> generateNewAttributes() {
    List<Attribute> attributes = new ArrayList<>();
    attributes.add(
        Attribute.builder()
            .value(getRandomIntensityAttribute())
            .type("string")
            .name("dataLogs_healthAttributes_excersizeIntensity")
            .build());
    return attributes;
  }

  /**
   * Randomize a value for intensity attribute
   *
   * @return An intensity attribute randomized value.
   */
  private String getRandomIntensityAttribute() {
    return IntensityAttribute.randomIntensityAttribute().name();
  }

  /**
   * Read extended attributes from xml
   *
   * @param extendedAttributes List of extended attributes from xml
   * @return list of attributes
   */
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
  public List<Annotation> generateAnnotations(AnnotationsFromXml annotationsFromXml) {
    if (annotationsFromXml == null) {
      return Collections.emptyList();
    } else {
      List<Annotation> annotations = new ArrayList<>();
      for (AnnotationFromXml annotationFromXml : annotationsFromXml.getAnnotation()) {
        annotations.add(generateAnnotation(annotationFromXml));
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
  private Annotation generateAnnotation(AnnotationFromXml annotationFromXml) {
    return Annotation.builder().value(annotationFromXml.getAnnotation()).build();
  }

  /**
   * Method resonsible for generating new UUID's for the events.
   *
   * @return A new UUID
   */
  public String generateId() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
