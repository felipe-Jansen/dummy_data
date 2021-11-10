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
import com.lifescan.dummy.data.model.AttributeValue;
import com.lifescan.dummy.data.model.HealthAttribute;
import com.lifescan.dummy.data.model.xml.HealthAttribFromXml;
import com.lifescan.dummy.data.service.util.Util;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.bind.JAXBException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HealthAttributesGeneratorImpl extends Generator implements HealthAttributeGenerator {

  /** {@inheritDoc} */
  @Override
  public List<HealthAttribute> generate(String file) {
    if (file == null) return generateRandomValues();
    else return generateFromFile(file);
  }

  /**
   * Generate health randomized values
   *
   * @return list of health attributes
   */
  private List<HealthAttribute> generateRandomValues() {
    return Stream.generate(this::buildObject)
        .limit(Util.getNumberOfEvents(ArgsParameter.getInstance().getExerciseNumbers()))
        .collect(Collectors.toList());
  }

  /**
   * Method responsible for generating an object from value food
   *
   * @return health attribute object
   */
  private HealthAttribute buildObject() {
    return HealthAttribute.builder()
        .active(ConfigConstants.TRUE)
        .manual(ConfigConstants.FALSE)
        .readingDate(generateReadingDateFormatted())
        .id(generateId())
        .lastUpdatedDate(System.currentTimeMillis())
        .healthAttributesValue(
            Util.getRandomNumberBetween(
                ConfigConstants.MIN_VALUE_DURATION_ATTRIBUTE,
                ConfigConstants.MAX_VALUE_DURATION_ATTRIBUTE))
        .healthAtributesLookup("HEALTH_ATTRIBUTE_EXERCISE")
        .editable(ConfigConstants.FALSE)
        .extendedAttribute(generateAttributeValue())
        .annotation(null)
        .build();
  }

  private AttributeValue generateAttributeValue() {
    return AttributeValue.builder().value(generateNewAttributes()).build();
  }

  /**
   * Get health attribute information from file
   *
   * @param file It concerns to the url to access the file.
   * @return list of events.
   */
  private List<HealthAttribute> generateFromFile(String file) {
    try {
      return Util.getDeviceDataDataSet(file).getHealthAttribsDataLog().getHealthAttrib().stream()
          .map(this::buildObject)
          .collect(Collectors.toList());
    } catch (JAXBException exception) {
      log.error("Error when generating HealthAttributes.");
    }
    return Collections.emptyList();
  }

  /**
   * Method responsible for converting an object from HealthAttribFromXml to HealthAttribute
   *
   * @param healthAttribFromXml it concerns to the information that were extracted from xml file
   * @return An object from type HealthAttribute
   */
  private HealthAttribute buildObject(HealthAttribFromXml healthAttribFromXml) {
    return HealthAttribute.builder()
        .active(healthAttribFromXml.getActive())
        .manual(healthAttribFromXml.getManual())
        .readingDate(generateReadingDateFormatted())
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
