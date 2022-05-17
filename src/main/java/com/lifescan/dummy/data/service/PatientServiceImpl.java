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
import com.lifescan.dummy.data.model.ListOfPatients;
import com.lifescan.dummy.data.model.Patient;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PatientServiceImpl implements PatientService {

  private final RegistrationService registrationService;

  /**
   * Method responsible for generate a date of birth from system date.
   *
   * @return A string that concerns to the formatted date of birth.
   */
  public static String generateDateOfBirth() {
    return LocalDateTime.now().minusYears(20).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
  }

  /**
   * Method responsible for generate a SHA1 token from the e-mail.
   *
   * @param emailAddress to serve as a base to generate the token.
   * @return A request token from email.
   */
  private String generateRequestToken(String emailAddress) {
    return DigestUtils.sha1Hex(DigestUtils.sha1Hex(emailAddress).concat(emailAddress));
  }

  /** {@inheritDoc} */
  @Override
  public Patient create(String language, String country) {
    Patient patient = buildPatient();
    String requestToken = generateRequestToken(patient.getEmailAddress());
    registrationService.registerPatient(language, country, requestToken, patient);
    saveEmail(patient.getEmailAddress());
    return patient;
  }

  /**
   * Save generated email
   *
   * @param email generated email.
   */
  private void saveEmail(String email) {
    ListOfPatients.getInstance()
        .setEmails(ListOfPatients.getInstance().getEmails().concat("\n - " + email + " | https://www.mailinator.com/v4/public/inboxes.jsp?to=" + email));
  }

  /**
   * Method responsible for generating one patient.
   *
   * @return A single object from type Patient.
   */
  private Patient buildPatient() {
    return Patient.builder()
        .gender(ConfigConstants.GENDER_FEMALE)
        .password(ConfigConstants.PATIENT_PASSWORD)
        .firstName(ConfigConstants.PATIENT_FIRST_NAME)
        .lastName(ConfigConstants.PATIENT_LAST_NAME)
        .emailAddress(Instant.now().toEpochMilli() + ConfigConstants.SUFFIX_PATTERN_PATIENT_EMAIL)
        .dateOfBirth(generateDateOfBirth())
        .diabetesType(ConfigConstants.PATIENT_DIABETES_TYPE_1)
        .build();
  }
}
