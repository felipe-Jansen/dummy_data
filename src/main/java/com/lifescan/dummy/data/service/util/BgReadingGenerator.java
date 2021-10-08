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
import java.util.ArrayList;
import java.util.List;

/** Class responsible for generating the objects with type bgReading. */
public class BgReadingGenerator extends Generator {

  /**
   * Method responsible for returning a list of bgReading.
   *
   * @return A list of blood glucose readings.
   */
  public static List<BgReading> generator() {
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
