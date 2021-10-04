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

public class WebEndPointConstants {

  public static final String AUTHENTICATE =
      "/dms-web-services/services/rest/account/v3/authenticate";
  public static final String REGISTER_PATIENT =
      "/dms-web-services/services/rest/account/v3/register";
  public static final String REGISTER_EVENT =
      "/dms-data-receiver-camel-web/services/rest/deviceData/publish";

  private WebEndPointConstants() {
    throw new IllegalStateException(BaseConstants.UTILITY_CLASSES_SHOULD_NOT_BE_INSTANTIATED);
  }
}
