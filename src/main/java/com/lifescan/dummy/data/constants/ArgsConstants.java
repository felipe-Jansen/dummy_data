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
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArgsConstants {

  public static final int LANGUAGE_ISO = 0;
  public static final int NUMBER_PATIENTS = 1;
  public static final int START_DATE = 2;
  public static final int END_DATE = 3;
  public static final int PRESET_ID = 4;
  public static final int MAX_TIME_INTERVAL = 90;
  public static final String EXERCISE = "exercise";
  public static final String FOOD = "food";
  public static final String BOLUS = "bolus";
  public static final String TYPE = "type";
  public static final String READING = "reading";
  public static final String TAG = "Tag";
  public static final String PRESET = "preset";
}
