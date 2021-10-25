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

import com.lifescan.dummy.data.model.ArgsParameter;
import com.lifescan.dummy.data.model.BolusReading;
import com.lifescan.dummy.data.model.xml.BolusFromXml;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import lombok.extern.log4j.Log4j2;

/** Class responsible for generating the objects with type bolusReading. */
@Log4j2
public class BolusReadingGenerator {

  /**
   * Method responsible for returning a list of bolusFromXmls reading.
   *
   * @return A list of bolusFromXmls readings.
   */
  public static List<BolusReading> returnFromFile(String file) {

    List<BolusReading> bolusReadings = new ArrayList<>();
    try {
      for (BolusFromXml bolusFromXml : getBolusReading(file)) {
        bolusReadings.add(buildObject(bolusFromXml));
      }
    } catch (JAXBException ex) {
      log.error("Error when generating bolusReading");
    }
    return bolusReadings;
  }

  /**
   * This method prevents the excessive reading to the xml file. When xml file is read for the first
   * time, all of his result is stored in a static attribute, then is read the value from this
   * attribute without accessing xml file.
   *
   * @param file file that will be readed
   * @return a list of BolusFromXml
   * @throws JAXBException
   */
  private static List<BolusFromXml> getBolusReading(String file) throws JAXBException {
    List<BolusFromXml> bolus = Util.getDeviceDataDataSet(file).getBolusDataLog().getBolus();
    return bolus.size() >= ArgsParameter.getInstance().getFoodNumbers()
        ? bolus.subList(0, ArgsParameter.getInstance().getFoodNumbers())
        : bolus;
  }

  /**
   * Method responsible for converting an object from BolusFromXml to BolusReading
   *
   * @param bolusFromXml it concerns to the informations that were extracted from xml file
   * @return An object from type BolusReading
   */
  private static BolusReading buildObject(BolusFromXml bolusFromXml) {
    return BolusReading.builder()
        .active(bolusFromXml.getActive())
        .manual(bolusFromXml.getManual())
        .readingDate(Util.generatingReadingDateFormatted())
        .id(Util.generatingId())
        .lastUpdatedDate(System.currentTimeMillis())
        .annotation(Util.generatingAnnotations(bolusFromXml.getAnnotation()))
        .injectedInsulinType(ArgsParameter.getInstance().getBolusType())
        .bolusDelivered(Util.generatingBolusDelivered(bolusFromXml.getBolusDelivered()))
        .editable(bolusFromXml.getEditable())
        .build();
  }
}
