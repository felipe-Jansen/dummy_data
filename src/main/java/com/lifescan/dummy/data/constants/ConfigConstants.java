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
public class ConfigConstants {

  public static final String SUFFIX_PATTERN_PATIENT_EMAIL = "_patient@mailinator.com";

  public static final String GENDER_FEMALE = "F";

  public static final String PATIENT_PASSWORD = "t1234567";

  public static final String PATIENT_LAST_NAME = "Partner tool";

  public static final String PATIENT_FIRST_NAME = "Jane";

  public static final String PATIENT_DIABETES_TYPE_1 = "DIABETES_TYPE_1";

  public static final String DATA_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

  public static final String SOURCE_APP = "REVEAL_MOBILE_IOS";

  public static final String APP_VERSION = "5.3.1";

  public static final int MIN_VALUE_DURATION_ATTRIBUTE = 30;

  public static final int MAX_VALUE_DURATION_ATTRIBUTE = 60;

  public static final int MIN_VALUE_BOLUS_UNIT = 1;

  public static final int MAX_VALUE_BOLUS_UNIT = 10;

  public static final String UNIT_BOLUS_VALUE = "U";

  public static final int MAX_VALUE_CARB_FOOD = 400;

  public static final int MIN_VALUE_CARB_FOOD = 10;

  public static final String UNIT_VALUE_CARB_FOOD = "g";

  public static final int DEFAULT_EVENTS_NUMBER = 10;

  public static final String FALSE = "false";

  public static final String TRUE = "true";
  public static final int MIN_VALUE_BGVALUE = 70;
  public static final int MAX_VALUE_BGVALUE = 126;
  public static final String UNIT_BGVALUE = "mg/dL";
}
