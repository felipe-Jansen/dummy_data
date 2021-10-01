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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lifescan.dummy.data.model.Login;
import com.lifescan.dummy.data.networking.service.SecurityServiceCore;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

  private final SecurityServiceCore securityServiceCore;

  public SecurityService(SecurityServiceCore securityServiceCore) {
    this.securityServiceCore = securityServiceCore;
  }

  public String getToken(Login user) {
    Gson gson = new Gson();
    return gson.fromJson(
            gson.fromJson(
                    securityServiceCore.getToken(user.getEmail(), user.getPassword()).toString(),
                    JsonObject.class)
                .get("result"),
            JsonObject.class)
        .get("token")
        .toString();
  }
}
