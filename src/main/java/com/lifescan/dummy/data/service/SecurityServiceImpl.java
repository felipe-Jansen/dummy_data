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

import com.lifescan.dummy.data.model.Login;
import com.lifescan.dummy.data.networking.service.SecurityServiceCore;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityServiceImpl implements SecurityService {

  private final SecurityServiceCore securityServiceCore;

  /** {@inheritDoc} */
  @Override
  public String doLogin(Login user) {
    return securityServiceCore
        .authenticate(user.getEmail(), user.getPassword())
        .getResult()
        .getToken();
  }
}
