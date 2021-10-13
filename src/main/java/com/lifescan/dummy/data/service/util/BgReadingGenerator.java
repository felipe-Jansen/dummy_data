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
import com.lifescan.dummy.data.model.xml.DeviceDataDataSet;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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
      JAXBContext jaxbContext = JAXBContext.newInstance(DeviceDataDataSet.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      DeviceDataDataSet data = (DeviceDataDataSet) jaxbUnmarshaller.unmarshal(new File(file));

      for (BgReadingFromXml bgReadingFromXml : data.getBgReadingDataLog().getBgReading()) {
        bgReadings.add(buildObject(bgReadingFromXml));
      }
    } catch (JAXBException ex) {
    }
    return bgReadings;
  }

  private static BgReading buildObject(BgReadingFromXml bgReading) {
    return BgReading.builder()
        .active(bgReading.getActive())
        .manual(bgReading.getManual())
        .readingDate(bgReading.getReadingDate())
        .id(String.valueOf(System.currentTimeMillis()))
        .extendedAttributes(generatingAttributeValue(bgReading.getExtendedAttributes()))
        .bgValue(generatingBgValue(bgReading.getBgValue()))
        .mealTag(bgReading.getMealTag())
        .lastUpdatedDate(System.currentTimeMillis())
        .build();
  }
}
