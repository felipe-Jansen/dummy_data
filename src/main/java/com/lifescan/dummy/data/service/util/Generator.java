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

import com.lifescan.dummy.data.constants.ConfigConstants;
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
import com.lifescan.dummy.data.model.xml.CarbohydrateFromXml;
import com.lifescan.dummy.data.model.xml.DeviceDataDataSet;
import com.lifescan.dummy.data.model.xml.ExtendedAttributesFromXml;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class Generator {

  /**
   * Method responsible for get the system's data and convert to string following the pattern
   * yyyy-MM-dd HH:mm:ss
   *
   */
  private static LocalDateTime localDateTime = LocalDateTime.now();

  /**
   * Method responsible for returning a new list of annotations.
   *
   * @return A list of annotations.
   * @param annotationsFromXml Concerns to the list of data that comes from xml file
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
   * @param annotationFromXml Concerns to the data that comes from xml file
   */
  protected static Annotation generatingAnnotation(AnnotationFromXml annotationFromXml) {
    return Annotation.builder().annotation(annotationFromXml.getAnnotation()).build();
  }

  /**
   * Method responsible for setting the attributes values.
   *
   * @return A single attribute value.
   * @param extendedAttributes Concerns to the list of data that comes from xml file
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
   * @param extendedAttributes Concerns to the list of data that comes from xml file
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
   * @param carbohydrates Concerns to the data that comes from xml file
   */
  protected static Carbohydrate generatingCarbohydrates(CarbohydrateFromXml carbohydrates) {
    return Carbohydrate.builder()
        .value(carbohydrates.getValue())
        .units(carbohydrates.getUnits())
        .build();
  }

  /**
   * Method responsible for ganerating a single bolusFromXmls delivered.
   *
   * @return A single bolusFromXmls delivered.
   * @param bolusDelivered Concerns to the data that comes from xml file
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
   * @param bgValue Concerns to the data that comes from xml file
   */
  protected static BgValue generatingBgValue(BgValueFromXml bgValue) {
    return BgValue.builder().value(bgValue.getValue()).units(bgValue.getUnits()).build();
  }

  /**
   * Method responsible for reading the object that was in the xml file and converting it into a
   * java object.
   *
   * @param file that concerns to the name of xml
   * @return the object created by information in xml file
   * @throws JAXBException that concerns to the errors when trying to read the xml file
   */
  protected static DeviceDataDataSet getDeviceDataDataSet(String file) throws JAXBException {
    JAXBContext jaxbContext = JAXBContext.newInstance(DeviceDataDataSet.class);
    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    return (DeviceDataDataSet) jaxbUnmarshaller.unmarshal(new File(file));
  }

  /**
   * Method responsible for generating a reading date for the events. Note: each calling to this
   * function increments a delay to time.
   *
   * @return A string with the formatted date
   */
  static String generatingReadingDateFormatted() {
    randomizeDate();
    localDateTime = localDateTime.plusMinutes(ConfigConstants.DELAY_TIME_BETWEEN_EVENTS);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ConfigConstants.DATA_FORMAT_PATTERN);
    return localDateTime.format(formatter);
  }

  private static void randomizeDate() {
    String inicio = "2023-10-20";
    String fim = "2021-10-21";
    LocalDateTime.now()
        .withYear(randomElementOfDate(inicio, fim, ChronoField.YEAR))
        .withMonth(randomElementOfDate(inicio, fim, ChronoField.MONTH_OF_YEAR))
        .withDayOfMonth(randomElementOfDate(inicio, fim, ChronoField.DAY_OF_MONTH))
        .withHour(new Random().nextInt(23))
        .withMinute(new Random().nextInt(59));
  }

  private static int randomElementOfDate(String inicio, String fim, ChronoField field) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    int max = formatter.parse(inicio).get(field);
    int min = formatter.parse(fim).get(field);
    int range = (max - min) + 1;
    return (int)(Math.random() * range) + min;
  }

  /**
   * Method resonsible for generating new UUID's for the events.
   *
   * @return A new UUID
   */
  static String generatingId() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
