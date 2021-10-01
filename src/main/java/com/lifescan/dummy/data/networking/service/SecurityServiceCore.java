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

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface SecurityServiceCore {

  @Headers({"Content-Type: application/json", "login: {login}", "password: {password}"})
  @RequestLine("POST /dms-web-services/services/rest/account/v3/authenticate")
  Object getToken(@Param("login") String login, @Param("password") String password);
}
