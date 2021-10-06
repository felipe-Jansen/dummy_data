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
import java.util.ArrayList;
import java.util.List;

/** Class responsible for generating the objects with type foodRecord. */
public class FoodRecordsGenerator extends Generator {

  /**
   * Method responsible for returning a list of food records.
   *
   * @return
   */
  public static List<FoodRecord> generator() {
    List<FoodRecord> foodRecords = new ArrayList<>();
    FoodRecord foodRecord = new FoodRecord();
    foodRecord.setActive("true");
    foodRecord.setManual("true");
    foodRecord.setReadingDate("2021-09-21 01:12:00");
    foodRecord.setId(String.valueOf(System.currentTimeMillis()));
    foodRecord.setLastUpdatedDate(System.currentTimeMillis());
    foodRecord.setAnnotation(generatingAnnotations());
    foodRecord.setCarbohydrates(generatingCarbohydrates());
    foodRecords.add(foodRecord);
    return foodRecords;
  }
}
