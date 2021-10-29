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

public interface RegistrationService {

  /**
   * Method responsible for registering a new patient.
   *
   * @param language native idiom for the patient
   * @param country country of the patient
   * @param requestToken request token to be able to access the api
   * @param patient patient information
   */
  void registerPatient(String language, String country, String requestToken, Patient patient);
}
