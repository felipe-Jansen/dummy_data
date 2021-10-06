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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lifescan.dummy.data.model.Event;
import com.lifescan.dummy.data.model.Login;
import com.lifescan.dummy.data.model.Meta;
import com.lifescan.dummy.data.networking.service.EventServiceCore;
import com.lifescan.dummy.data.service.util.BgReadingGenerator;
import com.lifescan.dummy.data.service.util.BolusReadingGenerator;
import com.lifescan.dummy.data.service.util.FoodRecordsGenerator;
import com.lifescan.dummy.data.service.util.HealthAttributesGenerator;
import feign.FeignException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class EventServiceImpl implements EventService {

  private final SecurityService securityService;
  private final EventServiceCore eventServiceCore;

  public EventServiceImpl(SecurityService securityService, EventServiceCore eventServiceCore) {
    this.securityService = securityService;
    this.eventServiceCore = eventServiceCore;
  }

  /**
   * Method responsible for creating the events for each new patient.
   *
   * @param login
   * @throws JsonProcessingException
   */
  @Override
  public void publishEvent(Login login) {
    try {
      String token = securityService.getToken(login);
      log.info("token -> {}", token);
      eventServiceCore.publishEvent(token, generatingEvent());
      log.info("Event published");
    } catch (FeignException ex) {
      log.error(ex.contentUTF8());
    }
  }

  /**
   * Method responsible for generating events.
   *
   * @return
   */
  private Event generatingEvent() {
    Event event = new Event();
    event.setBgReadings(BgReadingGenerator.generator());
    event.setBolusReadings(BolusReadingGenerator.generator());
    event.setFoodRecords(FoodRecordsGenerator.generator());
    event.setHealthAttributes(HealthAttributesGenerator.generator());
    event.setBackgroundSync(false);
    event.setMeta(generatingMeta());
    return event;
  }

  /**
   * Method responsible for generating the meta information.
   *
   * @return
   */
  private Meta generatingMeta() {
    Meta meta = new Meta();
    meta.setSourceApp("REVEAL_MOBILE_IOS");
    meta.setSourceAppVersion("5.3.1");
    return meta;
  }
}
