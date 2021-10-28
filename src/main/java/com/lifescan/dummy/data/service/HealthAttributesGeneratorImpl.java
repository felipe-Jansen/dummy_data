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

import com.lifescan.dummy.data.constants.ConfigConstants;
import com.lifescan.dummy.data.model.ArgsParameter;
import com.lifescan.dummy.data.model.HealthAttribute;
import com.lifescan.dummy.data.model.xml.HealthAttribFromXml;
import com.lifescan.dummy.data.service.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HealthAttributesGeneratorImpl extends Generator implements HealthAttributeGenerator {

  /**
   * Method responsible for returning a list of health attributes.
   *
   * @return A list of health attributes.
   */
  @Override
  public List<HealthAttribute> generate(String file) {
    return ArgsParameter.getInstance().getPreset() == null
        ? generateDefault()
        : generateFromFile(file);
  }

  private List<HealthAttribute> generateDefault() {
    List<HealthAttribute> listOfEvents = new ArrayList<>();
    listOfEvents.add(
        HealthAttribute.builder()
            .active(ConfigConstants.ACTIVE_VALUE)
            .manual(ConfigConstants.MANUAL_VALUE)
            .readingDate(Util.generateReadingDateFormatted())
            .id(generateId())
            .lastUpdatedDate(System.currentTimeMillis())
            .healthAttributesValue(
                Util.getRandomNumberBetween(
                    ConfigConstants.MIN_VALUE_DURATION_ATTRIBUTE,
                    ConfigConstants.MAX_VALUE_DURATION_ATTRIBUTE))
            .healthAtributesLookup("HEALTH_ATTRIBUTE_EXERCISE")
            .editable(ConfigConstants.EDITABLE_VALUE)
            .extendedAttribute(null)
            .annotation(null)
            .build());
    return listOfEvents;
  }

  private List<HealthAttribute> generateFromFile(String file) {
    try {
      List<HealthAttribute> listOfEvents =
          Util.getDeviceDataDataSet(file).getHealthAttribsDataLog().getHealthAttrib().stream()
              .map(this::buildObject)
              .collect(Collectors.toList());
      return listOfEvents.subList(
          0,
          Util.getNumberOfEvents(
              listOfEvents.size(), ArgsParameter.getInstance().getExerciseNumbers()));
    } catch (JAXBException exception) {
      log.error("Error when generating bgReading.");
    }
    return Collections.emptyList();
  }

  /**
   * Method responsible for converting an object from HealthAttribFromXml to HealthAttribute
   *
   * @param healthAttribFromXml it concerns to the informations that were extracted from xml file
   * @return An object from type HealthAttribute
   */
  private HealthAttribute buildObject(HealthAttribFromXml healthAttribFromXml) {
    return HealthAttribute.builder()
        .active(healthAttribFromXml.getActive())
        .manual(healthAttribFromXml.getManual())
        .readingDate(Util.generateReadingDateFormatted())
        .id(generateId())
        .lastUpdatedDate(System.currentTimeMillis())
        .healthAttributesValue(
            Util.getRandomNumberBetween(
                ConfigConstants.MIN_VALUE_DURATION_ATTRIBUTE,
                ConfigConstants.MAX_VALUE_DURATION_ATTRIBUTE))
        .healthAtributesLookup(healthAttribFromXml.getHealthAtributesLookup())
        .editable(healthAttribFromXml.getEditable())
        .extendedAttribute(generateAttributeValue(healthAttribFromXml.getExtendedAttributes()))
        .annotation(generateAnnotations(healthAttribFromXml.getAnnotation()))
        .build();
  }
}
