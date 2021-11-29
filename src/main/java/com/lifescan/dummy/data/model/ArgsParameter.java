/*
 * Models for the command line arguments.
 * We instantiate some properties with Random that can be overwritten
 * if such property was defined in command line.
 *
 * @author fjansen@lifescan.com
 * @version 1
 * Copyright: Copyright (c) 2021
 * Company: LifeScan IP Holdings, LLC
 * This file contains trade secrets of LifeScan IP Holdings, LLC.
 * No part may be reproduced or transmitted in any
 * form by any means or for any purpose without the express written
 * permission of LifeScan IP Holdings, LLC.
 */
package com.lifescan.dummy.data.model;

import com.lifescan.dummy.data.constants.ConfigConstants;
import com.lifescan.dummy.data.enums.Preset;
import java.util.List;
import java.util.Random;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ArgsParameter {

  private static final ArgsParameter instance = new ArgsParameter();
  private static int tagCounter = 0;

  private String language;
  private int totalPatients;

  private Preset preset;

  private String startDate;
  private String endDate;

  private int exerciseNumbers = new Random().nextInt(ConfigConstants.DEFAULT_EVENTS_NUMBER + 1);
  private int foodNumbers = new Random().nextInt(ConfigConstants.DEFAULT_EVENTS_NUMBER + 1);

  private int bolusNumber = new Random().nextInt(ConfigConstants.DEFAULT_EVENTS_NUMBER + 1);
  private String bolusType;

  private int readingsNumber = new Random().nextInt(ConfigConstants.DEFAULT_EVENTS_NUMBER + 1);
  private List<String> readingsTag;
  private String readingsPreset;

  public static ArgsParameter getInstance() {
    return instance;
  }
}
