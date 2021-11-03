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
package com.lifescan.dummy.data.model;

import com.lifescan.dummy.data.enums.Preset;
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

  private Preset preset;

  private String startDate;
  private String endDate;

  private int exerciseNumbers;
  private int foodNumbers;

  private int bolusNumber;
  private String bolusType;

  private int readingsNumber;
  private String readingsTag;
  private String readingsPreset;

  public static ArgsParameter getInstance() {
    return instance;
  }
}
