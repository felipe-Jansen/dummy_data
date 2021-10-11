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

  public static final String AUTHENTICATE = "/mobile/user/v3/authenticate";
  public static final String REGISTER_PATIENT = "/mobile/user/v3/register";
  public static final String REGISTER_EVENT = "/mobile/health/v1/data/publish";
}
