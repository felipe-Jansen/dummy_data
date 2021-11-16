/*
 * Holds values for BolusTypes.
 *
 * @author lpaccana@lifescan.com
 * @version 1
 * Copyright: Copyright (c) 2021
 * Company: LifeScan IP Holdings, LLC
 * This file contains trade secrets of LifeScan IP Holdings, LLC.
 * No part may be reproduced or transmitted in any
 * form by any means or for any purpose without the express written
 * permission of LifeScan IP Holdings, LLC.
 */
package com.lifescan.dummy.data.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum BolusType {
  BOLUS_INSULIN_SHORT,
  FAST,
  LONG,
  MIXED,
  NPH,
  OTHERS;

  public static boolean contains(String type) {
    return Arrays.stream(BolusType.values())
        .map(Enum::name)
        .collect(Collectors.toList())
        .contains(type.toUpperCase());
  }
}
