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
import com.lifescan.dummy.data.model.Login;
import com.lifescan.dummy.data.model.Patient;
import com.lifescan.dummy.data.networking.service.PatientServiceCore;
import com.lifescan.dummy.data.service.util.Util;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
// @TODO We should have one REGISTRATION service and another Sync/Publish service.
public class PatientServiceImpl implements PatientService {

  private final PatientServiceCore patientServiceCore;
  private final EventService eventService;

  /** {@inheritDoc} */
  @Override
  public void execute(String language, Integer numberPatients) {
    log.traceEntry("language:{}, numberPatients:{}", language, numberPatients);
    for (int i = 0; i < numberPatients; i++) {
      publishEvent(register(language, getCountry(language)));
      // final Patient patient = buildPatient();
      // registration.save / execute(patient);
      // login.doLogin / execute(patient.getUsername(), patient.getPassword());
      // event.sync(patient, readings);
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
   * Register a new patient.
   *
   * @param language Patient's language ISO code
   * @param country Patient's country
   * @return Patient object
   */
  private Patient register(String language, String country) {
    Patient patient = buildPatient();
    String requestToken = Util.generateRequestToken(patient.getEmailAddress());
    patientServiceCore.registerPatient(language, country, requestToken, patient);
    return patient;
  }

  /**
   * Method responsible for publishing the events
   *
   * @param patient that contains the patient's information
   */
  private void publishEvent(Patient patient) {
    eventService.publishEvent(buildLogin(patient.getEmailAddress(), patient.getPassword()));
  }

  /**
   * Method responsible for generating one patient.
   *
   * @return A single object from type Patient.
   */
  private Patient buildPatient() {
    long instant = Instant.now().toEpochMilli();
    return Patient.builder()
        .gender(ConfigConstants.GENDER_FEMALE)
        .password(ConfigConstants.PATIENT_PASSWORD)
        .firstName(ConfigConstants.PATIENT_FIRST_NAME)
        .lastName(ConfigConstants.PATIENT_LAST_NAME)
        .emailAddress(instant + ConfigConstants.SUFFIX_PATTERN_PATIENT_EMAIL)
        .dateOfBirth(Util.generateDateOfBirth())
        .diabetesType(ConfigConstants.PATIENT_DIABETES_TYPE_1)
        .build();
  }

  /**
   * Method responsible for return an object from type login.
   *
   * @param emailAddress it concerns to the email to do login.
   * @param password it concerns to the password to do login.
   * @return A single object from type login.
   */
  private Login buildLogin(String emailAddress, String password) {
    return Login.builder().email(emailAddress).password(password).build();
  }
}
