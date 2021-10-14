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

import com.lifescan.dummy.data.model.BolusReading;
import com.lifescan.dummy.data.model.xml.BolusFromXml;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import lombok.extern.log4j.Log4j2;

/** Class responsible for generating the objects with type bolusReading. */
@Log4j2
public class BolusReadingGenerator extends Generator {

  /**
   * Method responsible for returning a list of bolusFromXmls reading.
   *
   * @return A list of bolusFromXmls readings.
   */
  public static List<BolusReading> returnFromFile(String file) {

    List<BolusReading> bolusReadings = new ArrayList<>();
    try {
      for (BolusFromXml bolusFromXml : getDeviceDataDataSet(file).getBolusDataLog().getBolus()) {
        bolusReadings.add(buildObject(bolusFromXml));
      }
    } catch (JAXBException ex) {
      log.error("Error when generating bolusReading");
    }
    return bolusReadings;
  }

  private static BolusReading buildObject(BolusFromXml bolusFromXml) {
    return BolusReading.builder()
        .active(bolusFromXml.getActive())
        .manual(bolusFromXml.getManual())
        .readingDate(getReadingDateFormatted())
        .id(String.valueOf(System.currentTimeMillis()))
        .lastUpdatedDate(System.currentTimeMillis())
        .annotation(generatingAnnotations(bolusFromXml.getAnnotation()))
        .injectedInsulinType(bolusFromXml.getInjectedInsulinType())
        .bolusDelivered(generatingBolusDelivered(bolusFromXml.getBolusDelivered()))
        .editable(bolusFromXml.getEditable())
        .build();
  }
}
