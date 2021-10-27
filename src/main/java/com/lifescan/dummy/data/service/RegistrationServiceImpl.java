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

import com.lifescan.dummy.data.model.Patient;
import com.lifescan.dummy.data.networking.service.PatientServiceCore;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationServiceImpl implements RegistrationService {

  private final PatientServiceCore patientServiceCore;

  @Override
  public void registerPatient(
      String language, String country, String requestToken, Patient patient) {
    patientServiceCore.registerPatient(language, country, requestToken, patient);
  }
}
