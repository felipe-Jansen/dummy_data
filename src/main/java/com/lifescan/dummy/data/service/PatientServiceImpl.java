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
  public void create(String language, Integer numberPatients) {
    String country = Util.extractCountryFromLanguage(language);
    log.info("language -> {}", language);
    log.info("Country -> {}", country);
    log.info("qtdPatients -> {}", numberPatients);
    for (int i = 0; i < numberPatients; i++) {
      publishingEvent(save(language, country));
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
  private Patient save(String language, String country) {
    Patient patient = generatingPatient();
    String requestToken = Util.generateRequestToken(patient.getEmailAddress());
    try {
      registerPatient(language, country, patient, requestToken);
    } catch (FeignException ex) {
      log.error(ex.contentUTF8());
    }
    return patient;
  }

  /**
   * Method responsible for publishing the events
   *
   * @param patient that contains the patient's information
   */
  private void publishingEvent(Patient patient) {
    try {
      eventService.publishEvent(generatingLogin(patient.getEmailAddress(), patient.getPassword()));
    } catch (JsonProcessingException ex) {
      log.error("Error when publishing event!");
    }
  }

  /**
   * Method responsible for register patients
   *
   * @param language it concerns to native language ot the patient
   * @param country it concerns to country ot the patient
   * @param patient it concerns to general patient's information
   * @param requestToken it concerns to the native language ot the patient
   */
  private void registerPatient(
      String language, String country, Patient patient, String requestToken) {
    patientServiceCore.registerPatient(language, country, requestToken, patient);
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

  /**
   * Method responsible for return an object from type login.
   *
   * @param emailAddress it concerns to the email to do login.
   * @param password it concerns to the password to do login.
   * @return A single object from type login.
   */
  private Login generatingLogin(String emailAddress, String password) {
    return Login.builder().email(emailAddress).password(password).build();
  }
}
