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
import com.lifescan.dummy.data.model.HealthAttribute;
import com.lifescan.dummy.data.model.xml.HealthAttribFromXml;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class HealthAttributesGenerator {

  /**
   * Method responsible for returning a list of health attributes.
   *
   * @return A list of health attributes.
   */
  public static List<HealthAttribute> returnFromFile(String file) {

    List<HealthAttribute> healthAttributes = new ArrayList<>();
    try {
      for (HealthAttribFromXml healthAttribFromXml : getHealthAttributes(file)) {
        healthAttributes.add(buildObject(healthAttribFromXml));
      }
    } catch (JAXBException ex) {
      log.error("Error when generating healthAttributes");
    }
    return healthAttributes;
  }

  /**
   * This method prevents the excessive reading to the xml file. When xml file is read for the first
   * time, all of his result is stored in a static attribute, then is read the value from this
   * attribute without accessing xml file.
   *
   * @param file file that will be readed
   * @return a list of HealthAttribFromXml
   * @throws JAXBException
   */
  private static List<HealthAttribFromXml> getHealthAttributes(String file) throws JAXBException {
    List<HealthAttribFromXml> healthAttrib =
        Util.getDeviceDataDataSet(file).getHealthAttribsDataLog().getHealthAttrib();
    return healthAttrib.size() >= ArgsParameter.getInstance().getFoodNumbers()
        ? healthAttrib.subList(0, ArgsParameter.getInstance().getFoodNumbers())
        : healthAttrib;
  }

  /**
   * Method responsible for converting an object from HealthAttribFromXml to HealthAttribute
   *
   * @param healthAttribFromXml it concerns to the informations that were extracted from xml file
   * @return An object from type HealthAttribute
   */
  private static HealthAttribute buildObject(HealthAttribFromXml healthAttribFromXml) {
    return HealthAttribute.builder()
        .active(healthAttribFromXml.getActive())
        .manual(healthAttribFromXml.getManual())
        .readingDate(Util.generatingReadingDateFormatted())
        .id(Util.generatingId())
        .lastUpdatedDate(System.currentTimeMillis())
        .healthAttributesValue(healthAttribFromXml.getHealthAttributesValue())
        .healthAtributesLookup(healthAttribFromXml.getHealthAtributesLookup())
        .editable(healthAttribFromXml.getEditable())
        .extendedAttribute(
            Util.generatingAttributeValue(healthAttribFromXml.getExtendedAttributes()))
        .annotation(Util.generatingAnnotations(healthAttribFromXml.getAnnotation()))
        .build();
  }
}
