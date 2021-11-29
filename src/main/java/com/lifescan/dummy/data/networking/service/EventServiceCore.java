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
import com.lifescan.dummy.data.model.Event;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

public interface EventServiceCore {

  @Headers({
    "osversion: 14.6",
    "os: IOS-iPhone12,1",
    "appname: REVEAL_MOBILE_IOS",
    "appversion: " + WebEndPointConstants.APP_VERSION,
    "Content-Type: " + MediaType.APPLICATION_JSON_VALUE,
    "authenticationToken: {token}",
    "token: {token}"
  })
  @RequestLine("POST " + WebEndPointConstants.SYNC_EVENT)
  Object publishEvent(@Param("token") String token, @RequestBody Event event);
}
