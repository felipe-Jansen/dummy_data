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
import com.lifescan.dummy.data.model.ListOfPatients;
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
  private final FoodRecordsGenerator foodRecordsGenerator;
  private final HealthAttributeGenerator healthAttributeGenerator;
  private final PatientService patientService;

  /** {@inheritDoc} */
  @Override
  public void create(String language, Integer numberPatients) {
    log.traceEntry("language:{}, numberPatients:{}", language, numberPatients);
    for (int i = 0; i < numberPatients; i++) {
      Patient patient = patientService.create(language, getCountry(language));
      publishEvent(
          Login.builder().email(patient.getEmailAddress()).password(patient.getPassword()).build(),
          ArgsParameter.getInstance().getPreset());
    }
  }

  /** {@inheritDoc} */
  @Override
  public void publishEvent(Login login, Preset preset) {
    try {
      eventServiceCore.publishEvent(
          securityService.doLogin(login), generatingEvent(preset.getAddress()));
      saveEmail(login.getEmail());
      log.info("Event created successfully");
    } catch (FeignException ex) {
      if (log.isDebugEnabled()) {
        log.debug(ex.contentUTF8());
      }
    }
  }

  private void saveEmail(String email) {
    ListOfPatients.getInstance()
        .setEmails(ListOfPatients.getInstance().getEmails().concat("\n - " + email));
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
