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
import com.lifescan.dummy.data.constants.ConfigConstants;
import com.lifescan.dummy.data.model.Login;
import com.lifescan.dummy.data.model.Patient;
import com.lifescan.dummy.data.networking.service.PatientServiceCore;
import com.lifescan.dummy.data.service.util.Util;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PatientServiceImpl implements PatientService {

  private final PatientServiceCore patientServiceCore;
  private final EventService eventService;

  /** {@inheritDoc} */
  public void create(String language, Integer qtdPatients) {
    String country = Util.extractCountryFromLanguage(language);
    log.info("language -> {}", language);
    log.info("Country -> {}", country);
    log.info("qtdPatients -> {}", qtdPatients);
    for (int i = 0; i < qtdPatients; i++) {
      save(language, country);
    }
  }

  /**
   * Method responsible for save the patients and publish their respectives events.
   *
   * @param language A patient need to have a language associated, this param concerns to this
   *     information.
   * @param country A patient need to have a country associated, this param concerns to this
   *     information.
   */
  private void save(String language, String country) {
    Patient patient = generatingPatient();
    String requestToken = Util.generateRequestToken(patient.getEmailAddress());
    try {
      patientServiceCore.registerPatient(language, country, requestToken, patient);
    } catch (FeignException ex) {
      log.error(ex.contentUTF8());
    }
  }

  /**
   * Method responsible for generating one patient.
   *
   * @return A single object from type Patient.
   */
  private Patient generatingPatient() {
    long timeInterval = System.currentTimeMillis();
    return Patient.builder()
        .gender(ConfigConstants.GENDER_FEMALE)
        .password(ConfigConstants.PATIENT_PASSWORD)
        .firstName(ConfigConstants.PREFIX_PATTERN_PATIENT_NAME + timeInterval)
        .emailAddress(timeInterval + ConfigConstants.SUFFIX_PATTERN_PATIENT_EMAIL)
        .dateOfBirth(Util.generateDateOfBirth())
        .diabetesType(ConfigConstants.PATIENT_DIABETES_TYPE)
        .lastName(ConfigConstants.PATIENT_LAST_NAME)
        .build();
  }

}
