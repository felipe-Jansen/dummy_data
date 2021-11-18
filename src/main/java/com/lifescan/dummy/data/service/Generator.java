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
import com.lifescan.dummy.data.model.Reading;
import com.lifescan.dummy.data.model.xml.AnnotationFromXml;
import com.lifescan.dummy.data.model.xml.AnnotationsFromXml;
import com.lifescan.dummy.data.model.xml.AttributeFromXml;
import com.lifescan.dummy.data.model.xml.ExtendedAttributesFromXml;
import com.lifescan.dummy.data.service.util.Util;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Generator {

  static String referenceDate = null;
  static String newDate = null;
  private static LocalDate eventDate;

  /**
   * Method responsible for generating the date when a preset is informed by user
   *
   * @param readings list of readings
   * @return A list with readings
   */
  protected static List<Reading> configuringInformation(List<Reading> readings) {
    if (!readings.isEmpty()) {

      // First, is needed to delete the T letter from the date that comes from xml file.
      readings.forEach(
          value ->
              value.setReadingDate(
                  localDateTimeToLocalDateString(value.getReadingDate().replace("T", " "))));

      // Then, we need to group (distinguishing by date) and counting it.
      List<Entry<LocalDate, Long>> entries =
          new ArrayList<>(
              readings.stream()
                  .collect(
                      Collectors.groupingBy(
                          c -> Util.convertFromStringtoLocalDate(c.getReadingDate()),
                          Collectors.counting()))
                  .entrySet());

      // Then, we need to check if the amount of days is bigger than entries size, if not checked,
      // could throw an exception typed IndexOutOfBoundsException.
      entries =
          ((int) getTheAmountOfDaysBetweenRangeInformed() > entries.size())
              ? entries
              : entries.subList(0, (int) getTheAmountOfDaysBetweenRangeInformed());

      // This line is responsible for setting the dates following rules.
      readings.forEach(
          reading -> reading.setReadingDate(buildReadingDate(reading.getReadingDate())));

      // This line is responsible for reset referenceDate for each event.
      referenceDate = null;

      return readings.subList(0, getSizeOfList(entries));
    } else return new ArrayList<>();
  }

  /**
   * Method responsible for adding the amount of events. This value will determinate the quantity of
   * events.
   *
   * @param entries list of dates grouped
   * @return an int value with the sum of values
   */
  private static int getSizeOfList(List<Entry<LocalDate, Long>> entries) {
    return entries.stream().map(x -> x.getValue().intValue()).mapToInt(Integer::intValue).sum();
  }

  /**
   * Method that returns the amount of days between date entered by user.
   *
   * @return a long with amount of days
   */
  private static long getTheAmountOfDaysBetweenRangeInformed() {
    return ChronoUnit.DAYS.between(
            Util.convertFromStringtoLocalDate(ArgsParameter.getInstance().getStartDate()),
            Util.convertFromStringtoLocalDate(ArgsParameter.getInstance().getEndDate()))
        + 1;
  }

  /**
   * Method responsible for build the reading date. It will increase
   *
   * @param readingDate Date from event
   * @return a localDate as string
   */
  private static String buildReadingDate(String readingDate) {
    if (referenceDate == null) {
      referenceDate = readingDate;
      newDate = ArgsParameter.getInstance().getStartDate();
    } else if (!readingDate.equalsIgnoreCase(referenceDate)) {
      newDate =
          Util.convertFromStringtoLocalDate(newDate)
              .plusDays(1)
              .format(DateTimeFormatter.ofPattern(ConfigConstants.DATA_FORMAT_PATTERN));
      referenceDate = readingDate;
    }
    return newDate;
  }

  /**
   * Method responsible for converting localDateTime to localDate as String
   *
   * @param readingDate Date from event
   * @return a localDate as String
   */
  protected static String localDateTimeToLocalDateString(String readingDate) {
    return LocalDate.parse(
            readingDate, DateTimeFormatter.ofPattern(ConfigConstants.DATA_TIME_FORMAT_PATTERN))
        .toString();
  }

  /**
   * Method that is responsible for generate the reading date.
   *
   * @return A string that concerns to a new date
   */
  protected static String generateReadingDateFormatted() {
    LocalDate readingDate = runningDateRange();
    if (readingDate.isEqual(getEndDate())) {
      resetEventDate();
    }
    return readingDate
        .atTime(Util.getRandomNumberBetween(0, 23), Util.getRandomNumberBetween(0, 59))
        .format(DateTimeFormatter.ofPattern(ConfigConstants.DATA_TIME_FORMAT_PATTERN));
  }

  /**
   * eventDate attribute is responsible for control the dates that are being generated, however
   * resetEventDate() is responsible for set the value as null.
   */
  private static void resetEventDate() {
    eventDate = null;
  }

  /**
   * This method is responsible for increase one day to eventDate attribute, also it could set the
   * eventDate value with start date informed by user.
   *
   * @return A localDate object
   */
  private static LocalDate runningDateRange() {
    eventDate = eventDate == null ? getStartDate() : eventDate.plusDays(1);
    return eventDate;
  }

  /**
   * Method responsible for return the start date informed by user.
   *
   * @return start date as localdate
   */
  private static LocalDate getStartDate() {
    return Util.convertFromStringtoLocalDate(ArgsParameter.getInstance().getStartDate());
  }

  /**
   * Method responsible for return the end date informed by user.
   *
   * @return end date as localdate
   */
  private static LocalDate getEndDate() {
    return Util.convertFromStringtoLocalDate(ArgsParameter.getInstance().getEndDate());
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
