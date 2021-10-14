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

public interface PatientService {

  /**
   * Method responsible for analyse the input and start generating the patients.
   *
   * @param language that concerns to the idiom of the patients.
   * @param qtdPatients that concerns to the quantity of patients that will be created.
   */
  void create(String language, Integer qtdPatients);
}
