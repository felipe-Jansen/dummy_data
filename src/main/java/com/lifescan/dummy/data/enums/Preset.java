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
package com.lifescan.dummy.data.enums;

public enum Preset {
  PRESET_DIABETES_1_WITH_FOOD(1),
  PRESET_DIABETES_1(2),
  PRESET_DIABETES_2(3),
  PRESET_DIABETES_2_WITH_FOOD(4),
  PRESET_GESTATIONAL_DIABETES(5);

  // Change to ID
  private final int id;

  Preset(int id) {
    this.id = id;
  }
}
