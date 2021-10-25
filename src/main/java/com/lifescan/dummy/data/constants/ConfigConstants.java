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

  public static final String PREFIX_PATTERN_PATIENT_NAME = "Patient_";

  public static final String SUFFIX_PATTERN_PATIENT_EMAIL = "_patient@partnerapi.com";

  public static final String GENDER_MALE = "M";

  public static final String GENDER_FEMALE = "F";

  public static final String PATIENT_PASSWORD = "t1234567";

  public static final String PATIENT_LAST_NAME = "Partner tool";

  public static final String PATIENT_DIABETES_TYPE = "DIABETES_TYPE_1";

  public static final int DELAY_TIME_BETWEEN_EVENTS = 5;

  public static final String DATA_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

  public static final int DEFAULT_QUANTITY_EVENTS = 10;
}
