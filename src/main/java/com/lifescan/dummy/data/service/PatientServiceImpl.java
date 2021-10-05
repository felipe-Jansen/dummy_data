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
import com.lifescan.dummy.data.model.Login;
import com.lifescan.dummy.data.model.Patient;
import com.lifescan.dummy.data.networking.service.PatientServiceCore;
import com.lifescan.dummy.data.service.util.Util;
import feign.FeignException;
import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class PatientServiceImpl implements PatientService {

  private final PatientServiceCore patientServiceCore;
  private final EventService eventService;

  public PatientServiceImpl(PatientServiceCore patientServiceCore, EventService eventService) {
    this.patientServiceCore = patientServiceCore;
    this.eventService = eventService;
  }

  public void create(String language, Integer qtdPatients) throws InterruptedException {
    String country = Util.extractCountryFromLanguage(language);
    log.info("language -> {}", language);
    log.info("Country -> {}", country);
    log.info("qtdPatients -> {}", qtdPatients);
    for (int i = 0; i < qtdPatients; i++) {
      save(language, country);
    }
    System.exit(0);
  }

  private void save(String language, String country) throws InterruptedException {
    Patient patient = generatingPatient();
    String requestToken = Util.generateRequestToken(patient.getEmailAddress());
    try {
      log.info("============");
      patientServiceCore.registerPatient(language, country, requestToken, patient);
      log.info("Patient: {} | Password: {}", patient.getEmailAddress(), patient.getPassword());
      eventService.publishEvent(generatingLogin(patient.getEmailAddress(), patient.getPassword()));

    } catch (FeignException | JsonProcessingException ex) {
      log.error(ex.toString());
    }
    TimeUnit.MILLISECONDS.sleep(1L);
  }

  private Patient generatingPatient() {
    long timeInterval = System.currentTimeMillis();
    Patient patient = new Patient();
    patient.setGender("M");
    patient.setPassword("t1234567");
    patient.setFirstName("Patient_" + timeInterval);
    patient.setEmailAddress("patient4partner_" + timeInterval + "@mailinator.com");
    patient.setDateOfBirth(Util.generateDateOfBirth());
    patient.setDiabetesType("DIABETES_TYPE_1");
    patient.setLastName("Partner tool");
    return patient;
  }

  private Login generatingLogin(String emailAddress, String password) {
    Login login = new Login();
    login.setEmail(emailAddress);
    login.setPassword(password);
    return login;
  }
}
