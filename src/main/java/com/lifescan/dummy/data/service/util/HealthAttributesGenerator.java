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

  public static List<HealthAttribute> generator() {
    List<HealthAttribute> foodRecords = new ArrayList<>();
    HealthAttribute healthAttribute = new HealthAttribute();
    healthAttribute.setActive("true");
    healthAttribute.setManual("true");
    healthAttribute.setReadingDate("2021-09-21 01:12:00");
    healthAttribute.setId(String.valueOf(System.currentTimeMillis()));
    healthAttribute.setLastUpdatedDate(System.currentTimeMillis());
    healthAttribute.setAnnotation(generatingAnnotations());
    healthAttribute.setHealthAttributesValue(120);
    healthAttribute.setExtendedAttributeValue(generatingAttributeValue());
    healthAttribute.setHealthAtributesLookup("HEALTH_ATTRIBUTE_EXERCISE");
    foodRecords.add(healthAttribute);
    return foodRecords;
  }
}
