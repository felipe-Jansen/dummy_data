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
package com.lifescan.dummy.data.networking.service;

import com.lifescan.dummy.data.constants.WebEndPointConstants;
import com.lifescan.dummy.data.model.Patient;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;

public interface PatientServiceCore {

  @Headers({
    "osversion: 14.6",
    "os: IOS-iPhone12,1",
    "appname: REVEAL_MOBILE_IOS",
    "appversion: 4.5.0",
    "Content-Type: application/json",
    "language: {language}",
    "country: {country}",
    "requestToken: {requestToken}"
  })
  @RequestLine("POST " + WebEndPointConstants.REGISTER_PATIENT)
  void registerPatient(
      @Param("language") String language,
      @Param("country") String country,
      @Param("requestToken") String requestToken,
      @RequestBody Patient patient);
}
