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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class ArgsParameter {

  private static final ArgsParameter instance = new ArgsParameter();

  private String preset;

  private String startDate;
  private String endDate;

  private int exerciseNumbers;
  private int foodNumbers;

  private int bolusNumber;
  private String bolusType;

  private int readingsNumber;
  private String readingsTag;
  private String readingsPreset;

  private ArgsParameter() {}

  public static ArgsParameter getInstance() {
    return instance;
  }
}
