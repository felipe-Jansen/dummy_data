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
import com.lifescan.dummy.data.enums.Preset;
import com.lifescan.dummy.data.model.ArgsParameter;
import com.lifescan.dummy.data.model.BolusDelivered;
import com.lifescan.dummy.data.model.BolusReading;
import com.lifescan.dummy.data.model.xml.BolusDeliveredFromXml;
import com.lifescan.dummy.data.model.xml.BolusFromXml;
import com.lifescan.dummy.data.service.util.Util;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public class BolusReadingGeneratorImpl extends Generator implements BolusReadingGenerator {

  private static int dateNumber = 0;

  private static LocalDateTime localDateTime;

  /**
   * Method responsible for ganerating a single bolusFromXmls delivered.
   *
   * @return A single bolusFromXmls delivered.
   * @param bolusDelivered it concerns to the data that comes from xml file
   */
  protected static BolusDelivered generateBolusDelivered(BolusDeliveredFromXml bolusDelivered) {
    return ArgsParameter.getInstance().getPreset() == null
        ? BolusDelivered.builder()
            .value(
                String.valueOf(
                    Util.getRandomNumberBetween(
                        ConfigConstants.MIN_VALUE_BOLUS_UNIT,
                        ConfigConstants.MAX_VALUE_BOLUS_UNIT)))
            .units(ConfigConstants.UNIT_BOLUS_VALUE)
            .build()
        : BolusDelivered.builder()
            .value(bolusDelivered.getValue())
            .units(bolusDelivered.getUnits())
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
    if (localDateTime
                .toLocalDate()
                .compareTo(
                    Util.convertFromStringtoLocalDate(ArgsParameter.getInstance().getEndDate()))
            == 0
        && dateNumber == ArgsParameter.getInstance().getBolusNumber()) {
      localDateTime =
          Util.convertFromStringtoLocalDateTime(ArgsParameter.getInstance().getStartDate());
      dateNumber = 1;
      return localDateTime.format(formatter);
    }
    if (dateNumber == ArgsParameter.getInstance().getBolusNumber()) {
      localDateTime = localDateTime.plusDays(1);
      dateNumber = 1;
    } else {
      dateNumber++;
    }
    return localDateTime.format(formatter);
  }

  /** {@inheritDoc} */
  @Override
  public List<BolusReading> generate(String file) {
    return generateFromFile(
        ArgsParameter.getInstance().getPreset() == null
            ? Preset.randomPreset().getAddress()
            : file);
  }

  /**
   * Get bolus information for bolus from file
   *
   * @param file It concerns to the url to access the file.
   * @return list of events.
   */
  private List<BolusReading> generateFromFile(String file) {
    try {
      return Util.getDeviceDataDataSet(file).getBolusDataLog().getBolus().stream()
          .map(this::buildObject)
          .collect(Collectors.toList());
    } catch (JAXBException exception) {
      log.error("Error when generating BolusReading.");
    }
    return Collections.emptyList();
  }

  /**
   * Method responsible for converting an object from BolusFromXml to BolusReading
   *
   * @param bolusFromXml it concerns to the information that were extracted from xml file
   * @return An object from type BolusReading
   */
  private BolusReading buildObject(BolusFromXml bolusFromXml) {
    return BolusReading.builder()
        .active(bolusFromXml.getActive())
        .manual(bolusFromXml.getManual())
        .readingDate(generateReadingDateFormatted())
        .id(generateId())
        .lastUpdatedDate(System.currentTimeMillis())
        .annotation(generateAnnotations(bolusFromXml.getAnnotation()))
        .injectedInsulinType(ArgsParameter.getInstance().getBolusType())
        .bolusDelivered(generateBolusDelivered(bolusFromXml.getBolusDelivered()))
        .editable(bolusFromXml.getEditable())
        .build();
  }
}
