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
public class ArgsConstants {

  public static final int LANGUAGE_ISO = 0;
  public static final int NUMBER_PATIENTS = 1;
  public static final int START_DATE = 2;
  public static final int END_DATE = 3;
  public static final int EXERCISE = 4;
  public static final int FOOD = 5;
  public static final int BOLUS = 6;
  public static final int READING = 7;
  public static final MappedAttribute BOLUS_TYPE =
      MappedAttribute.builder().index(BOLUS).event("bolus").attribute("type").build();
  public static final MappedAttribute READING_TAG =
      MappedAttribute.builder().index(READING).event("reading").attribute("Tag").build();
  public static final MappedAttribute READING_PRESET =
      MappedAttribute.builder().index(READING).event("reading").attribute("preset").build();
}
