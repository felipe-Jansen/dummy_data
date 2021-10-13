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
import com.lifescan.dummy.data.model.xml.DeviceDataDataSet;
import com.lifescan.dummy.data.model.xml.HealthAttribFromXml;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class HealthAttributesGenerator extends Generator {

  /**
   * Method responsible for returning a list of health attributes.
   *
   * @return A list of health attributes.
   */
  public static List<HealthAttribute> returnFromFile(String file) {

    List<HealthAttribute> healthAttributes = new ArrayList<>();
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(DeviceDataDataSet.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      DeviceDataDataSet data = (DeviceDataDataSet) jaxbUnmarshaller.unmarshal(new File(file));

      for (HealthAttribFromXml healthAttribFromXml :
          data.getHealthAttribsDataLog().getHealthAttrib()) {
        healthAttributes.add(buildObject(healthAttribFromXml));
      }
    } catch (JAXBException ex) {
    }
    return healthAttributes;
  }

  private static HealthAttribute buildObject(HealthAttribFromXml healthAttribFromXml) {
    return HealthAttribute.builder()
        .active(healthAttribFromXml.getActive())
        .manual(healthAttribFromXml.getManual())
        .readingDate(healthAttribFromXml.getReadingDate())
        .id(String.valueOf(System.currentTimeMillis()))
        .lastUpdatedDate(System.currentTimeMillis())
        .healthAttributesValue(healthAttribFromXml.getHealthAttributesValue())
        .healthAtributesLookup(healthAttribFromXml.getHealthAtributesLookup())
        .editable(healthAttribFromXml.getEditable())
        .extendedAttribute(generatingAttributeValue(healthAttribFromXml.getExtendedAttributes()))
        .annotation(generatingAnnotations(healthAttribFromXml.getAnnotation()))
        .build();
  }
}
