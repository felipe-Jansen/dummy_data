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

import com.lifescan.dummy.data.model.Event;
import com.lifescan.dummy.data.model.Login;
import com.lifescan.dummy.data.model.Meta;
import com.lifescan.dummy.data.networking.service.EventServiceCore;
import com.lifescan.dummy.data.service.util.BgReadingGenerator;
import com.lifescan.dummy.data.service.util.BolusReadingGenerator;
import com.lifescan.dummy.data.service.util.FoodRecordsGenerator;
import com.lifescan.dummy.data.service.util.HealthAttributesGenerator;
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

  /** {@inheritDoc} */
  @Override
  public void publishEvent(Login login) {
    try {
      eventServiceCore.publishEvent(securityService.getToken(login), generatingEvent());
    } catch (FeignException ex) {
      log.error(ex.contentUTF8());
    }
  }

  /**
   * Method responsible for generating events.
   *
   * @return An object from type Event, that contains the informations readings.
   */
  private Event generatingEvent() {
    return Event.builder()
        .bgReadings(BgReadingGenerator.generator())
        .foodRecords(FoodRecordsGenerator.generator())
        .bolusReadings(BolusReadingGenerator.generator())
        .healthAttributes(HealthAttributesGenerator.generator())
        .isBackgroundSync(false)
        .meta(generatingMeta())
        .build();
  }

  /**
   * Method responsible for generating the meta information.
   *
   * @return A single object from type Meta.
   */
  private Meta generatingMeta() {

    return Meta.builder().sourceApp("REVEAL_MOBILE_IOS").sourceAppVersion("5.3.1").build();
  }
}
