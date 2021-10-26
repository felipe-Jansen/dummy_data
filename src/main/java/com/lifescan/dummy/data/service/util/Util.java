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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.codec.digest.DigestUtils;

public class Util {

  /**
   * Method responsible for get the system's data and convert to string following the pattern
   * yyyy-MM-dd HH:mm:ss
   */
  private static LocalDateTime localDateTime = LocalDateTime.now();

  /**
   * Method responsible for generate a SHA1 token from the e-mail.
   *
   * @param emailAddress to serve as a base to generate the token.
   * @return A request token from email.
   */
  public static String generateRequestToken(String emailAddress) {
    return DigestUtils.sha1Hex(DigestUtils.sha1Hex(emailAddress).concat(emailAddress));
  }

  /**
   * Method responsible for generate a date of birth from system date. The Date of birth will be the
   * current year minus 20.
   *
   * @return A date 20 years ago
   */
  public static String generateDateOfBirth() {
    return LocalDateTime.now().minusYears(20).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
  }

  /**
   * Method responsible for returning a new list of annotations.
   *
   * @return A list of annotations.
   * @param annotationsFromXml Concerns to the list of data that comes from xml file
   */
  public static List<Annotation> generatingAnnotations(AnnotationsFromXml annotationsFromXml) {
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
  private static Annotation generatingAnnotation(AnnotationFromXml annotationFromXml) {
    return Annotation.builder().value(annotationFromXml.getAnnotation()).build();
  }

  /**
   * Method responsible for setting the attributes values.
   *
   * @return A single attribute value.
   * @param extendedAttributes Concerns to the list of data that comes from xml file
   */
  public static AttributeValue generatingAttributeValue(
      ExtendedAttributesFromXml extendedAttributes) {
    if (extendedAttributes != null) {
      return AttributeValue.builder().value(generatingAttributes(extendedAttributes)).build();
    } else {
      return null;
    }
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
  public static Carbohydrate generatingCarbohydrates(CarbohydrateFromXml carbohydrates) {
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
  public static BolusDelivered generatingBolusDelivered(BolusDeliveredFromXml bolusDelivered) {
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
  public static BgValue generatingBgValue(BgValueFromXml bgValue) {
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
  public static DeviceDataDataSet getDeviceDataDataSet(String file) throws JAXBException {
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
  public static String generatingReadingDateFormatted() {
    localDateTime = localDateTime.plusMinutes(ConfigConstants.DELAY_TIME_BETWEEN_EVENTS);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ConfigConstants.DATA_FORMAT_PATTERN);
    return localDateTime.format(formatter);
  }

  /**
   * Method resonsible for generating new UUID's for the events.
   *
   * @return A new UUID
   */
  public static String generatingId() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
