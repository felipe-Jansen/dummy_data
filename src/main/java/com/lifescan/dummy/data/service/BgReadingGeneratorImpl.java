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

import com.lifescan.dummy.data.enums.Preset;
import com.lifescan.dummy.data.model.ArgsParameter;
import com.lifescan.dummy.data.model.BgReading;
import com.lifescan.dummy.data.model.BgValue;
import com.lifescan.dummy.data.model.xml.BgReadingFromXml;
import com.lifescan.dummy.data.model.xml.BgValueFromXml;
import com.lifescan.dummy.data.service.util.Util;
import java.time.Instant;
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

  /**
   * Method responsible for generating a single bg value.
   *
   * @return A single bolusFromXmls reading.
   * @param bgValue Concerns to the data that comes from xml file
   */
  public static BgValue generateBgValue(BgValueFromXml bgValue) {
    return BgValue.builder().value(bgValue.getValue()).units(bgValue.getUnits()).build();
  }

  /** {@inheritDoc} */
  @Override
  public List<BgReading> generate(String file) {
    return generateFromFile(
        ArgsParameter.getInstance().getPreset() == null
            ? Preset.randomPreset().getAddress()
            : file);
  }

  private List<BgReading> generateFromFile(String file) {
    try {
      List<BgReading> listOfEvents =
          Util.getDeviceDataDataSet(file).getBgReadingDataLog().getBgReading().stream()
              .map(this::buildObject)
              .collect(Collectors.toList());
      return listOfEvents.subList(
          0,
          Util.getNumberOfEvents(
              listOfEvents.size(), ArgsParameter.getInstance().getReadingsNumber()));
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
        .readingDate(Util.generateReadingDateFormatted())
        .id(generateId())
        .extendedAttributes(generateAttributeValue(bgReading.getExtendedAttributes()))
        .bgValue(generateBgValue(bgReading.getBgValue()))
        .mealTag(bgReading.getMealTag())
        .lastUpdatedDate(Instant.now().toEpochMilli())
        .build();
  }
}
