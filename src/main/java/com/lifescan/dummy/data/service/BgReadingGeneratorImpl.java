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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
  private static String generateReadingDateFormatted() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ConfigConstants.DATA_FORMAT_PATTERN);
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
      dateNumber = 1;
    } else {
      dateNumber++;
    }
    return localDateTime.format(formatter);
  }

  /** {@inheritDoc} */
  @Override
  public List<BgReading> generate(String file) {
    if (file == null) return generateRandomValues();
    else return generateFromFile(file);
  }

  /**
   * Generate bgreadings randomized values
   *
   * @return list of bgreadings
   */
  private List<BgReading> generateRandomValues() {
    List<BgReading> bgReadingList = new ArrayList<>();
    for (int i = 0;
        i < Util.getNumberOfEvents(ArgsParameter.getInstance().getReadingsNumber());
        i++) {
      bgReadingList.add(buildObject());
    }
    return bgReadingList;
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
