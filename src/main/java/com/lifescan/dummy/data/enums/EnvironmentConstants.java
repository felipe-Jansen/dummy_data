/*
 * @author fjansen@lifescan.com
 * @version 1
 * Copyright: Copyright (c) 2022
 * Company: LifeScan IP Holdings, LLC
 * This file contains trade secrets of LifeScan IP Holdings, LLC.
 * No part may be reproduced or transmitted in any
 * form by any means or for any purpose without the express written
 * permission of LifeScan IP Holdings, LLC.
 */
package com.lifescan.dummy.data.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EnvironmentConstants {

  DEV("https://api.dev.lfsdigital.com"),
  TEST("https://api.test.lfsdigital.com"),
  PRE_STAGE("https://api.pre-stage.lfsdigital.com"),
  STAGE("https://api.diabetes-dev.com");

  public final String url;

}
