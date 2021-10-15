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

import com.lifescan.dummy.data.model.BgReading;
import com.lifescan.dummy.data.model.xml.BgReadingFromXml;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import lombok.extern.log4j.Log4j2;

/** Class responsible for generating the objects with type bgReadingFromXml. */
@Log4j2
public class BgReadingGenerator extends Generator {

  /**
   * Method responsible for returning a list of bgReadingFromXml.
   *
   * @return A list of blood glucose readings.
   */
  public static List<BgReading> returnFromFile(String file) throws JAXBException {
    List<BgReading> bgReadings = new ArrayList<>();
    try {
      for (BgReadingFromXml bgReadingFromXml :
          getDeviceDataDataSet(file).getBgReadingDataLog().getBgReading()) {
        bgReadings.add(buildObject(bgReadingFromXml));
      }
    } catch (JAXBException ex) {
      log.error("Error when generating bgReading.");
    }
    return bgReadings;
  }

  private static BgReading buildObject(BgReadingFromXml bgReading) {
    return BgReading.builder()
        .active(bgReading.getActive())
        .manual(bgReading.getManual())
        .readingDate(generatingReadingDateFormatted())
        .id(generatingId())
        .extendedAttributes(generatingAttributeValue(bgReading.getExtendedAttributes()))
        .bgValue(generatingBgValue(bgReading.getBgValue()))
        .mealTag(bgReading.getMealTag())
        .lastUpdatedDate(System.currentTimeMillis())
        .build();
  }
}
