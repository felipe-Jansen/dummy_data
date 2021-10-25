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

import com.lifescan.dummy.data.model.FoodRecord;
import com.lifescan.dummy.data.model.xml.FoodFromXml;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import lombok.extern.log4j.Log4j2;

/** Class responsible for generating the objects with type foodRecord. */
@Log4j2
public class FoodRecordsGenerator {

  /**
   * Method responsible for returning a list of foodFromXml records.
   *
   * @return A list of foodFromXml records.
   */
  public static List<FoodRecord> returnFromFile(String file) {

    List<FoodRecord> foodRecords = new ArrayList<>();
    try {
      for (FoodFromXml foodFromXml : Util.getDeviceDataDataSet(file).getFoodDataLog().getFood()) {
        foodRecords.add(buildObject(foodFromXml));
      }
    } catch (JAXBException ex) {
      log.error("Error when generating foodRecords");
    }
    return foodRecords;
  }

  /**
   * Method responsible for converting from XML file to a java object.
   *
   * @param foodFromXml That concerns to the data that comes from xml file.
   * @return Data from xml file converted in a java object.
   */
  private static FoodRecord buildObject(FoodFromXml foodFromXml) {
    return FoodRecord.builder()
        .active(foodFromXml.getActive())
        .manual(foodFromXml.getManual())
        .readingDate(Util.generatingReadingDateFormatted())
        .id(Util.generatingId())
        .lastUpdatedDate(System.currentTimeMillis())
        .annotation(Util.generatingAnnotations(foodFromXml.getAnnotation()))
        .carbohydrates(Util.generatingCarbohydrates(foodFromXml.getCarbohydrates()))
        .editable(foodFromXml.getEditable())
        .build();
  }
}
