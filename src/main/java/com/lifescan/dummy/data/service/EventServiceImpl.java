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
import com.lifescan.dummy.data.enums.Preset;
import com.lifescan.dummy.data.model.ArgsParameter;
import com.lifescan.dummy.data.model.Event;
import com.lifescan.dummy.data.model.Login;
import com.lifescan.dummy.data.model.MetaInformation;
import com.lifescan.dummy.data.model.Patient;
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
  private final FoodRecordGenerator foodRecordGenerator;
  private final HealthAttributeGenerator healthAttributeGenerator;
  private final PatientService patientService;

  /** {@inheritDoc} */
  @Override
  public void create(String language, Integer numberPatients) {
    log.traceEntry("language:{}, numberPatients:{}", language, numberPatients);
    for (int i = 0; i < numberPatients; i++) {
      Patient patient = patientService.create(language, getCountry(language));
      publishFromPreset(
          Login.builder().email(patient.getEmailAddress()).password(patient.getPassword()).build(),
          ArgsParameter.getInstance().getPreset());
    }
  }

  /**
   * Method responsible for creating the events for each new patient.
   *
   * @param login that contains the information of email and password.
   */
  private void publishFromPreset(Login login, Preset preset) {
    try {
      eventServiceCore.publishEvent(
          securityService.doLogin(login), generateEventFromPreset(preset));
      log.info("Event created successfully");
    } catch (FeignException ex) {
      log.debug(ex.contentUTF8());
    }
  }

  /**
   * Method responsible for discovery the country from informed languageIsoCode.
   *
   * @param languageIsoCode that contains the information to extract the country.
   * @return The country informed in the languageIsoCode.
   */
  private String getCountry(String languageIsoCode) {
    try {
      if (languageIsoCode.contains("-")) {
        return log.traceExit(languageIsoCode.split("-")[1]);
      } else {
        return log.traceExit(languageIsoCode.split("_")[1]);
      }
    } catch (ArrayIndexOutOfBoundsException exception) {
      return log.traceExit(languageIsoCode);
    }
  }

  /**
   * Method responsible for generating events.
   *
   * @return An object from type Event, that contains the information readings.
   * @param presetSelected preset informed by user.
   */
  private Event generateEventFromPreset(Preset presetSelected) {
    String file = presetSelected != null ? presetSelected.getAddress() : null;
    return Event.builder()
        .bgReadings(bgReadingGenerator.generate(file))
        .foodRecords(foodRecordGenerator.generate(file))
        .bolusReadings(bolusReadingGenerator.generate(file))
        .healthAttributes(healthAttributeGenerator.generate(file))
        .isBackgroundSync(false)
        .metaInformation(generateMeta())
        .build();
  }

  /**
   * Method responsible for generating the metaInformation information.
   *
   * @return A single object from type MetaInformation.
   */
  private MetaInformation generateMeta() {
    return MetaInformation.builder()
        .sourceApp(ConfigConstants.SOURCE_APP)
        .sourceAppVersion(ConfigConstants.APP_VERSION)
        .build();
  }
}
