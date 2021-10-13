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
import com.lifescan.dummy.data.model.xml.DeviceDataDataSet;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import lombok.extern.log4j.Log4j2;

/** Class responsible for generating the objects with type bgReading. */
@Log4j2
public class BgReadingGenerator extends Generator {

  /**
   * Method responsible for returning a list of bgReading.
   *
   * @return A list of blood glucose readings.
   */
  public static List<BgReading> generator() {
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(DeviceDataDataSet.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      DeviceDataDataSet employee = (DeviceDataDataSet) jaxbUnmarshaller.unmarshal(new File("src/main/resources/Marianne.xml"));
      // log.info("employee - {}", employee.getBgReadingDataLog());
    } catch (JAXBException ex) {
      log.error(ex.getMessage());
    }

    List<BgReading> bgReadings = new ArrayList<>();
    bgReadings.add(
        BgReading.builder()
            .active("true")
            .manual("true")
            .readingDate("2021-09-21 01:12:00")
            .id(String.valueOf(System.currentTimeMillis()))
            .extendedAttributes(generatingAttributeValue())
            .bgValue(generatingBgValue())
            .mealTag("MEAL_TAG_POST_MEAL")
            .lastUpdatedDate(System.currentTimeMillis())
            .build());
    return bgReadings;
  }
}
