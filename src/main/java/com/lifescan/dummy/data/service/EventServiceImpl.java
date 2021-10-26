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
import com.lifescan.dummy.data.model.Meta;
import com.lifescan.dummy.data.networking.service.EventServiceCore;
import com.lifescan.dummy.data.service.util.BolusReadingGenerator;
import com.lifescan.dummy.data.service.util.FoodRecordsGenerator;
import com.lifescan.dummy.data.service.util.HealthAttributesGenerator;
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
  private final BgReadingGeneratorImpl readingGenerator;

  /** {@inheritDoc} */
  @Override
  public void publishEvent(Login login) {
    // @TODO We receive the preset name via args. Harry is fixed here.
    // Create enum with mapping of IDs and file names.
    eventServiceCore.publishEvent(
        securityService.doLogin(login), generatingEvent(PresetsConstants.HARRY));
    log.traceExit("Events synced successfully!");
  }

  /**
   * Method responsible for generating events.
   *
   * @param presetSelected preset informed by user.
   * @return An object from type Event, that contains the information readings.
   */
  private Event generatingEvent(String presetSelected) {
    return Event.builder()
        .bgReadings(readingGenerator.generate(presetSelected))
        .foodRecords(FoodRecordsGenerator.returnFromFile(presetSelected))
        .bolusReadings(BolusReadingGenerator.returnFromFile(presetSelected))
        .healthAttributes(HealthAttributesGenerator.returnFromFile(presetSelected))
        .isBackgroundSync(false)
        .meta(generateMeta())
        .build();
  }

  /**
   * Method responsible for generating the meta information.
   *
   * @return A single object from type Meta.
   */
  private Meta generateMeta() {
    return Meta.builder()
        .sourceApp(ConfigConstants.SOURCE_APP)
        .sourceAppVersion(ConfigConstants.APP_VERSION)
        .build();
  }
}
