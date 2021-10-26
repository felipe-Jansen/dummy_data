/*
 * Class responsible for generating the objects with type bgReadingFromXml.
 *
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

import com.lifescan.dummy.data.model.BgReading;
import com.lifescan.dummy.data.model.xml.BgReadingFromXml;
import com.lifescan.dummy.data.service.util.Util;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BgReadingGeneratorImpl implements BgReadingGenerator {

  /** {@inheritDoc} */
  @Override
  public List<BgReading> generate(String file) {
    try {
      return Util.getDeviceDataDataSet(file).getBgReadingDataLog().getBgReading().stream()
          .map(this::buildObject)
          .collect(Collectors.toList());
    } catch (JAXBException exception) {
      log.error("Error when generating bgReading.");
    }

    return Collections.emptyList();
  }

  /**
   * Method responsible for converting from XML file to a java object.
   *
   * @param bgReading That concerns to the data that comes from xml file.
   * @return Data from xml file converted in a java object.
   */
  private BgReading buildObject(BgReadingFromXml bgReading) {
    return BgReading.builder()
        .active(bgReading.getActive())
        .manual(bgReading.getManual())
        .readingDate(Util.generatingReadingDateFormatted())
        .id(Util.generatingId())
        .extendedAttributes(Util.generatingAttributeValue(bgReading.getExtendedAttributes()))
        .bgValue(Util.generatingBgValue(bgReading.getBgValue()))
        .mealTag(bgReading.getMealTag())
        .lastUpdatedDate(Instant.now().toEpochMilli())
        .build();
  }
}
