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

import com.lifescan.dummy.data.model.Carbohydrate;
import com.lifescan.dummy.data.model.FoodRecord;
import com.lifescan.dummy.data.model.xml.CarbohydrateFromXml;
import com.lifescan.dummy.data.model.xml.FoodFromXml;
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
public class FoodRecordGeneratorImpl extends Generator implements FoodRecordsGenerator {

  /**
   * Method responsible for ganerating a single carbohydrate.
   *
   * @return A single carbohydrate.
   * @param carbohydrates it concerns to the data that comes from xml file
   */
  protected static Carbohydrate generateCarbohydrates(CarbohydrateFromXml carbohydrates) {
    return Carbohydrate.builder()
        .value(carbohydrates.getValue())
        .units(carbohydrates.getUnits())
        .build();
  }

  /**
   * Method responsible for returning a list of foodFromXml records.
   *
   * @return A list of foodFromXml records.
   */
  @Override
  public List<FoodRecord> generate(String file) {
    try {
      return Util.getDeviceDataDataSet(file).getFoodDataLog().getFood().stream()
          .map(this::buildObject)
          .collect(Collectors.toList());
    } catch (JAXBException exception) {
      log.error("Error when generating bgReading.");
    }
    return Collections.emptyList();
  }

  /**
   * Method responsible for converting an object from FoodFromXml to FoodRecord
   *
   * @param foodFromXml it concerns to the informations that were extracted from xml file
   * @return An object from type FoodRecord
   */
  private FoodRecord buildObject(FoodFromXml foodFromXml) {
    return FoodRecord.builder()
        .active(foodFromXml.getActive())
        .manual(foodFromXml.getManual())
        .readingDate(Util.generateReadingDateFormatted())
        .id(generatingId())
        .lastUpdatedDate(System.currentTimeMillis())
        .annotation(generatingAnnotations(foodFromXml.getAnnotation()))
        .carbohydrates(generateCarbohydrates(foodFromXml.getCarbohydrates()))
        .editable(foodFromXml.getEditable())
        .build();
  }
}
