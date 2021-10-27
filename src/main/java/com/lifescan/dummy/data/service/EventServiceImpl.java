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
import com.lifescan.dummy.data.constants.PresetsConstants;
import com.lifescan.dummy.data.model.Event;
import com.lifescan.dummy.data.model.Login;
import com.lifescan.dummy.data.model.MetaInformation;
import com.lifescan.dummy.data.networking.service.EventServiceCore;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventServiceImpl implements EventService {

  private final SecurityService securityService;
  private final EventServiceCore eventServiceCore;
  private final BgReadingGenerator bgReadingGenerator;
  private final BolusReadingGenerator bolusReadingGenerator;
  private final FoodRecordsGenerator foodRecordsGenerator;
  private final HealthAttributeGenerator healthAttributeGenerator;

  /** {@inheritDoc} */
  @Override
  public void publishEvent(Login login) {
    try {
      eventServiceCore.publishEvent(
          securityService.doLogin(login), generatingEvent(PresetsConstants.HARRY));
      log.info("Events created with successfully!");
    } catch (FeignException ex) {
      if (log.isDebugEnabled()) {
        log.debug(ex.contentUTF8());
      }
    }
  }

  /**
   * Method responsible for generating events.
   *
   * @return An object from type Event, that contains the informations readings.
   * @param presetSelected preset informed by user.
   */
  private Event generatingEvent(String presetSelected) {
    return Event.builder()
        .bgReadings(bgReadingGenerator.generate(presetSelected))
        .foodRecords(foodRecordsGenerator.generate(presetSelected))
        .bolusReadings(bolusReadingGenerator.generate(presetSelected))
        .healthAttributes(healthAttributeGenerator.generate(presetSelected))
        .isBackgroundSync(false)
        .metaInformation(generatingMeta())
        .build();
  }

  /**
   * Method responsible for generating the metaInformation information.
   *
   * @return A single object from type MetaInformation.
   */
  private MetaInformation generatingMeta() {
    return MetaInformation.builder()
        .sourceApp(ConfigConstants.SOURCE_APP)
        .sourceAppVersion(ConfigConstants.APP_VERSION)
        .build();
  }
}
