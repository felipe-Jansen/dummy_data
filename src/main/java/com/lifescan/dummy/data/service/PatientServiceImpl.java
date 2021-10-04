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
import com.lifescan.dummy.data.service.Util.Util;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PatientServiceImpl implements PatientService {

  private final PatientServiceCore patientServiceCore;
  private final EventService eventService;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  List<Login> users = new ArrayList<>();

  public PatientServiceImpl(
      PatientServiceCore patientServiceCore,
      EventService eventService,
      SecurityService securityService) {
    this.patientServiceCore = patientServiceCore;
    this.eventService = eventService;
  }

  public void create(String language, Integer qtdPatients) throws InterruptedException {
    String country = Util.extractCountryFromLanguage(language);
    logger.info("language -> {}", language);
    logger.info("Country -> {}", country);
    logger.info("qtdPatients -> {}", qtdPatients);
    for (int i = 0; i < qtdPatients; i++) {
      save(language, country);
    }
    System.exit(0);
  }

  private void save(String language, String country) throws InterruptedException {
    long timeInterval = System.currentTimeMillis();
    Patient patient =
        new Patient(
            "M",
            "t1234567",
            "Patient_" + timeInterval,
            "patient4partner_" + timeInterval + "@mailinator.com",
            Util.generateDateOfBirth(),
            "DIABETES_TYPE_1",
            "Partner tool");
    String requestToken = Util.generateRequestToken(patient.getEmailAddress());
    try {
      logger.info("============");
      patientServiceCore.registerPatient(language, country, requestToken, patient);
      logger.info("Patient: {} | Password: {}", patient.getEmailAddress(), patient.getPassword());
      eventService.publishEvent(new Login(patient.getEmailAddress(), patient.getPassword()));

    } catch (FeignException | JsonProcessingException ex) {
      logger.error(ex.toString());
    }
    TimeUnit.MILLISECONDS.sleep(1L);
  }

  private void addToList(Login login) {
    this.users.add(login);
  }
}
