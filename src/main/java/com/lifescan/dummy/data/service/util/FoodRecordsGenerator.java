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
      for (FoodFromXml foodFromXml : getFoodRecords(file)) {
        foodRecords.add(buildObject(foodFromXml));
      }
    } catch (JAXBException ex) {
      log.error("Error when generating foodRecords");
    }
    return foodRecords;
  }

  /**
   * This method prevents the excessive reading to the xml file. When xml file is read for the first
   * time, all of his result is stored in a static attribute, then is read the value from this
   * attribute without accessing xml file.
   *
   * @param file file that will be readed
   * @return a list of FoodFromXml
   * @throws JAXBException
   */
  private static List<FoodFromXml> getFoodRecords(String file) throws JAXBException {
    List<FoodFromXml> food = Util.getDeviceDataDataSet(file).getFoodDataLog().getFood();
    return food.size() >= ArgsParameter.getInstance().getFoodNumbers()
        ? food.subList(0, ArgsParameter.getInstance().getFoodNumbers())
        : food;
  }

  /**
   * Method responsible for converting an object from FoodFromXml to FoodRecord
   *
   * @param foodFromXml it concerns to the informations that were extracted from xml file
   * @return An object from type FoodRecord
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
