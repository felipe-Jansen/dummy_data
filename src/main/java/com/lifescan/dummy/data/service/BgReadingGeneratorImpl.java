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
import com.lifescan.dummy.data.model.Reading;
import com.lifescan.dummy.data.model.xml.BgReadingFromXml;
import com.lifescan.dummy.data.model.xml.BgValueFromXml;
import com.lifescan.dummy.data.service.util.Util;
import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

  private static String previousDate;
  private static int tagRunner;

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
   * Get next mealTag from list. We will loop into the possible mealTag values (passed via command
   * line). We need to reset the value once we reach the end of the list or we change the day.
   *
   * @param readingDate referenced reading date
   * @return a meal tag
   */
  private static String getMealTag(String readingDate) {
    if (tagRunner >= ArgsParameter.getInstance().getReadingsTag().size()
        || datesAreDifferent(readingDate)) {
      tagRunner = 0;
      previousDate = localDateTimeToLocalDateString(readingDate);
    }
    return ArgsParameter.getInstance().getReadingsTag().get(tagRunner++);
  }

  /**
   * Method that checks if dates are different or not
   *
   * @param readingDate referenced reading date
   * @return boolean value
   */
  private static boolean datesAreDifferent(String readingDate) {
    return !localDateTimeToLocalDateString(readingDate).equalsIgnoreCase(previousDate);
  }

  /**
   * {@inheritDoc}
   *
   * @return
   */
  @Override
  public List<BgReading> generate(String file) {
    if (file == null) {
      return generateRandomValues();
    } else {
      return (List<BgReading>) generateFromFile(file);
    }
  }

  /**
   * Generate bgreadings randomized values
   *
   * @return list of bgreadings
   */
  private List<BgReading> generateRandomValues() {
    List<BgReading> bgReadingList =
        Stream.generate(this::buildObject)
            .limit(Util.getNumberOfEvents(ArgsParameter.getInstance().getReadingsNumber()))
            .collect(Collectors.toList());
    return setMealTag(bgReadingList);
  }

  private List<BgReading> setMealTag(List<BgReading> stream) {
    stream.sort(Comparator.comparing(Reading::getReadingDate));
    stream.forEach(event -> event.setMealTag(getMealTag(event.getReadingDate())));
    return stream;
  }

  /**
   * Get bgReading information from file
   *
   * @param file It concerns to the url to access the file.
   * @return list of events.
   */
  private List<? extends Reading> generateFromFile(String file) {
    try {
      return configureInformation(
          Util.getDeviceDataDataSet(file).getBgReadingDataLog().getBgReading().stream()
              .map(this::buildObject)
              .collect(Collectors.toList()));
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
        .readingDate(bgReading.getReadingDate())
        .id(generateId())
        .extendedAttributes(generateAttributeValue(bgReading.getExtendedAttributes()))
        .bgValue(generateBgValue(bgReading.getBgValue()))
        .mealTag(null)
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
        .mealTag(null)
        .lastUpdatedDate(Instant.now().toEpochMilli())
        .build();
  }
}
