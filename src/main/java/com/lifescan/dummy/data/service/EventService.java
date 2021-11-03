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

import com.lifescan.dummy.data.enums.Preset;
import com.lifescan.dummy.data.model.Login;
import org.springframework.stereotype.Service;

@Service
public interface EventService {

  /**
   * Method responsible for analyse the input and start generating the patients.
   *
   * @param language that concerns to the idiom of the patients.
   * @param numberPatients that concerns to the quantity of patients that will be created.
   */
  void create(String language, Integer numberPatients);

  /**
   * Method responsible for creating the events for each new patient.
   *
   * @param login that contains the information of email and password.
   */
  void publishEvent(Login login, Preset preset);
}
