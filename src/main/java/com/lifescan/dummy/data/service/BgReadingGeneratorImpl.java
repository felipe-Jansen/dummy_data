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
import com.lifescan.dummy.data.model.ArgsParameter;
import com.lifescan.dummy.data.model.BgReading;
import com.lifescan.dummy.data.model.BgValue;
import com.lifescan.dummy.data.model.xml.BgReadingFromXml;
import com.lifescan.dummy.data.model.xml.BgValueFromXml;
import com.lifescan.dummy.data.service.util.Util;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/** Class responsible for generating the objects with type bgReadingFromXml. */
@Log4j2
@Service
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BgReadingGeneratorImpl extends Generator implements BgReadingGenerator {

  private static int dateNumber = 0;

  // @TODO looks like this is basic a local variable. Why do you create it as class property?
  private static LocalDateTime localDateTime;

  /**
   * Method responsible for generating a single bg value.
   *
   * @return A single bolusFromXmls reading.
   * @param bgValue Concerns to the data that comes from xml file
   */
  public static BgValue generateBgValue(BgValueFromXml bgValue) {
    return BgValue.builder().value(bgValue.getValue()).units(bgValue.getUnits()).build();
  }

  public static BgValue generateBgValue() {
    return BgValue.builder()
        .value(
            Util.getRandomNumberBetween(
                ConfigConstants.MIN_VALUE_BGVALUE, ConfigConstants.MAX_VALUE_BGVALUE))
        .units(ConfigConstants.UNIT_BGVALUE)
        .build();
  }

  /**
   * Method that is responsible for generate the reading date
   *
   * @return A string that concerns to a new date
   */
  // @TODO I don't like this approach. You should provide a date for the reading.
  // You are storing dateNumber to control the DAY of the reading instead. IF other classes use this
  // Util, it will go crazy.
  // You'll loose control of the endDate.
  private static String generateReadingDateFormatted() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ConfigConstants.DATA_FORMAT_PATTERN);

    // This is confusing. If localDateTime is null you instantiate a new one.
    // Then localDateTime is set with a new hour and minute, but you just created a random one.
    // It's like you are generating a random number, then generate a new random number and use it.
    // Looks duplicated.
    if (localDateTime == null) {
      localDateTime =
          Util.convertFromStringtoLocalDateTime(ArgsParameter.getInstance().getStartDate());
    }
    localDateTime =
        localDateTime
            .withHour(Util.getRandomNumberBetween(0, 23))
            .withMinute(Util.getRandomNumberBetween(0, 59));
    if (dateNumber == ArgsParameter.getInstance().getReadingsNumber()) {
      localDateTime = localDateTime.plusDays(1);
      // @TODO, see you need to reset the dateNumber to 1 instead of just receiving it as parameter
      // Also, why you are reseting to 1, if you declare its initial value as 0?
      // private static int dateNumber = 0;
      dateNumber = 1;
    } else {
      dateNumber++;
    }
    return localDateTime.format(formatter);
  }

  /** {@inheritDoc} */
  @Override
  public List<BgReading> generate(String file) {
    if (file == null) {
      return generateRandomValues();
    } else {
      return generateFromFile(file);
    }
  }

  /**
   * Generate a list of bgreadings with random value.
   *
   * @return list of bgreadings
   */
  private List<BgReading> generateRandomValues() {
    int size = Util.getNumberOfEvents(ArgsParameter.getInstance().getReadingsNumber());

    return new Random().ints(size).boxed().map(num -> buildObject()).collect(Collectors.toList());
  }

  /**
   * Get bgreading information from file
   *
   * @param file It concerns to the url to access the file.
   * @return list of events.
   */
  private List<BgReading> generateFromFile(String file) {
    try {
      return Util.getDeviceDataDataSet(file).getBgReadingDataLog().getBgReading().stream()
          .map(this::buildObject)
          .collect(Collectors.toList());
    } catch (JAXBException exception) {
      log.error("Error when generating bgReading.");
    }
    return Collections.emptyList();
  }

  /**
   * Method responsible for converting from XML file to a java object.
   *
   * @param bgReading That concerns to the data that comes from xml file.
   * @return Data from xml file converted in a java object.
   */
  private BgReading buildObject(BgReadingFromXml bgReading) {
    return BgReading.builder()
        .active(bgReading.getActive())
        .manual(bgReading.getManual())
        .readingDate(generateReadingDateFormatted())
        .id(generateId())
        .extendedAttributes(generateAttributeValue(bgReading.getExtendedAttributes()))
        .bgValue(generateBgValue(bgReading.getBgValue()))
        .mealTag(ArgsParameter.getInstance().getReadingsTag())
        .lastUpdatedDate(Instant.now().toEpochMilli())
        .build();
  }

  /**
   * Method responsible for generating an object from value bgReading
   *
   * @return Bgreading object
   */
  private BgReading buildObject() {
    return BgReading.builder()
        .active(ConfigConstants.TRUE)
        .manual(ConfigConstants.FALSE)
        .readingDate(generateReadingDateFormatted())
        .id(generateId())
        .extendedAttributes(null)
        .bgValue(generateBgValue())
        .mealTag(ArgsParameter.getInstance().getReadingsTag())
        .lastUpdatedDate(Instant.now().toEpochMilli())
        .build();
  }
}
