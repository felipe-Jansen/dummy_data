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

import com.lifescan.dummy.data.enums.Preset;
import com.lifescan.dummy.data.model.ArgsParameter;
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
   * Method responsible for generating a single carbohydrate.
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

  /** {@inheritDoc} */
  @Override
  public List<FoodRecord> generate(String file) {
    return generateFromFile(
        ArgsParameter.getInstance().getPreset() == null
            ? Preset.randomPreset().getAddress()
            : file);
  }

  private List<FoodRecord> generateFromFile(String file) {
    try {
      List<FoodRecord> listOfEvents =
          Util.getDeviceDataDataSet(file).getFoodDataLog().getFood().stream()
              .map(this::buildObject)
              .collect(Collectors.toList());
      return listOfEvents.subList(
          0,
          Util.getNumberOfEvents(
              listOfEvents.size(), ArgsParameter.getInstance().getFoodNumbers()));
    } catch (JAXBException exception) {
      log.error("Error when generating foodRecord.");
    }
    return Collections.emptyList();
  }

  /**
   * Method responsible for converting an object from FoodFromXml to FoodRecord
   *
   * @param foodFromXml it concerns to the information that were extracted from xml file
   * @return An object from type FoodRecord
   */
  private FoodRecord buildObject(FoodFromXml foodFromXml) {
    return FoodRecord.builder()
        .active(foodFromXml.getActive())
        .manual(foodFromXml.getManual())
        .readingDate(Util.generateReadingDateFormatted())
        .id(generateId())
        .lastUpdatedDate(System.currentTimeMillis())
        .annotation(generateAnnotations(foodFromXml.getAnnotation()))
        .carbohydrates(generateCarbohydrates(foodFromXml.getCarbohydrates()))
        .editable(foodFromXml.getEditable())
        .build();
  }
}
