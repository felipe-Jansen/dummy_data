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

import com.lifescan.dummy.data.model.BolusDelivered;
import com.lifescan.dummy.data.model.BolusReading;
import com.lifescan.dummy.data.model.xml.BolusDeliveredFromXml;
import com.lifescan.dummy.data.model.xml.BolusFromXml;
import com.lifescan.dummy.data.service.util.Generator;
import com.lifescan.dummy.data.service.util.Util;
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

  /**
   * Method responsible for ganerating a single bolusFromXmls delivered.
   *
   * @return A single bolusFromXmls delivered.
   * @param bolusDelivered it concerns to the data that comes from xml file
   */
  protected static BolusDelivered generatingBolusDelivered(BolusDeliveredFromXml bolusDelivered) {
    return BolusDelivered.builder()
        .value(bolusDelivered.getValue())
        .units(bolusDelivered.getUnits())
        .build();
  }

  /**
   * Method responsible for returning a list of bolusFromXmls reading.
   *
   * @return A list of bolusFromXmls readings.
   */
  public List<BolusReading> generate(String file) {
    try {
      return Util.getDeviceDataDataSet(file).getBolusDataLog().getBolus().stream()
          .map(this::buildObject)
          .collect(Collectors.toList());
    } catch (JAXBException exception) {
      log.error("Error when generating bgReading.");
    }
    return Collections.emptyList();
  }

  /**
   * Method responsible for converting an object from BolusFromXml to BolusReading
   *
   * @param bolusFromXml it concerns to the informations that were extracted from xml file
   * @return An object from type BolusReading
   */
  private BolusReading buildObject(BolusFromXml bolusFromXml) {
    return BolusReading.builder()
        .active(bolusFromXml.getActive())
        .manual(bolusFromXml.getManual())
        .readingDate(Util.generateReadingDateFormatted())
        .id(generatingId())
        .lastUpdatedDate(System.currentTimeMillis())
        .annotation(generatingAnnotations(bolusFromXml.getAnnotation()))
        .injectedInsulinType(bolusFromXml.getInjectedInsulinType())
        .bolusDelivered(generatingBolusDelivered(bolusFromXml.getBolusDelivered()))
        .editable(bolusFromXml.getEditable())
        .build();
  }
}
