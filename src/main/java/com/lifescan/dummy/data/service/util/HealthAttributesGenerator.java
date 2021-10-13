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

import com.lifescan.dummy.data.model.HealthAttribute;
import java.util.ArrayList;
import java.util.List;

public class HealthAttributesGenerator extends Generator {

  /**
   * Method responsible for returning a list of health attributes.
   *
   * @return A list of health attributes.
   */
  public static List<HealthAttribute> generator() {
    List<HealthAttribute> foodRecords = new ArrayList<>();
    //    foodRecords.add(
    //        HealthAttribute.builder()
    //            .active("true")
    //            .manual("true")
    //            .readingDate("2021-09-21 01:12:00")
    //            .id(String.valueOf(System.currentTimeMillis()))
    //            .lastUpdatedDate(System.currentTimeMillis())
    //            .annotationFromXml(generatingAnnotations())
    //            .healthAttributesValue(120)
    //
    // .extendedAttribute(generatingAttributeValue(bgReadingFromXml.getExtendedAttributes()))
    //            .healthAtributesLookup("HEALTH_ATTRIBUTE_EXERCISE")
    //            .build());
    return foodRecords;
  }
}
