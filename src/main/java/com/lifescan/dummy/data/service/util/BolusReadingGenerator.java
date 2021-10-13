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

import com.lifescan.dummy.data.model.BolusReading;
import java.util.ArrayList;
import java.util.List;

/** Class responsible for generating the objects with type bolusReading. */
public class BolusReadingGenerator extends Generator {

  /**
   * Method responsible for returning a list of bolusFromXmls reading.
   *
   * @return A list of bolusFromXmls readings.
   */
  public static List<BolusReading> generator() {
    List<BolusReading> bolusReadings = new ArrayList<>();
    bolusReadings.add(
        BolusReading.builder()
            .active("true")
            .manual("true")
            .readingDate("2021-09-21 01:12:00") // This will be informed usin the xml file
            .id(String.valueOf(System.currentTimeMillis()))
            .lastUpdatedDate(System.currentTimeMillis())
            // .annotation(generatingAnnotations(foodFromXml.getAnnotation().getAnnotationFromXml()))
            .injectedInsulinType("BOLUS_INSULIN_SHORT")
            .bolusDelivered(generatingBolusDelivered())
            .build());
    return bolusReadings;
  }
}
