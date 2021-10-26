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
import com.lifescan.dummy.data.model.BgReading;
import com.lifescan.dummy.data.model.xml.BgReadingFromXml;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import lombok.extern.log4j.Log4j2;

/** Class responsible for generating the objects with type bgReadingFromXml. */
@Log4j2
public class BgReadingGenerator {

  /**
   * Method responsible for returning a list of bgReadingFromXml.
   *
   * @return A list of blood glucose readings.
   */
  public static List<BgReading> returnFromFile(String file) throws JAXBException {
    List<BgReading> bgReadings = new ArrayList<>();
    try {
      for (BgReadingFromXml bgReadingFromXml : getBgReadings(file)) {
        bgReadings.add(buildObject(bgReadingFromXml));
      }
    } catch (JAXBException ex) {
      log.error("Error when generating bgReading.");
    }
    return bgReadings;
  }

  /**
   * This method prevents the excessive reading to the xml file. When xml file is read for the first
   * time, all of his result is stored in a static attribute, then is read the value from this
   * attribute without accessing xml file.
   *
   * @param file file that will be read
   * @return a list of BgReadingFromXml
   * @throws JAXBException
   */
  private static List<BgReadingFromXml> getBgReadings(String file) throws JAXBException {
    List<BgReadingFromXml> bgReading =
        Util.getDeviceDataDataSet(file).getBgReadingDataLog().getBgReading();
    return bgReading.size() >= ArgsParameter.getInstance().getFoodNumbers()
        ? bgReading.subList(0, ArgsParameter.getInstance().getFoodNumbers())
        : bgReading;
  }

  /**
   * Method responsible for converting an object from BgReadingFromXml to BgReading
   *
   * @param bgReading it concerns to informations that were extracted from xml file
   * @return An object from type BgReading
   */
  private static BgReading buildObject(BgReadingFromXml bgReading) {
    return BgReading.builder()
        .active(bgReading.getActive())
        .manual(bgReading.getManual())
        .readingDate(Util.generatingReadingDateFormatted())
        .id(Util.generatingId())
        .extendedAttributes(Util.generatingAttributeValue(bgReading.getExtendedAttributes()))
        .bgValue(Util.generatingBgValue(bgReading.getBgValue()))
        .mealTag(ArgsParameter.getInstance().getReadingsTag())
        .lastUpdatedDate(System.currentTimeMillis())
        .build();
  }
}
