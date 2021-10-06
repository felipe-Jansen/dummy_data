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
   * Method responsible for returning a list of bolus reading.
   *
   * @return
   */
  public static List<BolusReading> generator() {
    List<BolusReading> bolusReadings = new ArrayList<>();
    BolusReading bolusReading = new BolusReading();
    bolusReading.setActive("true");
    bolusReading.setManual("true");
    bolusReading.setReadingDate("2021-09-21 01:12:00");
    bolusReading.setId(String.valueOf(System.currentTimeMillis()));
    bolusReading.setLastUpdatedDate(System.currentTimeMillis());
    bolusReading.setAnnotation(generatingAnnotations());
    bolusReading.setBolusDelivered(generatingBolusDelivered());
    bolusReadings.add(bolusReading);
    return bolusReadings;
  }
}
