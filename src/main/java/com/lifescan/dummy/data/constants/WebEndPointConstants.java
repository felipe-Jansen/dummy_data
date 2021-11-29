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
package com.lifescan.dummy.data.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WebEndPointConstants {

  /** This is the login endpoint. */
  public static final String AUTHENTICATE = "/mobile/user/v3/authenticate";

  /** This is the registration endpoint. */
  public static final String REGISTER_PATIENT = "/mobile/user/v3/register";

  /** This is the publish events endpoint. */
  public static final String SYNC_EVENT = "/mobile/health/v1/data/publish";

  public static final String APP_VERSION = "4.5.0";
}
