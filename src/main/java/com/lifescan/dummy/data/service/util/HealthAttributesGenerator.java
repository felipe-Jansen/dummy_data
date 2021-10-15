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
import com.lifescan.dummy.data.model.xml.HealthAttribFromXml;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class HealthAttributesGenerator extends Generator {

  /**
   * Method responsible for returning a list of health attributes.
   *
   * @return A list of health attributes.
   */
  public static List<HealthAttribute> returnFromFile(String file) {

    List<HealthAttribute> healthAttributes = new ArrayList<>();
    try {
      for (HealthAttribFromXml healthAttribFromXml :
          getDeviceDataDataSet(file).getHealthAttribsDataLog().getHealthAttrib()) {
        healthAttributes.add(buildObject(healthAttribFromXml));
      }
    } catch (JAXBException ex) {
      log.error("Error when generating healthAttributes");
    }
    return healthAttributes;
  }

  private static HealthAttribute buildObject(HealthAttribFromXml healthAttribFromXml) {
    return HealthAttribute.builder()
        .active(healthAttribFromXml.getActive())
        .manual(healthAttribFromXml.getManual())
        .readingDate(generatingReadingDateFormatted())
        .id(generatingId())
        .lastUpdatedDate(System.currentTimeMillis())
        .healthAttributesValue(healthAttribFromXml.getHealthAttributesValue())
        .healthAtributesLookup(healthAttribFromXml.getHealthAtributesLookup())
        .editable(healthAttribFromXml.getEditable())
        .extendedAttribute(generatingAttributeValue(healthAttribFromXml.getExtendedAttributes()))
        .annotation(generatingAnnotations(healthAttribFromXml.getAnnotation()))
        .build();
  }
}
